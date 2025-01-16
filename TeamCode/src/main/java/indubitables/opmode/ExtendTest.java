package indubitables.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "TeleOp", name = "Extend Test")
@Config
public class ExtendTest extends OpMode {

    Servo eL, eR;

    public static double e;

    @Override
    public void init() {
        eL = hardwareMap.get(Servo.class,"eL");
        eR = hardwareMap.get(Servo.class, "eR");
    }

    @Override
    public void loop() {
        if(gamepad1.right_bumper) {
            eL.setPosition(0);
            eR.setPosition(0);
        }

        if(gamepad1.left_bumper) {
            eL.setPosition(1);
            eR.setPosition(1);
        }

        if(gamepad1.a) {
            eL.setPosition(e);
            eR.setPosition(e);
        }
    }
}
