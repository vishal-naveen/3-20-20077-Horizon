package indubitables.config.core.paths;

import android.graphics.Path;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class SevenSpec {

    public static Pose startPose = new Pose(7, 66, Math.toRadians(180));

    public static PathChain score1() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(7.000, 66.000, Point.CARTESIAN),
                                new Point(40.000, 75.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
    }

    public static PathChain grab2() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(40.000, 75.000, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(16.000, 36.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain score2() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 36.000, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(40.000, 72.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain grab3() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(40.000, 72.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(16.000, 36.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain score3() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 36.000, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(40.000, 70.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain grab4() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(40.000, 70.000, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(16.000, 36.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain score4() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 36.000, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(37.500, 67.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .build();
    }

    public static PathChain push() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(40.000, 67.500, Point.CARTESIAN),
                                new Point(28.500, 17.000, Point.CARTESIAN),
                                new Point(66.500, 44.000, Point.CARTESIAN),
                                new Point(55.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(55.000, 24.000, Point.CARTESIAN),
                                new Point(16.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 24.000, Point.CARTESIAN),
                                new Point(70.000, 24.000, Point.CARTESIAN),
                                new Point(55.000, 13.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(55.000, 13.000, Point.CARTESIAN),
                                new Point(16.000, 13.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 13.000, Point.CARTESIAN),
                                new Point(70.000, 24.000, Point.CARTESIAN),
                                new Point(55.000, 6.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(55.000, 6.500, Point.CARTESIAN),
                                new Point(8.500, 6.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain score5() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.500, 6.500, Point.CARTESIAN),
                                new Point(37.500, 65.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain grab6() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(37.500, 65.000, Point.CARTESIAN),
                                new Point(8.500, 36.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain score6() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.500, 36.000, Point.CARTESIAN),
                                new Point(37.500, 62.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain grab7() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(37.500, 62.500, Point.CARTESIAN),
                                new Point(8.500, 36.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain score7() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.500, 36.000, Point.CARTESIAN),
                                new Point(37.500, 60.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }
}
