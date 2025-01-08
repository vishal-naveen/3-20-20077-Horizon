package indubitables.config.core;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import indubitables.config.subsystems.extend.Extend;
import indubitables.config.subsystems.intake.Intake;
import indubitables.config.subsystems.lift.Lift;
import indubitables.config.subsystems.outtake.Outtake;

public class Robot extends SubsystemBase {
    private HardwareMap h;
    private Telemetry t;
    private GamepadEx g1, g2;
    private Alliance a;
    private Start s;
    private Follower f;
    private Extend e;
    private Intake i;
    private Lift l;
    private Outtake o;


    public Robot(HardwareMap h, Telemetry t, GamepadEx g1, GamepadEx g2, Alliance a, Start s, Pose startPose) {
        this.h = h;
        this.t = t;
        this.g1 = g1;
        this.g2 = g2;
        this.a = a;
        this.s = s;

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        e = new Extend(this.h,this.t);
        l = new Lift(this.h,this.t);
        i = new Intake(this.h,this.t);
        o = new Outtake(this.h,this.t);
    }

    @Override
    public void periodic() {
        e.periodic();
        l.periodic();
        i.periodic();
        o.periodic();
    }

    public HardwareMap getH() {
        return h;
    }

    public void setH(HardwareMap h) {
        this.h = h;
    }

    public Telemetry getT() {
        return t;
    }

    public void setT(Telemetry t) {
        this.t = t;
    }

    public GamepadEx getG1() {
        return g1;
    }

    public void setG1(GamepadEx g1) {
        this.g1 = g1;
    }

    public GamepadEx getG2() {
        return g2;
    }

    public void setG2(GamepadEx g2) {
        this.g2 = g2;
    }

    public Alliance getA() {
        return a;
    }

    public void setA(Alliance a) {
        this.a = a;
    }

    public Start getS() {
        return s;
    }

    public void setS(Start s) {
        this.s = s;
    }

    public Follower getF() {
        return f;
    }

    public void setF(Follower f) {
        this.f = f;
    }

    public Extend getE() {
        return e;
    }

    public void setE(Extend e) {
        this.e = e;
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

    public void setL(Lift l) {
        this.l = l;
    }

    public Outtake getO() {
        return o;
    }

    public void setO(Outtake o) {
        this.o = o;
    }
}
