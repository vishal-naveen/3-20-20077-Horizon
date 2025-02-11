package opmode.auto;

import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.paths.SevenSpec;
import config.core.Robot;
import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Seven Spec Pathing")
public class SevenSpecPathing extends OpModeCommand {
    Follower f;
    public static String currentPath = "";

    @Override
    public void initialize() {

//        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SevenSpec.start);
//        r.getT().addData("init", true);
//        r.getT().update();

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(hardwareMap);
        f.setStartingPose(SevenSpec.start);

        schedule(
                new RunCommand(f::update),
                new SequentialCommandGroup(
                        new FollowPath(f, SevenSpec.score1(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score1")),
                        new FollowPath(f, SevenSpec.grab2(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab2")),
                        new FollowPath(f, SevenSpec.score2(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score2")),
                        new FollowPath(f, SevenSpec.grab3(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab3")),
                        new FollowPath(f, SevenSpec.score3(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score3")),
                        new FollowPath(f, SevenSpec.grab4(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab4")),
                        new FollowPath(f, SevenSpec.score4(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score4")),
                        new FollowPath(f, SevenSpec.push(), true, 1).setCompletionThreshold(0.95).alongWith(new InstantCommand(() -> currentPath = "push")),
                        new FollowPath(f, SevenSpec.grab5(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab5")),
                        new FollowPath(f, SevenSpec.score5(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score5")),
                        new FollowPath(f, SevenSpec.grab6(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab6")),
                        new FollowPath(f, SevenSpec.score6(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score6")),
                        new FollowPath(f, SevenSpec.grab7(), true, 1).alongWith(new InstantCommand(() -> currentPath = "grab7")),
                        new FollowPath(f, SevenSpec.score7(), true, 1).alongWith(new InstantCommand(() -> currentPath = "score7"))
                ),

                new RunCommand(() -> telemetry.addData("curr path", currentPath)),
                new RunCommand(() -> f.telemetryDebug(telemetry)),
                new RunCommand(() -> telemetry.update())
        );
    }
}