package config.vision.limelight;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Config
public class Vision {
    // Limelight and claw configuration
    public static double limelightHeight = 9.5;  // Camera height in inches
    public static double limelightAngle = 60;    // Camera angle (0° = down, 90° = forward)
    public static double clawForwardOffset = 19; // Claw's forward offset from the camera
    public static double clawLateralOffset = 5;  // Claw's lateral (right is +) offset from the camera

    private Pose sample = new Pose();  // The best sample's position
    private Limelight3A limelight;
    private LLResult result;
    private Telemetry telemetry;

    public Vision(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(9);
        limelight.start();
    }

    public void periodic() {
        result = limelight.getLatestResult();
        List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();

        if (detections.isEmpty()) {
            telemetry.addData("Detections", "None");
            telemetry.update();
            return;
        }

        // List to store scored detections
        List<LL3ADetection> scoredDetections = new ArrayList<>();

        for (LLResultTypes.DetectorResult detection : detections) {
            // Compute angles
            double actualYAngle = Math.toRadians(limelightAngle + detection.getTargetYDegrees());
            double xAngle = Math.toRadians(-detection.getTargetXDegrees());

            // Compute distances
            double yDistance = limelightHeight / Math.tan(actualYAngle);  // Forward distance (FTC X)
            double xDistance = Math.tan(xAngle) * yDistance;              // Lateral distance (FTC Y)

            // Score based on alignment
            double rotationScore = -Math.abs((detection.getTargetCorners().get(0).get(0) - 
                                              detection.getTargetCorners().get(1).get(0)) /
                                             (detection.getTargetCorners().get(1).get(1) - 
                                              detection.getTargetCorners().get(2).get(1)) - (1.5 / 3.5));
            double score = -yDistance - Math.abs(xDistance) + 2.0 * rotationScore; // Weighted scoring

            scoredDetections.add(new LL3ADetection(detection, score, yDistance, xDistance, rotationScore));
        }

        // Find the best detection
        scoredDetections.sort(Comparator.comparingDouble(LL3ADetection::getScore).reversed());
        LL3ADetection bestDetection = scoredDetections.get(0);

        // Convert to coordinates and apply claw offsets
        sample = new Pose(
                bestDetection.getYDistance() - clawForwardOffset, // X (forward)
                -bestDetection.getXDistance() - clawLateralOffset, // Y (left)
                0
        );

        // Display results
        telemetry.clear();
        telemetry.addData("Best Detection", bestDetection.getDetection().getClassName());
        telemetry.addData("Sample Position", "X: %.2f, Y: %.2f", sample.getX(), sample.getY());
        telemetry.update();
    }

    public Pose getPose(Pose currentPose) {
        return new Pose(sample.getX() + currentPose.getX(), sample.getY() + currentPose.getY(), currentPose.getHeading());
    }

    public void off() {
        limelight.stop();
    }

    public void on() {
        limelight.start();
    }
}
