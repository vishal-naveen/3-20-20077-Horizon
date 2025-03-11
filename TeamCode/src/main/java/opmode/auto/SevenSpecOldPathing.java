package opmode.auto;

import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.paths.SevenSpecOld;
import config.core.Robot;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Seven Spec Pathing")
public class SevenSpecOldPathing extends OpModeCommand {
    Robot r;
    public static String currentPath = "";

    @Override
    public void initialize() {

        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SevenSpecOld.start);
        r.getT().addData("init", true);
        r.getT().update();

        schedule(
                new RunCommand(r::aPeriodic),
                new SequentialCommandGroup(
                        new FollowPath(r.getF(), SevenSpecOld.score1(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score1")),
                        new FollowPath(r.getF(), SevenSpecOld.grab2(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab2")),
                        new FollowPath(r.getF(), SevenSpecOld.score2(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score2")),
                        new FollowPath(r.getF(), SevenSpecOld.grab3(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab3")),
                        new FollowPath(r.getF(), SevenSpecOld.score3(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score3")),
                        new FollowPath(r.getF(), SevenSpecOld.grab4(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab4")),
                        new FollowPath(r.getF(), SevenSpecOld.score4(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score4")),
                        new FollowPath(r.getF(), SevenSpecOld.push(), true, 1).setCompletionThreshold(0.95).alongWith(new InstantCommand(() -> currentPath = "push")),
                        new FollowPath(r.getF(), SevenSpecOld.grab5(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab5")),
                        new FollowPath(r.getF(), SevenSpecOld.score5(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score5")),
                        new FollowPath(r.getF(), SevenSpecOld.grab6(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab6")),
                        new FollowPath(r.getF(), SevenSpecOld.score6(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score6")),
                        new FollowPath(r.getF(), SevenSpecOld.grab7(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab7")),
                        new FollowPath(r.getF(), SevenSpecOld.score7(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score7"))
                )
        );
    }
}