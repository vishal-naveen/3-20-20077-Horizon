package config.vision.limelight;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.config.Config;
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

@Config
public class Vision {
    // Configurable parameters for sample and camera setup
    public static double idealAspectRatio = 1.5 / 3.5; // Expected width:height ratio for a properly aligned sample
    public static double angleWeight = 5.0; // Weight for scoring based on rotation
    public static double minDistance = 3; // Minimum allowable distance (adjust as needed)
    public static double maxDistance = 20;
    public static double limelightHeight = 9.5; //how high up the limelight is in inches;
    public static double limelightAngle = 60; //angle of the limelight, camera facing straight down would be 0
    //and camera facing straight forwards would be 90

    public static double limelightRotationOffset = 0.0; // Rotation of the camera in degrees

    public static double clawDistance = -1; // Distance of the claw in front of the Limelight in inches
    public static double clawLateralOffset = 0; // Distance of the claw to the right of the Limelight in inches


    private double yDistance;
    private double xDistance;

    public static double forwardOffset = -5;
    public static double lateralOffset = 6;
    public static double angleOffset = -0.349;
    // 0 is Blue, Red is 1, Yellow is 2
    public static int[] unwantedSamples;

    private List<LL3ADetection> LL3ADetections;

    private double sampleAngle = 0 ;
    private double dx = 0;
    private double dy = 0;
    private List<List<Double>> corner;

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
        LL3ADetections = new ArrayList<>();

        for (LLResultTypes.DetectorResult detection : detections) {
            int color = detection.getClassId(); // Detected class (color)

            boolean colorRight = true;
            for (int e: unwantedSamples) {
                if (color == e) {
                    colorRight = false;
                    break;
                }
            }

            if(colorRight) {
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
                double yDistance = limelightHeight * Math.atan(Math.toRadians(actualYAngle));
                double xDistance = Math.atan(Math.toRadians(detection.getTargetXDegrees())) * yDistance;

                // Calculate a final score
                double score = calculateScore(color, yDistance, xDistance, rotationScore, angleWeight, detections);

                if (LL3ADetections.isEmpty()) {
                    sampleAngle = Double.NaN;
                }

                if (corners == null || corners.size() < 4) {
                    sampleAngle = Double.NaN;
                }

                this.corner = corners;

                dx = Math.toRadians(corners.get(1).get(0) - corners.get(0).get(0));
                dy = Math.toRadians(corners.get(2).get(1) - corners.get(0).get(1));
                sampleAngle = ((Math.atan2(dy, dx) - angleOffset) * 4.5);

                // Add the scored detection to the list
                LL3ADetections.add(new LL3ADetection(detection, score, yDistance, xDistance, rotationScore));
            }
        }

        // Sort detections by score in descending order
        LL3ADetections.sort(Comparator.comparingDouble(LL3ADetection::getScore).reversed());

        // Display sorted results on telemetry
        if (!LL3ADetections.isEmpty()) {
            telemetry.addData("Best Detection", LL3ADetections.get(0).getDetection().getClassName());
            telemetry.addLine("Sorted Detections:");
            for (LL3ADetection scored : LL3ADetections) {
                LLResultTypes.DetectorResult det = scored.getDetection();
                telemetry.addData(det.getClassName(),
                        String.format("Score: %.2f | Position: (%.2f, %.2f) | Rotation: %.2f | XDeg %.2f: | YDeg: %.2f", scored.getScore(), scored.getXDistance(), scored.getYDistance(), Math.toDegrees(scored.getRotationScore()), det.getTargetXDegrees(), det.getTargetYDegrees()));
            }
        } else {
            telemetry.addData("Detections", "None (No matches for preferred color or too close)");
        }

        if (!LL3ADetections.isEmpty()) {
            double adjustedXDegrees = LL3ADetections.get(0).getDetection().getTargetXDegrees() - limelightRotationOffset;
            double actualYAngle = limelightAngle - LL3ADetections.get(0).getDetection().getTargetYDegrees();
            yDistance = limelightHeight * Math.tan(Math.toRadians(90) - Math.toRadians(actualYAngle));
            xDistance = Math.tan(Math.toRadians(adjustedXDegrees)) * yDistance;
        }
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

        for (int i : unwantedSamples)
            if (i == color) {
                score = -1000000000;
                break;
            }

        return score;
    }

    public Pose getAlignedPose(Pose currentPose) {
        double adjustedX = xDistance * Math.cos(Math.toRadians(currentPose.getHeading())) - yDistance * Math.sin(Math.toRadians(currentPose.getHeading()));
        double adjustedY = xDistance * Math.sin(Math.toRadians(currentPose.getHeading())) + yDistance * Math.cos(Math.toRadians(currentPose.getHeading()));

        // Adjust for the lateral offset of the claw
        adjustedX -= clawLateralOffset * Math.sin(Math.toRadians(currentPose.getHeading()));
        adjustedY += clawLateralOffset * Math.cos(Math.toRadians(currentPose.getHeading()));

        adjustedX -= clawDistance;

        adjustedX += currentPose.getX();
        adjustedY += currentPose.getY();

        return new Pose(adjustedX - lateralOffset, adjustedY - forwardOffset, currentPose.getHeading());
    }

    public void setLimelightRotationOffset(double limelightRotationOffset) {
        this.limelightRotationOffset = limelightRotationOffset;
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