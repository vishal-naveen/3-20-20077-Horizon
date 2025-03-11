package opmode.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import config.commands.Chamber;
import config.commands.ForwardChamber;
import config.commands.Specimen;
import config.commands.Submersible;
import config.commands.Transfer;
import config.core.util.Alliance;
import config.core.util.OpModeCommand;
import config.core.Robot;

@Autonomous(name = "7+0", group = "...Sigma")
public class SevenSpec extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, config.core.paths.SevenSpec.start, true, 1);
        r.getI().hover();
        r.getO().specimenScore0();
        r.getE().toZero();
        r.getT().addData("init", true);
        r.getT().update();

        schedule(
                new RunCommand(r::aPeriodic),
                new SequentialCommandGroup(
                        new ForwardChamber(r)
                                .alongWith(
                                        //        new WaitCommand(500)
                                        //                .andThen(
                                        new FollowPath(r.getF(), config.core.paths.SevenSpec.score1())
                                        //                )
                                ),
                        new InstantCommand(
                                () -> {
                                    r.getI().rotateDegrees(r.getM().getManualPoses().get(0).getRotation());//robot.getV().getBestDetectionAngle());
                                    r.getE().toFull();
                                    config.core.paths.SevenSpec.sub2 = r.getM().getManualPoses().get(0).getPose().copy();
                                }
                        )
                                .andThen(
                                        new FollowPath(r.getF(), config.core.paths.SevenSpec.sub2())
                                                .andThen(
                                                        new Submersible(r)
                                                )
                                ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.deposit2())
                                .alongWith(
                                        new Transfer(r)
                                                .andThen(
                                                        new InstantCommand(
                                                                () -> {
                                                                    r.getO().specimenGrab0();
                                                                    r.getO().close();
                                                                }
                                                        )
                                                )
                                ),
                        new WaitCommand(200)
                                .andThen(
                                        new InstantCommand(() -> r.getO().open()),
                                        new FollowPath(r.getF(), config.core.paths.SevenSpec.grab2()).setCompletionThreshold(0.975)
                                                .andThen(new InstantCommand(() -> r.getO().close()))
                                ),
                        new ForwardChamber(r)
                                .alongWith(
                                        new FollowPath(r.getF(), config.core.paths.SevenSpec.score2())
                                ),
                        new InstantCommand(
                                () -> {
                                    r.getI().rotateDegrees(r.getM().getManualPoses().get(1).getRotation());//robot.getV().getBestDetectionAngle());
                                    r.getE().toFull();
                                    config.core.paths.SevenSpec.sub3 = r.getM().getManualPoses().get(1).getPose().copy();
                                }
                        )
                                .andThen(
                                        new FollowPath(r.getF(), config.core.paths.SevenSpec.sub3())
                                                .andThen(
                                                        new Submersible(r)
                                                )
                                ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.deposit3())
                                .alongWith(
                                        new InstantCommand(() -> r.getI().hover()),
                                        new WaitCommand(500)
                                                .andThen(
                                                        new InstantCommand(() -> r.getE().toFull())
                                                )
                                )
                                .andThen(new InstantCommand(() -> r.getI().open())),
                        new SequentialCommandGroup(
                                new FollowPath(r.getF(), config.core.paths.SevenSpec.push0())
                                        .alongWith(
                                                new WaitCommand(250)
                                                        .andThen(
                                                                new InstantCommand(() -> r.getI().drag()),
                                                                new InstantCommand(() -> r.getE().toQuarter())
                                                        )
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SevenSpec.push1())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SevenSpec.push2())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toQuarter())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SevenSpec.push3())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SevenSpec.push4())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toQuarter())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SevenSpec.push5())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new InstantCommand(() -> r.getI().specimen()),
                                new WaitCommand(500),
                                new InstantCommand(() -> r.getE().toZero()),
                                new InstantCommand(() -> r.getO().specimenGrab180())
                        ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.grab3()),
                        new Chamber(r)
                                .alongWith(
                                        new WaitCommand(300)
                                                .andThen(
                                                        new FollowPath(r.getF(), config.core.paths.SevenSpec.score3()).setCompletionThreshold(0.975)
                                                )
                                ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.grab4(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SevenSpec.score4(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.grab5(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SevenSpec.score5(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.grab6(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SevenSpec.score6(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.grab7(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SevenSpec.score7(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SevenSpec.park(), true, 1)
                                .alongWith(
                                        new InstantCommand(
                                                () -> {
                                                    r.getO().transfer();
                                                    r.getI().hover();
                                                }
                                        ),
                                        new WaitCommand(500)
                                                .andThen(
                                                        new InstantCommand(() -> r.getE().toFull())
                                                )
                                )
                )
        );
    }

    @Override
    public void init_loop() {
        super.init_loop();
        r.aInitLoop(gamepad2);
    }
}
