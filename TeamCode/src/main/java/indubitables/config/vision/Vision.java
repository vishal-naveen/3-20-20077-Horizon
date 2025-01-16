package indubitables.config.vision;

import android.annotation.SuppressLint;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Vision extends SubsystemBase {
    // Configurable parameters for sample and camera setup
    private final double idealAspectRatio = 1.5 / 3.5; // Expected width:height ratio for a properly aligned sample
    private final double angleWeight = 15.0; // Weight for scoring based on rotation
    private final double minDistance = 18.0; // Minimum allowable distance (adjust as needed)
    private final double maxDistance = 32.0;
    private final double limelightHeight = 12.25; //how high up the limelight is in inches;
    private final double limelightAngle = 58.5125; //angle of the limelight, camera facing straight down would be 0
    //and camera facing straight forwards would be 90

    private double limelightRotationOffset = 90.0; // Rotation of the camera in degrees


    private double yDistance;
    private double xDistance;

    private final double forwardOffset = 4;
    private final double lateralOffset = -4.75;

    // 0 is Blue, Red is 1, Yellow is 2
    private final int[] unwantedSamples;

    public List<ScoredDetection> scoredDetections;

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

    @SuppressLint("DefaultLocale")
    @Override
    public void periodic() {
        result = limelight.getLatestResult();
        List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();

        // List to hold scored detections
        scoredDetections = new ArrayList<>();

        for (LLResultTypes.DetectorResult detection : detections) {
            int color = detection.getClassId(); // Detected class (color)

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
            double yDistance = limelightHeight * Math.tan(Math.toRadians(actualYAngle));
            double xDistance = Math.tan(Math.toRadians(detection.getTargetXDegrees())) * yDistance;

            // If color matches unwanted sample, skip the rest of the current iteration
            if (Arrays.stream(unwantedSamples).anyMatch(sample -> sample == color)) {
                continue;
            }

            // Calculate a final score
            double score = calculateScore(color, yDistance, xDistance, rotationScore, angleWeight, detections);

            // Add the scored detection to the list
            scoredDetections.add(new ScoredDetection(detection, score, yDistance, xDistance, rotationScore));
        }

        // Sort detections by score in descending order
        scoredDetections.sort(Comparator.comparingDouble(ScoredDetection::getScore).reversed());

        // Display sorted results on telemetry
        if (!scoredDetections.isEmpty()) {
            telemetry.addData("Best Detection", scoredDetections.get(0).getDetection().getClassName());
            telemetry.addLine("Sorted Detections:");
            for (ScoredDetection scored : scoredDetections) {
                LLResultTypes.DetectorResult det = scored.getDetection();
                telemetry.addData(det.getClassName(),
                        String.format("Score: %.2f | Position: (%.2f, %.2f) | Rotation: %.2f | XDeg %.2f: | YDeg: %.2f", scored.getScore(), scored.getXDistance(), scored.getYDistance(), scored.getRotationScore(), det.getTargetXDegrees(), det.getTargetYDegrees()));
            }
        } else {
            telemetry.addData("Detections", "None (No matches for preferred color or too close)");
        }

        if (!scoredDetections.isEmpty()) {
            double adjustedXDegrees = scoredDetections.get(0).getDetection().getTargetXDegrees() - limelightRotationOffset;
            double actualYAngle = limelightAngle - scoredDetections.get(0).getDetection().getTargetYDegrees();
            yDistance = limelightHeight * Math.tan(Math.toRadians(actualYAngle));
            xDistance = Math.tan(Math.toRadians(adjustedXDegrees)) * yDistance;
        }
    }

    private double calculateDistance(List<Double> point1, List<Double> point2) {
        double dx = point1.get(0) - point2.get(0);
        double dy = point1.get(1) - point2.get(1);
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Scoring function
    private double calculateScore(int color, double distance, double x, double rotationScore, double angleWeight, List<LLResultTypes.DetectorResult> detections) {
        double score = 0.0;
        // high high penalty for detections that are too close or far
        if (distance < minDistance || distance > maxDistance) {
            score -= 100;
        }

        // Factor 2: Alignment (centered is better)
        score -= Math.abs(x);

        // Factor 3: Rotation (closer to ideal aspect ratio is better)
        score += rotationScore * angleWeight;

        // Factor 4: Isolation (penalize for overlapping or blocked objects)
        for (LLResultTypes.DetectorResult other : detections) {
            double actualYAngle = limelightAngle - other.getTargetYDegrees();
            double yDistance = limelightHeight / Math.cos(Math.toRadians(actualYAngle));
            double xDistance = Math.tan(Math.toRadians(other.getTargetXDegrees())) * yDistance;

            // Penalize if another object is close
            if (Math.abs(xDistance - x) < 4 && Math.abs(yDistance - distance) < 4) {
                score -= 20.0; // Adjust penalty value as needed
            }
        }

        return score;
    }

    public double extendDistance() {
        return yDistance - forwardOffset;
    }

    public Pose getAlignedPose(Pose currentPose) {
        double adjustedX = xDistance * Math.cos(Math.toRadians(currentPose.getHeading())) - yDistance * Math.sin(Math.toRadians(currentPose.getHeading()));
        double adjustedY = xDistance * Math.sin(Math.toRadians(currentPose.getHeading())) + yDistance * Math.cos(Math.toRadians(currentPose.getHeading()));
        return new Pose(adjustedX - lateralOffset, adjustedY - forwardOffset, Math.toRadians(currentPose.getHeading()));
    }

    public void setLimelightRotationOffset(double limelightRotationOffset) {
        this.limelightRotationOffset = limelightRotationOffset;
    }

    // Helper class for scored detections
    private static class ScoredDetection {
        private final LLResultTypes.DetectorResult detection;
        private final double score;
        private final double yDistance;
        private final double rotationScore;
        private final double xDistance;

        public ScoredDetection(LLResultTypes.DetectorResult detection, double score, double yDistance, double xDistance, double rotationScore) {
            this.detection = detection;
            this.score = score;
            this.yDistance = yDistance;
            this.rotationScore = rotationScore;
            this.xDistance = xDistance;
        }

        public LLResultTypes.DetectorResult getDetection() {
            return detection;
        }
        public double getScore() {
            return score;
        }
        public double getYDistance() {
            return yDistance;
        }
        public double getXDistance() {
            return xDistance;
        }
        public double getRotationScore() {
            return rotationScore;
        }
    }
}
