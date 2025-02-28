
package opmode.auto;

import static java.lang.Thread.sleep;

import config.commands.Chamber;
import config.commands.Preload;
import config.commands.Specimen;
import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.Robot;
import config.core.paths.FiveSpecOneSample;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "5+1")
public class BlueFiveSpecOneSample extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, FiveSpecOneSample.start);

        schedule(
                new RunCommand(r::aPeriodic),
                new SequentialCommandGroup(
                        new Preload(r).alongWith(
                                new WaitCommand(500)
                                        .andThen(
                                                new FollowPath(r.getF(), FiveSpecOneSample.score1(), true, 1),
                                                new InstantCommand(() -> r.getO().open()),
                                                new FollowPath(r.getF(), FiveSpecOneSample.push(), true, 1).setCompletionThreshold(0.95)
                                                        .alongWith(
                                                                new WaitCommand(500)
                                                                        .andThen(
                                                                                new Specimen(r)
                                                                        )
                                                        )
                                        )

                        ),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab2(), true, 1),
                        new Chamber(r).alongWith(
                                new WaitCommand(450)
                                        .andThen(
                                                new FollowPath(r.getF(), FiveSpecOneSample.score2(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab3(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), FiveSpecOneSample.score3(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab4(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), FiveSpecOneSample.score4(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab5(), true, 1)
                                .alongWith(new Specimen(r)),
                        new Chamber(r).alongWith(
                                new WaitCommand(250)
                                        .andThen(
                                                new FollowPath(r.getF(), FiveSpecOneSample.score5(), true, 1).setCompletionThreshold(0.975)
                                        )
                        ),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab6(), true, 1)
                                .alongWith(new Specimen(r)),
                        new InstantCommand(() -> r.getO().close())
                                .andThen(
                                        new WaitCommand(250),
                                        new FollowPath(r.getF(), FiveSpecOneSample.score6(), true, 1).setCompletionThreshold(0.975)
                                                .alongWith(
                                                        new InstantCommand(() -> r.getO().score()),
                                                        new WaitCommand(250)
                                                                .andThen(
                                                                        new InstantCommand(() -> r.getI().cloud()),
                                                                        new InstantCommand(() -> r.getO().score()),
                                                                        new InstantCommand(() -> r.getL().toHighBucket())
                                                                )
                                                ),
                                        new InstantCommand(() -> r.getO().open())
                                                .andThen(
                                                        new WaitCommand(450),
                                                        new InstantCommand(() -> r.getL().pidOff())
                                                                .alongWith(
                                                                        new FollowPath(r.getF(), FiveSpecOneSample.park(), true, 1),
                                                                        new InstantCommand(() -> r.getI().hover()),
                                                                        new InstantCommand(() -> r.getE().toFull())
                                                                )
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