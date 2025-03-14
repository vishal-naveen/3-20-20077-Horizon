package opmode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import config.core.paths.FourSamp;
import config.core.paths.SevenSpec;
import config.core.paths.SixSpecPush;
import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.subsystems.Extend;
import config.subsystems.Intake;
import config.subsystems.Outtake;

@Config
@TeleOp(name = "IAmGoingInsaneTest", group = "Tests")
public class IAmGoingInsaneTest extends OpMode {
    Follower f;
    public static double x = 41.75, y = 66;
    @Override
    public void init() {
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        f.setStartingPose(SevenSpec.start);
    }

    @Override
    public void loop()
    {
        f.holdPoint(new Pose(x, y, 0));
        f.update();
        telemetry.addData("Pose", f.getPose());
        telemetry.update();
    }
}
