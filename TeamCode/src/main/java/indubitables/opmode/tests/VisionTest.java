package indubitables.opmode.tests;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import indubitables.config.vision.limelight.Vision;

@TeleOp(group = "TeleOp", name = "Vision Test")
public class VisionTest extends OpMode {
    Vision v;
    Follower f;
    Servo eL, eR;
    final double ipt = 0.001;

    @Override
    public void init() {
        v = new Vision(hardwareMap, telemetry, new int[]{1});
        f = new Follower(hardwareMap);
        eL = hardwareMap.get(Servo.class,"eL");
        eR = hardwareMap.get(Servo.class, "eR");

        CommandScheduler.getInstance().registerSubsystem(v);
    }

    @Override
    public void loop() {
        v.periodic();
        telemetry.update();

        if(gamepad1.right_trigger > .1) {
            Pose c = f.getPose();
            f.holdPoint(v.getAlignedPose(c));
        }

        if(gamepad1.left_trigger > .1) {
            setInches(v.extendDistance());
        }

    }

    public void setInches(double in) {
        eL.setPosition(in * ipt);
        eR.setPosition(in * ipt);
    }
}
