package opmode.tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.vision.limelight.Vision;

@TeleOp(group = "TeleOp", name = "Potato")
public class Potato extends OpMode {
    Vision v;
    Follower f;
    @Override
    public void init() {
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        v = new Vision(hardwareMap, telemetry, new int[]{1, 2}, f);
    }

    @Override
    public void loop() {
        f.update();
        v.periodic();
        telemetry.update();
    }
}
