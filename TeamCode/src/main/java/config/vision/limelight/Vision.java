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
    // Configurable parameters for sample and camera setup
    public static double idealAspectRatio = 1.5 / 3.5; // Expected width:height ratio for a properly aligned sample
    public static double angleWeight = 2.0; // Weight for scoring based on rotation
    public static double minDistance = 3; // Minimum allowable distance (adjust as needed)
    public static double maxDistance = 20;
    public static double limelightHeight = 9.5; //how high up the limelight is in inches;
    public static double limelightAngle = 60; //angle of the limelight, camera facing straight down would be 0
    //and camera facing straight forwards would be 90

    public static double clawDistance = 0; // Distance of the claw in front of the Limelight in inches
    public static double clawLateralOffset = 0; // Distance of the claw to the right of the Limelight in inches

    private double yDistance;
    private double xDistance;

    public static double forwardOffset = 0;
    public static double lateralOffset = 0;
    public static double angleOffset = -0.349;
    // 0 is Blue, Red is 1, Yellow is 2
    public static int[] unwantedSamples;

    private List<LL3ADetection> LL3ADetections;

    private double sampleAngle = 0;
    private double dx = 0;
    private double dy = 0;
    private List<List<Double>> corner;
    private Pose sample = new Pose(), claw = new Pose();
    private double x, y;

    Limelight3A limelight;
    LLResult result;

    Telemetry telemetry;
    HardwareMap hardwareMap;

    public Vision(HardwareMap hardwareMap, Telemetry telemetry, int[] unwantedSamples) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        this.unwantedSamples = unwantedSamples;

        limelight = this.hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // Updates per second
        limelight.pipelineSwitch(9);

        limelight.start();
    }

    public void periodic() {
        result = limelight.getLatestResult();
        List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();

        // List to hold scored detections
        List<LL3ADetection> scoredDetections = new ArrayList<>();

        for (LLResultTypes.DetectorResult detection : detections) {
            int color = detection.getClassId(); // Detected class (color)

            boolean colorRight = true;

            for (int e : unwantedSamples) {
                if (color == e) {
                    colorRight = false;
                    break;
                }
            }

            if (colorRight) {

                // Calculate bounding box dimensions
                List<List<Double>> corners = detection.getTargetCorners();
                if (corners == null || corners.size() < 4) {
                    continue; // Skip invalid detections
                }

                double width = calculateDistance(corners.get(0), corners.get(1)); // Top edge
                double height = calculateDistance(corners.get(1), corners.get(2)); // Side edge

                // Calculate aspect ratio and rotation score
                double aspectRatio = width / height;
                double rotationScore = -Math.abs(aspectRatio - idealAspectRatio); // Closer to ideal is better

                // Calculate distance (approximation based on angles)
                double actualYAngle = limelightAngle - detection.getTargetYDegrees();
                double yDistance = ((limelightHeight * Math.tan(Math.toRadians(actualYAngle))) - 16) / 3.3125;
                double xDistance = Math.tan(Math.toRadians(detection.getTargetXDegrees())) * yDistance;

                // Calculate a final score
                double score = calculateScore(color, yDistance, xDistance, rotationScore, angleWeight, detections);

                // Add the scored detection to the list
                scoredDetections.add(new LL3ADetection(detection, score, yDistance, xDistance, rotationScore));
            }
        }

        // Sort detections by score in descending order
        scoredDetections.sort(Comparator.comparingDouble(LL3ADetection::getScore).reversed());

        // Display sorted results on telemetry
                       telemetry.clear();
                        if (!scoredDetections.isEmpty()) {
                            telemetry.addData("Best Detection", scoredDetections.get(0).getDetection().getClassName());
                            telemetry.addLine("Sorted Detections:");
                            for (LL3ADetection scored : scoredDetections) {
                                LLResultTypes.DetectorResult det = scored.getDetection();
                                telemetry.addData(det.getClassName(),
                                        String.format("Score: %.2f | Position: (%.2f, %.2f) | Rotation: %.2f | XDeg %.2f: | YDeg: %.2f",scored.getScore(), scored.getXDistance(), scored.getYDistance(), scored.getRotationScore(),det.getTargetXDegrees(), det.getTargetYDegrees()));
                            }
                        } else {
                            telemetry.addData("Detections", "None (No matches for preferred color or too close)");
                        }

        if(!scoredDetections.isEmpty()) {
            sample = new Pose(scoredDetections.get(0).getYDistance() + forwardOffset, scoredDetections.get(0).getXDistance() + lateralOffset, 0);
            telemetry.addData("sample", sample);
        }

        telemetry.update();
    }

    private double calculateDistance(List<Double> point1, List<Double> point2) {
        double dx = point1.get(0) - point2.get(0);
        double dy = point1.get(1) - point2.get(1);
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getBestDetectionAngle() {
        return Math.toDegrees(sampleAngle);
    }

    public double[] getD() {
        return new double[]{dx, dy};
    }

    public List<List<Double>> getCorner() {
        return corner;
    }

    private double calculateScore(int color, double distance, double x, double rotationScore, double angleWeight, List<
            LLResultTypes.DetectorResult> detections) {
        double score = 0.0;
        // high high penalty for detections that are too close or far
        if (distance < minDistance || distance > maxDistance) {
            score -= 100;
        }

        //closer to robot is better
        score -= distance;

        // Factor 2: Alignment (centered is better)
        score -= Math.abs(x+4.4)/2;

        // Factor 3: Rotation (closer to ideal aspect ratio is better)
        score += rotationScore * angleWeight;

        // Factor 4: Isolation (penalize for overlapping or blocked objects)
        for (LLResultTypes.DetectorResult other : detections) {
            double actualYAngle = limelightAngle - other.getTargetYDegrees();
            double yDistance = limelightHeight / Math.cos(Math.toRadians(actualYAngle));
            double xDistance = Math.tan(Math.toRadians(other.getTargetXDegrees())) * yDistance;

            // Penalize if another object is close
            if (Math.abs(xDistance - x) < 2 && (yDistance - distance) < 1) {
                score -= 5.0; // Adjust penalty value as needed
            }
        }

        return score;
    }

    public Pose getPose(Pose currentPose) {
        return new Pose(- sample.getX() + currentPose.getX(), - sample.getY() + currentPose.getY(), currentPose.getHeading());
    }

    public LL3ADetection bestDetection() {
        return LL3ADetections.get(0);
    }

    public void off() {
        limelight.stop();
    }

    public void on() {
        limelight.start();
    }

    public Limelight3A getLimelight() {
        return limelight;
    }

}