package indubitables.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "TeleOp", name = "Outtake Test")
public class OuttakeTest extends OpMode {
    Servo oLP, oRP, oLR, oRR;

    @Override
    public void init() {
        oLP = hardwareMap.get(Servo.class, "oLP");
        oRP = hardwareMap.get(Servo.class, "oRP");
        oLR = hardwareMap.get(Servo.class, "oLR");
        oRR = hardwareMap.get(Servo.class, "oRR");

        oLR.setPosition(0.5);
        oRR.setPosition(0.5);
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            oLP.setPosition(0);
            oRP.setPosition(0);
        }

        if (gamepad1.y) {
            oLP.setPosition(1);
            oRP.setPosition(1);
        }
    }
}
