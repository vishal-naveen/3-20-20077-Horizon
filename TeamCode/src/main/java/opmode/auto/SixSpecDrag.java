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
import config.core.util.Alliance;
import config.core.util.OpModeCommand;
import config.core.Robot;

@Autonomous(name = "6+0 Drag", group = "...Sigma")
public class SixSpecDrag extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, config.core.paths.SixSpecDrag.start, true, 1);
        r.getI().init();
        r.getO().specimenScore0();
        r.getE().toZero();
        r.getT().addData("init", true);
        r.getT().update();

        schedule(
                new RunCommand(r::aPeriodic),
                new SequentialCommandGroup(
                        new ForwardChamber(r)
                                .alongWith(
                                        new WaitCommand(500)
                                                .andThen(
                                                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.score1())
                                                ),
                                        new WaitCommand(100)
                                                .andThen(
                                                        new InstantCommand(
                                                                () -> {
                                                                    r.getI().hover();
                                                                    r.getE().toFull();
                                                                }
                                                        )
                                                )
                                ),
                        new InstantCommand(
                                () -> {
                                    r.getI().rotateDegrees(r.getM().getManualPoses().get(0).getRotation());//robot.getV().getBestDetectionAngle());
                                    r.getE().toFull();
                                    config.core.paths.SixSpecDrag.sub2 = r.getM().getManualPoses().get(0).getPose().copy();
                                }
                        )
                                .andThen(
                                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.sub2())
                                                .andThen(
                                                        new Submersible(r)
                                                )
                                ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.deposit2())
                                .alongWith(
                                        new InstantCommand(() -> r.getI().hover()),
                                        new WaitCommand(500)
                                                .andThen(
                                                        new InstantCommand(() -> r.getE().toFull())
                                                )
                                )
                                .andThen(new InstantCommand(() -> r.getI().open())),
                        new SequentialCommandGroup(
                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.push0())
                                        .alongWith(
                                                new WaitCommand(250)
                                                        .andThen(
                                                                new InstantCommand(() -> r.getI().drag()),
                                                                new InstantCommand(() -> r.getE().toQuarter())
                                                        )
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.push1())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.push2())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toQuarter())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.push3())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.push4())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toQuarter())
                                        ),
                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.push5())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new InstantCommand(() -> r.getI().specimen()),
                                new WaitCommand(500),
                                new InstantCommand(() -> r.getE().toZero()),
                                new InstantCommand(() -> r.getO().specimenGrab180())
                        ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.grab2()),
                        new Chamber(r)
                                .alongWith(
                                        new WaitCommand(300)
                                                .andThen(
                                                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.score2()).setCompletionThreshold(0.975)
                                                )
                                ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.grab3(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.score3(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.grab4(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.score4(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.grab5(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.score5(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.grab6(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), config.core.paths.SixSpecDrag.score6(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), config.core.paths.SixSpecDrag.park(), true, 1)
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
