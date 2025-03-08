package config.subsystems;

import static config.core.RobotConstants.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/** @author Baron Henderson
 * @version 2.0 | 1/4/25
 */

public class Extend {

    public enum ExtendState {
        ZERO, QUARTER, HALF, FULL, TRANSFER
    }

    private MultipleTelemetry telemetry;

    public Servo left, right;
    private ExtendState state = ExtendState.ZERO;
    private double pos = 0;

    public Extend(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        left = hardwareMap.get(Servo.class, "eL");
        right = hardwareMap.get(Servo.class, "eR");

    }

    public void setTarget(double b) {
        left.setPosition(b);
        right.setPosition(b);
        pos = b;
    }

    public void toZero() {
        setTarget(extendZero);
        state = ExtendState.ZERO;
    }

    public void toQuarter() {
        setTarget(extendFull/4);
        state = ExtendState.QUARTER;
    }

    public void toHalf() {
        setTarget(extendFull/2);
        state = ExtendState.HALF;
    }

    public void toFull() {
        setTarget(extendFull);
        state = ExtendState.FULL;
    }

    public void toTransfer() {
        setTarget(extendTransfer);
        state = ExtendState.TRANSFER;
    }

    public double getPos() {
        pos = right.getPosition();
        return pos;
    }

    public ExtendState getState() {
        return state;
    }


    public void telemetry() {
        telemetry.addData("Extend Pos: ", getPos());
    }

    public void periodic() {
        telemetry();
    }
}