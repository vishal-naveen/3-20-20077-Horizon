package indubitables.config.core.paths;

import static indubitables.config.core.FieldConstants.*;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class FiveSpec {

    public static Pose observationStartPose = new Pose(7, 66, Math.toRadians(180));
    public static Pose observationPreloadPose = new Pose(38.5, 75, Math.toRadians(180));

    public static Pose observationSpecimenPickupPose = new Pose(6.5, 35, Math.toRadians(180));
    public static Pose observationSpecimenPickup2Pose = new Pose(6.5, 35, Math.toRadians(180));
    public static Pose observationSpecimenPickup3Pose = new Pose(6.5, 35, Math.toRadians(180));
    public static Pose observationSpecimenPickup4Pose = new Pose(6.5, 35, Math.toRadians(180));

    public static Pose observationSpecimen1Pose = new Pose(38, 74, Math.toRadians(180));
    public static Pose observationSpecimen2Pose = new Pose(38, 72, Math.toRadians(180));
    public static Pose observationSpecimen3Pose = new Pose(38, 69, Math.toRadians(180));
    public static Pose observationSpecimen4Pose = new Pose(38, 66, Math.toRadians(180));

    public static Pose observationParkPose = new Pose(24, 44, Math.toRadians(215));
    
    public static Pose startPose = new Pose(7, 66, Math.toRadians(180));
    public static Pose preloadPose = new Pose(38.5, 75, Math.toRadians(180));
    public static Pose grab1Pose = observationSpecimenPickupPose;
    public static Pose grab2Pose = observationSpecimenPickup2Pose;
    public static Pose grab3Pose = observationSpecimenPickup3Pose;
    public static Pose grab4Pose = observationSpecimenPickup4Pose;
    public static Pose specimen1Pose = observationSpecimen1Pose;
    public static Pose specimen2Pose = observationSpecimen2Pose;
    public static Pose specimen3Pose = observationSpecimen3Pose;
    public static Pose specimen4Pose = observationSpecimen4Pose;
    public static Pose parkPose = observationParkPose;

    public static PathChain preload() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(preloadPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), preloadPose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }

    public static PathChain pushSamples() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(preloadPose), new Point(15, 36, Point.CARTESIAN), new Point(59, 36.25, Point.CARTESIAN), new Point(56, 26.000, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(preloadPose.getHeading(), Math.toRadians(180))
                .addPath(new BezierLine(new Point(56.000, 26.000, Point.CARTESIAN), new Point(28, 26.000, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(new BezierCurve(new Point(28, 26.000, Point.CARTESIAN), new Point(52.000, 26.000, Point.CARTESIAN), new Point(56.000, 15.000, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(new BezierLine(new Point(56.000, 15.000, Point.CARTESIAN), new Point(28, 15.000, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(new BezierCurve(new Point(28, 15.000, Point.CARTESIAN), new Point(56.000, 10.000, Point.CARTESIAN), new Point(56.000, 8, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(new BezierLine(new Point(56.000, 8, Point.CARTESIAN), new Point(26, 8, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(new BezierCurve(new Point(26, 8, Point.CARTESIAN), new Point(35, 20, Point.CARTESIAN), new Point(grab1Pose)))
                .setLinearHeadingInterpolation(Math.toRadians(180), grab1Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }

    public static PathChain specimen1() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(grab1Pose), new Point(specimen1Pose.getX() - 10, specimen1Pose.getY(), Point.CARTESIAN), new Point(specimen1Pose)))
                .setLinearHeadingInterpolation(grab1Pose.getHeading(), specimen1Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }

    public static PathChain grab2() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(specimen1Pose), new Point(grab2Pose)))
                .setLinearHeadingInterpolation(specimen1Pose.getHeading(), grab2Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(2.5)
                .build();
    }

    public static PathChain specimen2() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(grab2Pose), new Point(specimen2Pose.getX() - 10, specimen2Pose.getY(), Point.CARTESIAN), new Point(specimen2Pose)))
                .setLinearHeadingInterpolation(grab2Pose.getHeading(), specimen2Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }

    public static PathChain grab3() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(specimen2Pose), new Point(grab3Pose)))
                .setLinearHeadingInterpolation(specimen2Pose.getHeading(), grab3Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(2.5)
                .build();
    }

    public static PathChain specimen3() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(grab3Pose), new Point(specimen3Pose.getX() - 10, specimen3Pose.getY(), Point.CARTESIAN), new Point(specimen3Pose)))
                .setLinearHeadingInterpolation(grab3Pose.getHeading(), specimen3Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }

    public static PathChain grab4() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(specimen3Pose), new Point(grab4Pose)))
                .setLinearHeadingInterpolation(specimen3Pose.getHeading(), grab4Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(2.5)
                .build();
    }

    public static PathChain specimen4() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(grab4Pose), new Point(specimen4Pose.getX() - 10, specimen3Pose.getY(), Point.CARTESIAN), new Point(specimen3Pose)))
                .setLinearHeadingInterpolation(grab4Pose.getHeading(), specimen4Pose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }

    public static PathChain park() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(specimen4Pose), new Point(parkPose)))
                .setLinearHeadingInterpolation(specimen4Pose.getHeading(), parkPose.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }
}
