package opmode.tests;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import config.core.paths.SixSpec;
import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.subsystems.Extend;
import config.subsystems.Intake;
import config.subsystems.Light;
import config.subsystems.Outtake;
import config.vision.limelight.Vision;

@TeleOp(group = "TeleOp", name = "Vision Test")
public class VisionTest extends OpMode {
    Vision v;
    Follower f;
    Extend e;
    Intake i;
    Light j;
    Outtake o;
    Gamepad g1, p1;
    
    public int sState;
    public Timer sTimer;

    @Override
    public void init() {
        v = new Vision(hardwareMap, telemetry, new int[]{1, 2});
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        e = new Extend(hardwareMap, telemetry);
        i = new Intake(hardwareMap, telemetry);
        j = new Light(hardwareMap, telemetry);
        o = new Outtake(hardwareMap, telemetry);

        f.setStartingPose(SixSpec.score1);

        g1 = new Gamepad();
        p1 = new Gamepad();

        sTimer = new Timer();
        
        o.score();

        j.max();
    }

    @Override
    public void loop() {
        p1.copy(g1);
        g1.copy(gamepad1);
        v.periodic();
        f.update();
        telemetry.update();

        if (g1.y && !p1.y) {
            i.rotateDegrees(v.getBestDetectionAngle());
            e.toFull();
            Pose c = f.getPose();
            f.holdPoint(v.getPose(c));
        }

        if (g1.dpad_up && !p1.dpad_up)
            j.switchI();

        if (g1.a && !p1.a)
            i.rotateDegrees(v.getBestDetectionAngle());

        if(g1.right_bumper && !p1.right_bumper) {
            Pose c = f.getPose();
            f.holdPoint(v.getPose(c));
        }

        if(g1.left_bumper && !p1.left_bumper) {
            e.toFull();
        }

        if (!g1.dpad_down && p1.dpad_down)
            startSubmersible();

        if (g1.dpad_down && !p1.dpad_down) {
            i.cloud();
            i.open();
        }

        submersible();



    }

    private void submersible() {
        telemetry.addData("Submersible State", sState);

        switch (sState) {
            case 0:
                i.ground();
                i.open();
                setSubmersibleState(1);
                break;
            case 1:
                if(sTimer.getElapsedTimeSeconds() > 0.1) {
                    i.close();
                    setSubmersibleState(2);
                }
                break;
            case 2:
                if (sTimer.getElapsedTimeSeconds() > 0.15) {
                    i.hover();
                    setSubmersibleState(-1);
                }
                break;
        }
    }

    public void setSubmersibleState(int x) {
        sState = x;
        sTimer.resetTimer();
    }

    public void startSubmersible() {
        setSubmersibleState(0);
    }

}
