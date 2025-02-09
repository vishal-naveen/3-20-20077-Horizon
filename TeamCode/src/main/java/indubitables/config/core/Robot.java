package indubitables.config.core;

import static indubitables.config.core.Opmode.*;
import static indubitables.config.core.gamepad.GamepadKeys.Button.*;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.ServoHubConfiguration;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import indubitables.config.commands.Submersible;
import indubitables.config.commands.Transfer;
import indubitables.config.core.gamepad.ExGamepad;
import indubitables.config.core.gamepad.GamepadKeys;
import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;
import indubitables.config.subsystems.Extend;
import indubitables.config.subsystems.Intake;
import indubitables.config.subsystems.Lift;
import indubitables.config.subsystems.Outtake;

public class Robot {
    private HardwareMap h;
    private Telemetry t;
    private Gamepad g1, g2, p1, p2;
    private Alliance a;
    private Follower f;
    private Extend e;
    private Intake i;
    private Lift l;
    private Outtake o;
    private Opmode op = TELEOP;
    public static Pose autoEndPose = new Pose(0,0,0);
    public double speed = 0.75;
    public int flip = 1;

    public Robot(HardwareMap h, Telemetry t, Gamepad g1, Gamepad g2, Alliance a, Pose startPose) {
        this.op = TELEOP;
        this.h = h;
        this.t = t;
        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.p1 = new Gamepad();
        this.p2 = new Gamepad();
        this.a = a;

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        e = new Extend(this.h,this.t);
        l = new Lift(this.h,this.t);
        i = new Intake(this.h,this.t);
        o = new Outtake(this.h,this.t);
    }

    public Robot(HardwareMap h, Telemetry t, Alliance a, Pose startPose) {
        this.op = AUTONOMOUS;
        this.h = h;
        this.t = t;
        this.a = a;

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        e = new Extend(this.h,this.t);
        l = new Lift(this.h,this.t);
        i = new Intake(this.h,this.t);
        o = new Outtake(this.h,this.t);
    }

    public void periodic() {
        e.periodic();
        l.periodic();
        i.periodic();
        o.periodic();
        f.update();
        t.update();
    }

    public void teleopStart() {
        o.start();
        f.startTeleopDrive();
    }

    public void stop() {
        autoEndPose = f.getPose();
    }

    public void updateControls(Gamepad gamepad1, Gamepad gamepad2) {
        p1.copy(g1);
        p2.copy(g2);
        g1.copy(gamepad1);
        g2.copy(gamepad2);

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

        if (g2.dpad_left && !p2.dpad_left)
            specimenGrabPos();

        if (g2.dpad_right && !p2.dpad_right)
            specimenScorePos();

//        if (g2.b && !p2.b)
//           CommandScheduler.getInstance().schedule(new Transfer(this));

        if (g2.dpad_up && !p2.dpad_up) {
            i.switchGrabState();
        }

//        if (g2.dpad_down && !p2.dpad_down) {
//            CommandScheduler.getInstance().schedule(new Submersible(this));
//            o.score();
//        }

        if (g2.left_bumper && !p2.left_bumper) {
            i.rotateCycleLeft();
        }

        if (g2.right_bumper && !p2.right_bumper) {
            i.rotateCycleRight();
        }

        if (g2.left_stick_button) {
            o.hang();
            i.transfer();
            e.toZero();
        }

        if (g2.right_stick_button) {
            i.transfer();
        }
        
        f.setTeleOpMovementVectors(flip * -g1.left_stick_y * speed, flip * -g1.left_stick_x * speed, -g1.right_stick_x * speed * 0.5, true);
    }

    public HardwareMap getH() {
        return h;
    }

    public Telemetry getT() {
        return t;
    }


    public Gamepad getG1() {
        return g1;
    }

    public Gamepad getG2() {
        return g2;
    }

    public Gamepad getP1() {
        return p1;
    }

    public Gamepad getP2() {
        return p2;
    }

    public Alliance getA() {
        return a;
    }

    public void setA(Alliance a) {
        this.a = a;
    }

    public Follower getF() {
        return f;
    }

    public Extend getE() {
        return e;
    }

    public Intake getI() {
        return i;
    }

    public void setI(Intake i) {
        this.i = i;
    }

    public Lift getL() {
        return l;
    }

    public Outtake getO() {
        return o;
    }

    public void slowDrive() {
        speed = 0.25;
    }

    public void normalDrive() {
        speed = 0.75;
    }

    public void fastDrive() {
        speed = 0.25;
    }

    public void flip() {
        flip = -1;
    }

    public void unflip() {
        flip = 1;
    }

    private void specimenGrabPos() {
        o.startSpecGrab();
        i.specimen();
    }

    private void specimenScorePos() {
        o.specimenScore();
        i.specimen();
    }
}
