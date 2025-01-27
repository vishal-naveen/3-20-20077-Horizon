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

public class Light extends SubsystemBase {

    public enum HeadlightState{
        ON, OFF
    }
    private MultipleTelemetry telemetry;

    public Servo left, right;

    public HeadlightState headlight;

    public Light(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        left = hardwareMap.get(Servo.class, "hL");
        right = hardwareMap.get(Servo.class, "hR");

        register();
    }

    public void set(HeadlightState state) {
        if(state == HeadlightState.ON) {
            left.setPosition(1);
            right.setPosition(1);
            headlight = HeadlightState.ON;
        }

        if(state == HeadlightState.OFF) {
            left.setPosition(0);
            right.setPosition(0);
            headlight = HeadlightState.OFF;
        }
    }

    public void on() {
        set(HeadlightState.ON);
    }

    public void off() {
        set(HeadlightState.OFF);
    }


    public void telemetry() {
        telemetry.addData("Headlight State", headlight);
    }

    @Override
    public void periodic() {
        telemetry();
    }
}