package indubitables.config.core;

import com.acmerobotics.dashboard.config.Config;

import com.pedropathing.localization.Pose;

@Config
public class FieldConstants {

    public enum RobotStart {
        BUCKET,
        OBSERVATION,
    }

    // Bucket Poses
    public static Pose bucketStartPose = new Pose(7.5, 78.75, Math.toRadians(270));

    public static Pose bucketLeftSampleGrabPose = new Pose(35.1, 120.8, Math.toRadians(0));
    public static Pose bucketMidSampleGrabPose = new Pose(34.8, 131.4, Math.toRadians(0));
    public static Pose bucketRightSampleGrabPose = new Pose(45.4, 129.4, Math.toRadians(90));

    public static Pose bucketScorePose = new Pose(16, 128, Math.toRadians(315));

    public static Pose bucketParkPose = new Pose(60.7, 95.6, Math.toRadians(90));


}