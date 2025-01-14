package indubitables.config.core;

import static indubitables.config.core.Opmode.*;
import static indubitables.config.core.gamepad.GamepadKeys.Button.*;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import indubitables.config.commands.Submersible;
import indubitables.config.commands.Transfer;
import indubitables.config.core.gamepad.ButtonReader;
import indubitables.config.core.gamepad.ExGamepad;
import indubitables.config.core.gamepad.GamepadKeys;
import indubitables.config.core.gamepad.TriggerReader;
import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;
import indubitables.config.subsystems.extend.Extend;
import indubitables.config.subsystems.intake.Intake;
import indubitables.config.subsystems.lift.Lift;
import indubitables.config.subsystems.outtake.Outtake;

public class Robot extends SubsystemBase {
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
    public static Pose autoEndPose;
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
    }

    public Robot(HardwareMap h, Telemetry t, Alliance a, Pose startPose) {
        this.op = AUTONOMOUS;
        this.h = h;
        this.t = t;
        this.a = a;

        CommandScheduler.getInstance().enable();

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        e = new Extend(this.h,this.t);
        l = new Lift(this.h,this.t);
        i = new Intake(this.h,this.t);
        o = new Outtake(this.h,this.t);

        register();
    }

    @Override
    public void periodic() {
        if(op == TELEOP)
            teleopControls();

        e.periodic();
        l.periodic();
        i.periodic();
        o.periodic();
        f.update();
        t.update();
    }

    public void start() {
        if(op == TELEOP) {
            e.setLimitToSample();
            o.start();
            f.startTeleopDrive();
        }
    }

    public void stop() {
        CommandScheduler.getInstance().cancelAll();
        autoEndPose = f.getPose();
    }

    public void teleopControls() {
        normalDrive();

        g1.getGamepadButton(LEFT_BUMPER)
                .whileHeld(this::fastDrive);

        g1.getGamepadButton(RIGHT_BUMPER)
                .whileHeld(this::slowDrive);

        //lift.manual(gamepad2.right_trigger - gamepad2.left_trigger);

        g1.getGamepadButton(LEFT_BUMPER)
                .whileHeld(this::fastDrive);

        g1.getGamepadButton(RIGHT_BUMPER)
                .whileHeld(this::slowDrive);

        g1.getGamepadButton(X)
                .whenPressed(this::flip);

        g1.getGamepadButton(B)
                .whenPressed(this::unflip);

        if(g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
            e.toFull();

        if(g1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
            e.toZero();

        g2.getGamepadButton(A).whenPressed(o::switchGrabState);

        g2.getGamepadButton(Y).whenPressed(() -> {
            e.setLimitToSample();
            o.transfer();
            i.hover();
        });

        g2.getGamepadButton(X).whenPressed(() -> {
            e.setLimitToSample();
            o.score();
            i.hover();
        });

        g2.getGamepadButton(DPAD_LEFT).whenPressed(this::specimenGrabPos);

        g2.getGamepadButton(DPAD_RIGHT).whenPressed(this::specimenScorePos);

        g2.getGamepadButton(B).whenPressed(new Transfer(this));

        g2.getGamepadButton(DPAD_UP).whenPressed(i::switchGrabState);

        g2.getGamepadButton(DPAD_DOWN).whenPressed(new Submersible(this));

        g2.getGamepadButton(LEFT_BUMPER).whenPressed(i::rotateCycleLeft);

        g2.getGamepadButton(LEFT_BUMPER).whenPressed(i::rotateCycleRight);

        g2.getGamepadButton(LEFT_STICK_BUTTON).whenPressed(() -> {
            o.hang();
            i.transfer();
            e.toZero();
        });

        g2.getGamepadButton(RIGHT_STICK_BUTTON).whenPressed(i::transfer);

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
        e.setLimitToSpecimen();
        o.startSpecGrab();
        i.specimen();
    }

    private void specimenScorePos() {
        e.setLimitToSpecimen();
        o.specimenScore();
        i.specimen();
    }
}
