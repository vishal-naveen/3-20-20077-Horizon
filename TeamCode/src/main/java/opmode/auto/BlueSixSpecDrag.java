package opmode.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import config.commands.Align;
import config.commands.Chamber;
import config.commands.ForwardChamber;
import config.commands.Specimen;
import config.commands.Submersible;
import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.Robot;
import config.core.paths.SixSpecDrag;

@Autonomous(name = "Blue Six Spec Drag", group = "...Sigma")
public class BlueSixSpecDrag extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SixSpecDrag.start, true, 1);
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
                                        new WaitCommand(500)
                                                .andThen(
                                                        new FollowPath(r.getF(), SixSpecDrag.score1())
                                                )
                                ),
                        new Align(r, r.getM().getManualPoses().get(0))
                                .andThen(
                                        new Submersible(r)
                                ),
                        new FollowPath(r.getF(), SixSpecDrag.deposit2())
                                .alongWith(
                                        new InstantCommand(() -> r.getI().hover()),
                                        new WaitCommand(500)
                                                .andThen(
                                                        new InstantCommand(() -> r.getE().toFull())
                                                )
                                )
                                .andThen(new InstantCommand(() -> r.getI().open())),
                        new SequentialCommandGroup(
                                new FollowPath(r.getF(), SixSpecDrag.push0())
                                        .alongWith(
                                                new WaitCommand(250)
                                                        .andThen(
                                                                new InstantCommand(() -> r.getI().drag()),
                                                                new InstantCommand(() -> r.getE().toQuarter())
                                                        )
                                        ),
                                new FollowPath(r.getF(), SixSpecDrag.push1())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new FollowPath(r.getF(), SixSpecDrag.push2())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toQuarter())
                                        ),
                                new FollowPath(r.getF(), SixSpecDrag.push3())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new FollowPath(r.getF(), SixSpecDrag.push4())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toQuarter())
                                        ),
                                new FollowPath(r.getF(), SixSpecDrag.push5())
                                        .alongWith(
                                                new InstantCommand(() -> r.getE().toFull())
                                        ),
                                new InstantCommand(() -> r.getI().specimen()),
                                new WaitCommand(500),
                                new InstantCommand(() -> r.getE().toZero()),
                                new InstantCommand(() -> r.getO().specimenGrab180())
                        ),
                        new FollowPath(r.getF(), SixSpecDrag.grab2()),
                        new Chamber(r)
                                .alongWith(
                                        new WaitCommand(300)
                                                .andThen(
                                                        new FollowPath(r.getF(), SixSpecDrag.score2()).setCompletionThreshold(0.975)
                                                )
                                ),
                        new FollowPath(r.getF(), SixSpecDrag.grab3(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), SixSpecDrag.score3(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), SixSpecDrag.grab4(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), SixSpecDrag.score4(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), SixSpecDrag.grab5(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), SixSpecDrag.score5(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), SixSpecDrag.grab6(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), SixSpecDrag.score6(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), SixSpecDrag.park(), true, 1)
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
