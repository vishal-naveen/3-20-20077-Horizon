
package opmode.auto;

import config.commands.Chamber;
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
        r.getO().specimenGrab180();
        r.getO().close();
        r.getI().specimen();
        r.getE().toZero();
        r.getT().addData("init", true);
        r.getT().update();

        schedule(
                new RunCommand(r::aPeriodic),
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new Chamber(r),
                                new FollowPath(r.getF(), FiveSpecOneSample.score1(), true, 1)
                                        .andThen(
                                                new FollowPath(r.getF(), FiveSpecOneSample.push(), true, 1).setCompletionThreshold(0.95))
                        ),
                        new FollowPath(r.getF(), FiveSpecOneSample.grab2(), true, 1)
                                .alongWith(new Specimen(r)),
                        new FollowPath(r.getF(), FiveSpecOneSample.score2(), true, 1)
                                .alongWith(new Chamber(r)),
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