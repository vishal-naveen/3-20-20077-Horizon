package indubitables.config.subsystems.extend;

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
    public double extendLimit = extendFullSample;

    public Extend(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        left = hardwareMap.get(Servo.class, "leftExtend");
        right = hardwareMap.get(Servo.class, "rightExtend");
    }

    public void manual(int direction) {
        double rightPos = right.getPosition();

        if (rightPos <= extendLimit || direction < 0) {
            rightPos += (extendManualIncrements * direction);
        } else {
            rightPos = right.getPosition();
        }

        left.setPosition(rightPos);
        right.setPosition(rightPos);
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
        setTarget(extendLimit/4);
    }

    public void toHalf() {
        setTarget(extendLimit/2);
    }

    public void toFull() {
        setTarget(extendLimit);
    }

    public double getPos() {
        pos = right.getPosition();
        return pos;
    }

    public void setLimitToSpecimen() {
        extendLimit = extendFullSpecimen;
    }

    public void setLimitToSample() {
        extendLimit = extendFullSample;
    }

    public void telemetry() {
        telemetry.addData("Extend Pos: ", getPos());
    }

    @Override
    public void periodic() {
        telemetry();
    }
}