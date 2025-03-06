package config.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import config.core.hardware.Headlight;

public class Light {

    public enum HeadlightState {
        OFF, HALF, MAX
    }
    private Headlight hL, hR;
    private HeadlightState hState;

    public Light(HardwareMap h, Telemetry t) {
        hL = new Headlight(h.get(Servo.class, "hL"));
        hR = new Headlight(h.get(Servo.class, "hR"));
        hState = HeadlightState.OFF;
        off();
    }

    public void max() {
        hL.max();
        hR.max();
        hState = HeadlightState.MAX;
    }

    public void off() {
        hL.off();
        hR.off();
        hState = HeadlightState.OFF;
    }

    public void half() {
        hL.half();
        hR.half();
        hState = HeadlightState.HALF;
    }

    public void switchI() {
        if(hState == HeadlightState.OFF) {
            max();
        } else {
            off();
        }
    }


}
