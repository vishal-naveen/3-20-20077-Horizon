package opmode.tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import config.core.paths.FourSamp;
import config.core.paths.SixSpecPush;
import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.subsystems.Extend;
import config.subsystems.Intake;
import config.subsystems.Outtake;

@TeleOp(name = "Drive Test", group = "Tests")
public class DriveTest extends OpMode {
    Follower f;
    Intake i;
    Extend e;
    Outtake o;

    @Override
    public void init() {
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        f.setStartingPose(FourSamp.start);

        i = new Intake(hardwareMap, telemetry);
        e = new Extend(hardwareMap, telemetry);
        o = new Outtake(hardwareMap, telemetry);

        e.toFull();
        o.score();
        i.cloud();
    }

    @Override
    public void loop()
    {
        f.update();
        i.periodic();
        telemetry.addData("Pose", f.getPose());
        telemetry.update();
    }
}
