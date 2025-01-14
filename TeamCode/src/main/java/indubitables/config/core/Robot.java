package indubitables.config.core;

import static indubitables.config.core.Opmode.*;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import indubitables.config.core.gamepad.ExGamepad;
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
}
