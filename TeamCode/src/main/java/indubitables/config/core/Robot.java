package indubitables.config.core;

import static indubitables.config.core.Opmode.*;
import static indubitables.config.core.gamepad.GamepadKeys.Button.*;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    private ExGamepad g1, g2;
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

    public Robot(HardwareMap h, Telemetry t, ExGamepad g1, ExGamepad g2, Alliance a, Pose startPose) {
        this.op = TELEOP;
        this.h = h;
        this.t = t;
        this.g1 = g1;
        this.g2 = g2;
        this.a = a;

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        e = new Extend(this.h,this.t);
        l = new Lift(this.h,this.t);
        i = new Intake(this.h,this.t);
        o = new Outtake(this.h,this.t);

        CommandScheduler.getInstance().registerSubsystem(e, l, i, o);
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

        CommandScheduler.getInstance().registerSubsystem(e, l, i, o);
    }

    public void periodic() {
        if (op == TELEOP)
            updateControls();
       
        e.periodic();
        l.periodic();
        i.periodic();
        o.periodic();
        f.update();
        t.update();
        l.periodic();
    }

    public void teleopStart() {
        o.start();
        f.startTeleopDrive();
    }

    public void stop() {
        CommandScheduler.getInstance().cancelAll();
        autoEndPose = f.getPose();
    }

    public void updateControls() {
        if(g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
            e.toFull();

        if(g1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
            e.toZero();

        getL().manual(getG2().getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), getG2().getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        if(g1.getButton(LEFT_BUMPER))
            speed = 0.25;
        else if(g1.getButton(RIGHT_BUMPER))
            speed = 1;
        else
            speed = 0.75;

        if(g1.getButton(X))
            flip = -1;
        else if(g1.getButton(B))
            flip = 1;

        if (g2.wasJustPressed(A))
            o.switchGrabState();

        if (g2.wasJustPressed(Y)) {
            o.transfer();
            i.hover();
        }

        if (g2.wasJustPressed(X)) {
            o.score();
            i.hover();
        }

        if (g2.wasJustPressed(DPAD_LEFT))
            specimenGrabPos();

        if (g2.wasJustPressed(DPAD_RIGHT))
            specimenScorePos();

        if (g2.wasJustPressed(B))
            CommandScheduler.getInstance().schedule(new Transfer(this));

        if (g2.wasJustPressed(DPAD_UP))
            i.switchGrabState();

        if (g2.wasJustPressed(DPAD_DOWN))
            CommandScheduler.getInstance().schedule(new Submersible(this));

        if (g2.wasJustPressed(LEFT_BUMPER))
            i.rotateCycleLeft();

        if (g2.wasJustPressed(RIGHT_BUMPER))
            i.rotateCycleRight();

        if (g2.wasJustPressed(LEFT_STICK_BUTTON)) {
            o.hang();
            i.transfer();
            e.toZero();
        }

        if (g2.wasJustPressed(RIGHT_STICK_BUTTON))
            i.transfer();

        f.setTeleOpMovementVectors(flip * -g1.getLeftY() * speed, flip * -g1.getLeftX() * speed, -g1.getRightX() * speed * 0.5);
    }

    public HardwareMap getH() {
        return h;
    }

    public Telemetry getT() {
        return t;
    }


    public ExGamepad getG1() {
        return g1;
    }

    public ExGamepad getG2() {
        return g2;
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
