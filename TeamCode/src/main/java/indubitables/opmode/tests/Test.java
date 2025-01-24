package indubitables.opmode.tests;

import static com.pedropathing.follower.FollowerConstants.leftFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.*;
import static indubitables.config.core.RobotConstants.extendFull;
import static indubitables.config.core.RobotConstants.extendZero;
import static indubitables.config.core.RobotConstants.outtakePivotSpecimenGrab;
import static indubitables.config.core.RobotConstants.outtakePivotSpecimenScore;
import static indubitables.config.core.RobotConstants.outtakeRotateLeftSpecimenScore;
import static indubitables.config.core.RobotConstants.outtakeRotateRightSpecimenScore;
import static indubitables.config.core.RobotConstants.outtakeRotateSpecimenGrab;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.Arrays;
import java.util.List;

import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;

@TeleOp(name = "overall test")
public class Test extends OpMode {
    Servo eL, eR, oLP, oRP, oLR, oRR;
    DcMotor rightL = null;
    DcMotor leftL = null;
    Follower f;

    private DcMotorEx leftFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightFront;
    private DcMotorEx rightRear;
    private List<DcMotorEx> motors;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        oLP = hardwareMap.get(Servo.class, "oLP");
        oRP = hardwareMap.get(Servo.class, "oRP");
        rightL = hardwareMap.get(DcMotor.class, "rightLift");
        leftL = hardwareMap.get(DcMotor.class, "leftLift");
        leftFront = hardwareMap.get(DcMotorEx.class, leftFrontMotorName);
        leftRear = hardwareMap.get(DcMotorEx.class, leftRearMotorName);
        rightRear = hardwareMap.get(DcMotorEx.class, rightRearMotorName);
        rightFront = hardwareMap.get(DcMotorEx.class, rightFrontMotorName);
        leftFront.setDirection(leftFrontMotorDirection);
        leftRear.setDirection(leftRearMotorDirection);
        rightFront.setDirection(rightFrontMotorDirection);
        rightRear.setDirection(rightRearMotorDirection);

        motors = Arrays.asList(leftFront, leftRear, rightFront, rightRear);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rightL.setDirection(DcMotorSimple.Direction.FORWARD);
        leftL.setDirection(DcMotorSimple.Direction.REVERSE);
        rightL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        eL = hardwareMap.get(Servo.class,"eL");
        eR = hardwareMap.get(Servo.class, "eR");

        oLR = hardwareMap.get(Servo.class, "oLR");
        oRR = hardwareMap.get(Servo.class, "oRR");

        telemetry.addData("Init Complete", true);
        telemetry.update();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x; // this is strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = (y + x + rx) / denominator;
        double leftRearPower = (y - x + rx) / denominator;
        double rightFrontPower = (y - x - rx) / denominator;
        double rightRearPower = (y + x - rx) / denominator;

        leftFront.setPower(leftFrontPower);
        leftRear.setPower(leftRearPower);
        rightFront.setPower(rightFrontPower);
        rightRear.setPower(rightRearPower);

        if(gamepad1.left_trigger > .1) {
            eL.setPosition(extendZero);
            eR.setPosition(extendZero);
        }

        if(gamepad1.right_trigger > 0.1) {
            eL.setPosition(extendFull);
            eR.setPosition(extendFull);
        }

        if (gamepad2.y) {
            oLP.setPosition(outtakePivotSpecimenScore);
            oRP.setPosition(outtakePivotSpecimenScore);
        }

        if (gamepad2.x) {
            oLP.setPosition(outtakePivotSpecimenGrab);
            oRP.setPosition(outtakePivotSpecimenGrab);
        }

        if(gamepad2.a) {
            oLR.setPosition(outtakeRotateSpecimenGrab);
            oRR.setPosition(outtakeRotateSpecimenGrab);
        }

        if(gamepad2.b) {
            oLR.setPosition(outtakeRotateLeftSpecimenScore);
            oRR.setPosition(outtakeRotateRightSpecimenScore);
        }

        leftL.setPower(gamepad2.right_trigger-gamepad2.left_trigger);
        rightL.setPower(gamepad2.right_trigger-gamepad2.left_trigger);

        telemetry.addData("Right Lift Position: ", rightL.getCurrentPosition());
        telemetry.update();
    }
}
