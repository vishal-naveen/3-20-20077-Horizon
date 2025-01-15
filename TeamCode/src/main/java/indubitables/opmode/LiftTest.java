package indubitables.opmode;

import static indubitables.config.core.RobotConstants.extendFullSample;
import static indubitables.config.core.RobotConstants.outtakePivotSpecimenScore;
import static indubitables.config.core.RobotConstants.outtakePivotTransfer;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import indubitables.config.core.RobotConstants;
import indubitables.config.core.hardware.CachedMotor;
@TeleOp(group = "TeleOp", name = "Hang Test")
public class LiftTest extends OpMode {

    DcMotor rightL = null;
    DcMotor leftL = null;
    Servo hang, oLP, oRP, eL, eR;
    @Override
    public void init() {
        rightL = hardwareMap.get(DcMotor.class, "rightLift");
        leftL = hardwareMap.get(DcMotor.class, "leftLift");
        hang = hardwareMap.get(Servo.class, "hang");
        oLP = hardwareMap.get(Servo.class, "oLP");
        oRP = hardwareMap.get(Servo.class, "oRP");
        eL = hardwareMap.get(Servo.class,"eL");
        eR = hardwareMap.get(Servo.class, "eR");

        rightL.setDirection(DcMotorSimple.Direction.REVERSE);
        leftL.setDirection(DcMotorSimple.Direction.FORWARD);
        rightL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Init Complete", true);
        telemetry.update();
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            hang.setPosition(0.125);
        } else if (gamepad1.b) {
            hang.setPosition(0.875);
        }

        if (gamepad1.x) {
            oLP.setPosition(0);
            oRP.setPosition(0);
        }

        if (gamepad1.y) {
            oLP.setPosition(1);
            oRP.setPosition(1);
        }

        if(gamepad1.right_bumper) {
            eL.setPosition(0);
            eR.setPosition(0);
        }

        if(gamepad1.left_bumper) {
            eL.setPosition(1);
            eR.setPosition(1);
        }

        leftL.setPower(gamepad2.left_stick_y);
        rightL.setPower(gamepad2.left_stick_y);
        leftL.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        rightL.setPower(gamepad1.right_trigger-gamepad1.left_trigger);

        telemetry.addData("Hang Position: ", hang.getPosition());
        telemetry.addData("Right Lift Position: ", rightL.getCurrentPosition());
        telemetry.update();
    }
}
