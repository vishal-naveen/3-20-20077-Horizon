package indubitables.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "TeleOp", name = "Outtake Test")
public class OuttakeTest extends OpMode {
    Servo oLP, oRP;

    @Override
    public void init() {
        oLP = hardwareMap.get(Servo.class, "oLP");
        oRP = hardwareMap.get(Servo.class, "oRP");
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
