package indubitables.config.subsystems;

import static indubitables.config.core.RobotConstants.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/** @author Baron Henderson
 * @version 2.0 | 1/4/25
 */

public class Extend extends SubsystemBase {
    private MultipleTelemetry telemetry;

    public Servo left, right;
    private double pos = 0;

    public Extend(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        left = hardwareMap.get(Servo.class, "eL");
        right = hardwareMap.get(Servo.class, "eR");

        register();
    }

    public void setTarget(double b) {
        left.setPosition(b);
        right.setPosition(b);
        pos = b;
    }

    public void toZero() {
        setTarget(extendZero);
    }

    public void toQuarter() {
        setTarget(extendFull/4);
    }

    public void toHalf() {
        setTarget(extendFull/2);
    }

    public void toFull() {
        setTarget(extendFull);
    }

    public double getPos() {
        pos = right.getPosition();
        return pos;
    }


    public void telemetry() {
        telemetry.addData("Extend Pos: ", getPos());
    }

    @Override
    public void periodic() {
        telemetry();
    }
}