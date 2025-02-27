package opmode.auto;

import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.paths.FiveSpecOneSample;
import config.core.Robot;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "5+1 Pathing")
public class FiveSpecOneSamplePathing extends OpModeCommand {
    //    Follower f;
    Robot r;

    @Override
    public void initialize() {

        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, FiveSpecOneSample.start);
        r.getT().addData("init", true);
        r.getT().update();

        schedule(
                new RunCommand(r::aPeriodic),
                new SequentialCommandGroup(
                        new FollowPath(r.getF(), FiveSpecOneSample.score1(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.push(), true, 1).setCompletionThreshold(0.95),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab2(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.score2(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab3(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.score3(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab4(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.score4(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab5(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.score5(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab6(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.score6(), true, 1),
                        new FollowPath(r.getF(), FiveSpecOneSample.park(), true, 1)
                )
        );
    }
}