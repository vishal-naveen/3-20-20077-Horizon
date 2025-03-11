//package opmode.auto;
//
//import config.commands.Chamber;
//import config.commands.Specimen;
//import config.core.util.Alliance;
//import config.core.util.OpModeCommand;
//import config.core.Robot;
//
//import com.arcrobotics.ftclib.command.*;
//import com.pedropathing.commands.FollowPath;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//@Autonomous(name = "5+0", group = "UnSigma")
//public class FiveSpec extends OpModeCommand {
//    Robot r;
//
//    @Override
//    public void initialize() {
//        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, config.core.paths.FiveSpec.startPose);
//        r.getI().specimen();
//        r.getT().addData("init", true);
//        r.getT().update();
//    }
//
//    @Override
//    public void start() {
//        schedule(
//                new RunCommand(r::aPeriodic),
//                new SequentialCommandGroup(
//                        new Chamber(r),
//                        new FollowPath(r.getF(), config.core.paths.FiveSpec.preload(), true, 1).alongWith(new InstantCommand(() -> r.getT().addData("f", true))),
//                        new FollowPath(r.getF(), config.core.paths.FiveSpec.pushSamples(), true, 1)
//                                .alongWith(
//                                        new SequentialCommandGroup(
//                                                new OuttakeOpen(r),
//                                                new WaitCommand(500),
//                                                new Specimen(r)
//                                        )
//                                ),
//                        new WaitCommand(500).andThen(new OuttakeClose(r)),
//                        new WaitCommand(250)
//                                .andThen(
//                                        new FollowPath(r.getF(), config.core.paths.FiveSpec.specimen1(), true, 1)
//                                                .alongWith(new Chamber(r))
//                                ),
//                        new FollowPath(r.getF(), config.core.paths.FiveSpec.grab2(), true, 1)
//                                .alongWith(
//                                        new SequentialCommandGroup(
//                                                new WaitCommand(700)
//                                                        .andThen(new Specimen(r)),
//                                                new WaitCommand(1500)
//                                                        .andThen(new OuttakeClose(r))
//                                        )
//                                ),
//                        new WaitCommand(250)
//                                .andThen(
//                                        new FollowPath(r.getF(), config.core.paths.FiveSpec.specimen2(), true, 1)
//                                )
//                                .alongWith(
//                                        new Chamber(r)
//                                ),
//                        new FollowPath(r.getF(), config.core.paths.FiveSpec.grab3(), true, 1)
//                                .alongWith(
//                                        new SequentialCommandGroup(
//                                                new WaitCommand(700)
//                                                        .andThen(new Specimen(r)),
//                                                new WaitCommand(1500)
//                                                        .andThen(new OuttakeClose(r))
//                                        )
//                                ),
//                        new WaitCommand(250)
//                                .andThen(
//                                        new FollowPath(r.getF(), config.core.paths.FiveSpec.specimen3(), true, 1)
//                                )
//                                .alongWith(
//                                        new Chamber(r)
//                                ),
//                        new FollowPath(r.getF(), config.core.paths.FiveSpec.grab4(), true, 1)
//                                .alongWith(
//                                        new SequentialCommandGroup(
//                                                new WaitCommand(700)
//                                                        .andThen(new Specimen(r)),
//                                                new WaitCommand(1500)
//                                                        .andThen(new OuttakeClose(r))
//                                        )
//                                ),
//                        new WaitCommand(250)
//                                .andThen(
//                                        new FollowPath(r.getF(), config.core.paths.FiveSpec.specimen4(), true, 1)
//                                )
//                                .alongWith(
//                                        new Chamber(r)
//                                ),
//                        new FollowPath(r.getF(), config.core.paths.FiveSpec.park(), true, 1)
//                                .alongWith(
//                                        new SequentialCommandGroup(
//                                                new OuttakeOpen(r),
//                                                new ExtendFull(r),
//                                                new InstantCommand(() -> r.getI().hover())
//                                        )
//                                )
//                )
//        );
//    }
//
//    @Override
//    public void loop() {
//        super.loop();
//        r.aPeriodic();
//    }
//
//    @Override
//    public void stop() {
//        super.stop();
//        r.stop();
//    }
//}
