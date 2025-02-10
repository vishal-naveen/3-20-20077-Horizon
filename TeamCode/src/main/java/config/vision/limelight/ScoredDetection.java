package config.vision.limelight;

import com.qualcomm.hardware.limelightvision.LLResultTypes;

public class ScoredDetection {
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
