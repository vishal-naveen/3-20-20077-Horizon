package opmode.auto;

import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.paths.SevenSpec;
import config.core.Robot;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Seven Spec Pathing")
public class SevenSpecPathing extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SevenSpec.start);
        r.getT().addData("init", true);
        r.getT().update();

        schedule(
                new RunCommand(r::periodic),
                new SequentialCommandGroup(
                        new FollowPath(r.getF(), SevenSpec.score1(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.grab2(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.score2(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.grab3(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.score3(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.grab4(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.score4(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.push(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.grab5(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.score5(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.grab6(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.score6(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.grab7(), true, 1),
                        new FollowPath(r.getF(), SevenSpec.score7(), true, 1)
                )
        );
    }
}