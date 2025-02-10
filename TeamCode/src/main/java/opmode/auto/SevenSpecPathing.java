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
                new RunCommand(() -> f.telemetryDebug(telemetry)),
                new SequentialCommandGroup(
                        new FollowPath(f, SevenSpec.score1(f), true, 1),
                        new FollowPath(f, SevenSpec.grab2(), true, 1),
                        new FollowPath(f, SevenSpec.score2(), true, 1),
                        new FollowPath(f, SevenSpec.grab3(), true, 1),
                        new FollowPath(f, SevenSpec.score3(), true, 1),
                        new FollowPath(f, SevenSpec.grab4(), true, 1),
                        new FollowPath(f, SevenSpec.score4(), true, 1),
                        new FollowPath(f, SevenSpec.push(), true, 1),
                        new FollowPath(f, SevenSpec.grab5(), true, 1),
                        new FollowPath(f, SevenSpec.score5(), true, 1),
                        new FollowPath(f, SevenSpec.grab6(), true, 1),
                        new FollowPath(f, SevenSpec.score6(), true, 1),
                        new FollowPath(f, SevenSpec.grab7(), true, 1),
                        new FollowPath(f, SevenSpec.score7(), true, 1)
                ),
                new RunCommand(() -> telemetry.addData("Localizer", FollowerConstants.localizers)),
                new RunCommand(() -> telemetry.update())
        );
    }
}