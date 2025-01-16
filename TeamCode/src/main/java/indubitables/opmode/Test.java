package indubitables.opmode;

import static indubitables.opmode.ExtendTest.e;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Test extends OpMode {
    Servo eL, eR, oLP, oRP, hang;
    DcMotor rightL = null;
    DcMotor leftL = null;

    @Override
    public void init() {
        oLP = hardwareMap.get(Servo.class, "oLP");
        oRP = hardwareMap.get(Servo.class, "oRP");
        rightL = hardwareMap.get(DcMotor.class, "rightLift");
        leftL = hardwareMap.get(DcMotor.class, "leftLift");
        hang = hardwareMap.get(Servo.class, "hang");

        rightL.setDirection(DcMotorSimple.Direction.REVERSE);
        leftL.setDirection(DcMotorSimple.Direction.FORWARD);
        rightL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        eL = hardwareMap.get(Servo.class,"eL");
        eR = hardwareMap.get(Servo.class, "eR");

        telemetry.addData("Init Complete", true);
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > .1) {
            eL.setPosition(0);
            eR.setPosition(0);
        }

        if(gamepad1.left_trigger > 0.1) {
            eL.setPosition(1);
            eR.setPosition(1);
        }

        if(gamepad1.dpad_left) {
            eL.setPosition(e);
            eR.setPosition(e);
        }

        if (gamepad2.x) {
            oLP.setPosition(0);
            oRP.setPosition(0);
        }

        if (gamepad2.y) {
            oLP.setPosition(1);
            oRP.setPosition(1);
        }

        if (gamepad2.dpad_up) {
            hang.setPosition(0.25);
        } else if (gamepad2.dpad_down) {
            hang.setPosition(0.875);
        }

        leftL.setPower(gamepad2.right_trigger-gamepad2.left_trigger);
        rightL.setPower(gamepad2.right_trigger-gamepad2.left_trigger);

        telemetry.addData("Hang Position: ", hang.getPosition());
        telemetry.addData("Right Lift Position: ", rightL.getCurrentPosition());
        telemetry.update();
    }
}
