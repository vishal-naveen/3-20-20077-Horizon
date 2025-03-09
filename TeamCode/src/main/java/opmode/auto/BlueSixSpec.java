package opmode.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import config.commands.Align;
import config.commands.ForwardChamber;
import config.commands.Submersible;
import config.commands.Transfer;
import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.Robot;
import config.core.paths.FiveSpec;
import config.core.paths.SixSpec;

@Autonomous
public class BlueSixSpec extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SixSpec.start);
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
                                                    new FollowPath(r.getF(), SixSpec.score1())
                                                )
                                ),
                     //   new Align(r)
                       //         .andThen(
                                    new Submersible(r),
                      //          ),
                        new FollowPath(r.getF(),SixSpec.deposit2())
                                .alongWith(
                                    new WaitCommand(500)
                                        .andThen(
                                                new InstantCommand(() -> r.getE().toFull())
                                        )
                                )
                                .andThen(new InstantCommand(() -> r.getI().open())),
                        new FollowPath(r.getF(), SixSpec.push())
                                .alongWith(
                                        new WaitCommand(250)
                                                .andThen(
                                                        new InstantCommand(() -> r.getI().open())
                                                                .andThen(
                                                                        new WaitCommand(250)
                                                                                .andThen(new InstantCommand(() -> r.getI().specimen()))
                                                                ),
                                                        new WaitCommand(250),
                                                        new InstantCommand(() -> r.getE().toZero()),
                                                        new InstantCommand(() -> r.getO().specimenGrab180())
                                                )
                                )
                )
        );
    }
}
