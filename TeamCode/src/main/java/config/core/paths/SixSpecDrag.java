package config.core.paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class SixSpecDrag {

    public static Pose start = new Pose(8, 65.5, Math.toRadians(0));
    public static Pose score1 = new Pose(42, 66, Math.toRadians(0));
    public static Pose sub2 = new Pose(25, 66, Math.toRadians(0)); // 48 + 2.5 - 18.5 - 7
    public static Pose deposit2 = new Pose(24,48, Math.toRadians(225));
    public static Pose grab2 = new Pose(7.75, 36, Math.toRadians(180));
    public static Pose score2 = new Pose(39, 66, Math.toRadians(180));
    public static Pose grab3 = new Pose(7.75, 36, Math.toRadians(180));
    public static Pose score3 = new Pose(39, 66, Math.toRadians(180));
    public static Pose grab4 = new Pose(7.75, 36, Math.toRadians(180));
    public static Pose score4 = new Pose(39, 66, Math.toRadians(180));
    public static Pose grab5 = new Pose(7.75, 36, Math.toRadians(180));
    public static Pose score5 = new Pose(39, 66, Math.toRadians(180));
    public static Pose grab6 = new Pose(7.75, 36, Math.toRadians(180));
    public static Pose score6 = new Pose(39, 66, Math.toRadians(270));
    public static Pose park = new Pose(24,48, Math.toRadians(225));

    public static PathChain score1() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(start),
                                new Point(score1)
                        )
                )
                .setConstantHeadingInterpolation(start.getHeading())
                .setZeroPowerAccelerationMultiplier(3)
                .build();
    }

    public static PathChain sub2() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(score1),
                                new Point(sub2)
                        )
                )
                .setConstantHeadingInterpolation(start.getHeading())
                .setZeroPowerAccelerationMultiplier(3)
                .build();

    }

    public static PathChain deposit2() {
        return new PathBuilder()
                .addPath(new BezierCurve(sub2, new Pose(sub2.getX() - 10, sub2.getY() + 10), deposit2))
                .setLinearHeadingInterpolation(sub2.getHeading(), deposit2.getHeading())
                .setZeroPowerAccelerationMultiplier(4)
                .build();
    }

    public static PathChain push0() {
        return new PathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(deposit2),
                                new Point(32.500, 44.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(deposit2.getHeading(), Math.toRadians(315))
                .build();
    }

    public static PathChain push1() {
        return new PathBuilder()
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(32.500, 44.000, Point.CARTESIAN),
                                new Point(15.500, 41.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(315), Math.toRadians(265))
                .build();

    }

    public static PathChain push2() {
        return new PathBuilder()
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(15.500, 41.000, Point.CARTESIAN),
                                new Point(36.000, 35.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(265), Math.toRadians(300))
                .build();
    }

        public static PathChain push3() {
            return new PathBuilder()
                    .addPath(
                            // Line 4
                            new BezierLine(
                                    new Point(36.000, 35.500, Point.CARTESIAN),
                                    new Point(17.750, 35.750, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(300), Math.toRadians(260))
                    .build();
        }

        public static PathChain push4() {
            return new PathBuilder()
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(17.750, 35.750, Point.CARTESIAN),
                                new Point(36.000, 27.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(260), Math.toRadians(297))
                    .build();
        }

        public static PathChain push5() {
            return new PathBuilder()
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(36.000, 27.000, Point.CARTESIAN),
                                new Point(21.000, 30.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(297), Math.toRadians(250))
                .build();
        }

    public static PathChain grab2() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(21, 30, Point.CARTESIAN),
                                new Point(grab2.getX() + 10, grab2.getY()),
                                new Point(grab2)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(250), Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain score2() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                grab2,
                                new Pose(score2.getX() - 10, score2.getY()),
                                score2
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
    }

    public static PathChain grab3() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                score2,
                                grab3
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain score3() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                grab3,
                                new Pose(score3.getX() - 10, score3.getY()),
                                score3
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
    }

    public static PathChain grab4() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                score3,
                                grab4
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain score4() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                grab4,
                                new Pose(score4.getX() - 10, score4.getY()),
                                score4
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
    }

    public static PathChain grab5() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                score4,
                                grab5
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain score5() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                grab5,
                                new Pose(score5.getX() - 10, score5.getY()),
                                score5
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
    }

    public static PathChain grab6() {
        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                score5,
                                grab6
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain score6() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                grab6,
                                new Pose(score6.getX() - 10, score6.getY()),
                                score6
                        )
                )
                .setZeroPowerAccelerationMultiplier(6)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    public static PathChain park() {
        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                score6,
                                new Pose(score6.getX() - 10, score6.getY()),
                                park
                        )
                )
                .setLinearHeadingInterpolation(score6.getHeading(), park.getHeading())
                .setZeroPowerAccelerationMultiplier(7)
                .build();
    }
}


