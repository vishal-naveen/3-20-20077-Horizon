package indubitables.opmode;

import static indubitables.config.core.RobotConstants.extendFull;
import static indubitables.config.core.RobotConstants.extendZero;

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
        if(gamepad1.left_trigger > 0.1) {
            eL.setPosition(extendZero);
            eR.setPosition(extendZero);
        }

        if(gamepad1.right_trigger > 0.1) {
            eL.setPosition(extendFull);
            eR.setPosition(extendFull);
        }

    }
}
