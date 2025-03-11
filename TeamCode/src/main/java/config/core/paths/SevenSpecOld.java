package config.core.paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class SevenSpecOld {

    public static Pose start = new Pose(8, 66, Math.toRadians(0));
    public static Pose score1 = new Pose(40, 75, Math.toRadians(0));
    public static Pose grab2 = new Pose(16, 36, Math.toRadians(0));
    public static Pose score2 = new Pose(40, 72.5, Math.toRadians(0));
    public static Pose grab3 = new Pose(16, 36, Math.toRadians(0));
    public static Pose score3 = new Pose(40, 70, Math.toRadians(0));
    public static Pose grab4 = new Pose(16, 36, Math.toRadians(0));
    public static Pose score4 = new Pose(40, 67.5, Math.toRadians(0));
    public static Pose grab5 = new Pose(8.5, 36, Math.toRadians(180));
    public static Pose score5 = new Pose(37.5, 65, Math.toRadians(180));
    public static Pose grab6 = new Pose(8.5, 36, Math.toRadians(180));
    public static Pose score6 = new Pose(37.5, 62.5, Math.toRadians(180));
    public static Pose grab7 = new Pose(8.5, 36, Math.toRadians(180));
    public static Pose score7 = new Pose(37.5, 60, Math.toRadians(180));

    public static PathChain score1() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(start),
                                new Point(score1)
                        )
                )
                .setConstantHeadingInterpolation(start.getHeading())
                .build();
    }

    public static PathChain grab2() {

        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score1),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(grab2)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain score2() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(grab2),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(score2)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain grab3() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score2),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(grab3)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain score3() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(grab3),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(score3)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain grab4() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score3),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(grab4)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public static PathChain score4() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(grab4),
                                new Point(38.500, 35.500, Point.CARTESIAN),
                                new Point(20.000, 70.500, Point.CARTESIAN),
                                new Point(score4)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .build();
    }

    public static PathChain push() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score4),
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
                                new Point(55.000, 9, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(55.000, 9, Point.CARTESIAN),
                                new Point(16.000, 9, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain grab5() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 9, Point.CARTESIAN),
                                new Point(20, 24, Point.CARTESIAN),
                                new Point(grab5)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain score5() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(grab5),
                                new Point(score5)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain grab6() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(score5),
                                new Point(grab6)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain score6() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(grab6),
                                new Point(score6)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain grab7() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(score6),
                                new Point(grab7)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain score7() {

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(grab7),
                                new Point(score7)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }
}


