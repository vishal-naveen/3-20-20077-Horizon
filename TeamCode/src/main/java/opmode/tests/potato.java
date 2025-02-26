package opmode.tests;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import static config.core.Opmode.TELEOP;

import java.util.List;

import config.core.Alliance;
import config.core.Opmode;
import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.subsystems.Extend;
import config.subsystems.Intake;
import config.subsystems.Lift;
import config.subsystems.Outtake;

@TeleOp
public class potato extends OpMode {

    private Gamepad g1a, g2a, g1, g2, p1, p2;
    private Alliance a;
    private Follower f;
    private Extend e;
    private Intake i;
    private Lift l;
    private Outtake o;
    private Opmode op = TELEOP;
    public static Pose autoEndPose = new Pose(0,0,0);
    public double speed = 0.75;
    public Timer tTimer, sTimer, spec0Timer, spec180Timer, c0Timer, c180Timer;
    public int flip = 1, tState = -1, sState = -1, spec0State = -1, spec180State = -1, c0State = -1, c180State = -1;
    
    @Override
    public void init() {
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        f.setStartingPose(new Pose());

        e = new Extend(hardwareMap,telemetry);
        l = new Lift(hardwareMap,telemetry);
        i = new Intake(hardwareMap,telemetry);
        o = new Outtake(hardwareMap,telemetry);

        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.p1 = new Gamepad();
        this.p2 = new Gamepad();

        tTimer = new Timer();
        sTimer = new Timer();
        spec0Timer = new Timer();
        spec180Timer = new Timer();
        c0Timer = new Timer();
        c180Timer = new Timer();

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    @Override
    public void loop() {
        p1.copy(g1);
        p2.copy(g2);
        g1.copy(g1a);
        g2.copy(g2a);

        if (g1.right_bumper)
            speed = 1;
        else if (g1.left_bumper)
            speed = 0.25;
        else
            speed = 0.75;

        l.manual(g2.left_trigger, g2.right_trigger);

        if (g1.x) {
            flip = -1;
        }

        if (g1.b) {
            flip = 1;
        }

        if (g1.right_trigger > 0.1)
            e.toFull();

        if (g1.left_trigger > 0.1)
            e.toZero();

        if (g2.a && !p2.a)
            o.switchGrabState();

        if (g2.y && !p2.y) {
            o.transfer();
            i.hover();
        }

        if (g2.x && !p2.x) {
            o.score();
            i.hover();
        }

        if (g2.dpad_left && !p2.dpad_left) {
            o.specimenGrab180();
            i.specimen();
        }

        if (g2.dpad_right && !p2.dpad_right) {
            o.specimenScore180();
            i.specimen();
        }

        if (g2.b && !p2.b)
            startTransfer();

        if (g2.dpad_up && !p2.dpad_up)
            i.switchGrabState();

        if (!g2.dpad_down && p2.dpad_down)
            startSubmersible();


        if (g2.dpad_down && !p2.dpad_down) {
            i.cloud();
            i.open();
            o.transfer();
        }

        if (g2.left_bumper && !p2.left_bumper)
            i.rotateCycleLeft();

        if (g2.right_bumper && !p2.right_bumper)
            i.rotateCycleRight();

        if (g2.left_stick_button) {
            o.hang();
            i.transfer();
            e.toZero();
        }

        if (g2.right_stick_button)
            i.transfer();

        f.setTeleOpMovementVectors(flip * -g1.left_stick_y * speed, flip * -g1.left_stick_x * speed, -g1.right_stick_x * speed * 0.5, true);

        submersible();
        transfer();

        e.periodic();
        l.periodic();
        i.periodic();
        o.periodic();
        f.update();
        telemetry.update();
    }

    private void transfer() {
        telemetry.addData("Transfer State", tState);

        switch (tState) {
            case 1:
                i.close();
                //     transferSampleDetected = (intake.getColor() == IntakeColor.BLUE || intake.getColor() == IntakeColor.RED || intake.getColor() == IntakeColor.YELLOW);
                o.transfer();
                i.transfer();
                setTransferState(2);
                break;
            case 2:
                if (tTimer.getElapsedTimeSeconds() > 0.1) {
                    o.setRotateState(Outtake.RotateState.TRANSFER);
                    e.toZero();
                    setTransferState(3);
                }
                break;
            case 3:
                if (tTimer.getElapsedTimeSeconds() > 0.15) {
                    o.transfer();
                    setTransferState(4);
                }
                break;
            case 4:
                if (tTimer.getElapsedTimeSeconds() > 0) {
                    o.close();
                    setTransferState(5);
                }
                break;
            case 5:
                if (tTimer.getElapsedTimeSeconds() > 0.5) {
                    o.score();
                    setTransferState(6);
                }
                break;
            case 6:
                if (tTimer.getElapsedTimeSeconds() > 0) {
                    i.open();
                    setTransferState(7);
                }
                break;
            case 7:
                if (tTimer.getElapsedTimeSeconds() > 0.25) {
                    i.hover();
                    setTransferState(-1);
                }
                break;
        }
    }

    public void setTransferState(int x) {
        tState = x;
        tTimer.resetTimer();
    }

    public void startTransfer() {
        setTransferState(1);
    }

    private void submersible() {
        telemetry.addData("Submersible State", sState);

        switch (sState) {
            case 0:
                i.ground();
                i.open();
                o.transfer();
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
