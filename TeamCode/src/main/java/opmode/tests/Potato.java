package opmode.tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.vision.limelight.Vision;

@TeleOp(group = "TeleOp", name = "Potato")
public class Potato extends OpMode {
    Vision v;
    Follower f;
    Gamepad g1 = new Gamepad(), p1 = new Gamepad();

    @Override
    public void init() {
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        v = new Vision(hardwareMap, telemetry, new int[]{1, 2}, f);
    }

    @Override
    public void loop() {
        p1.copy(g1);
        g1.copy(gamepad1);

        if(g1.a && !p1.a) {
            f.followPath(v.toTarget());
            telemetry.addData("Target Path", "Following");
        } else {
            telemetry.addData("Target Path", "Waiting");
        }

        f.update();
        v.find();
        telemetry.update();
    }
}
