package indubitables.opmode.tests;

import static com.pedropathing.follower.FollowerConstants.leftFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.leftFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorDirection;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorName;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorDirection;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorName;
import static indubitables.config.core.RobotConstants.*;

import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.Arrays;
import java.util.List;

import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;

@TeleOp(group = "TeleOp", name = "Intake Test")
public class IntakeTest extends OpMode {
    Servo iP, iLR, iRR, iG;

    private DcMotorEx leftFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightFront;
    private DcMotorEx rightRear;
    private List<DcMotorEx> motors;

    @Override
    public void init() {

        Constants.setConstants(FConstants.class, LConstants.class);
        iP = hardwareMap.get(Servo.class, "iP");
        iLR = hardwareMap.get(Servo.class, "iLR");
        iRR = hardwareMap.get(Servo.class, "iRR");
   //      iG = hardwareMap.get(Servo.class, "iG");

        iLR.setPosition(0.5);
        iRR.setPosition(0.5);

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
    }

    @Override
    public void loop() {
      /*  if (gamepad1.dpad_down)
            oG.setPosition(intakeGrabClose);

        if (gamepad1.dpad_up)
            oG.setPosition(intakeGrabOpen);
*/
        if (gamepad1.y) {
            iP.setPosition(intakePivotSpecimen);
        }

        if (gamepad1.x) {
            iP.setPosition(intakePivotGround);
        }

/*
        if(gamepad1.a) {
            iLR.setPosition(intakeRotateSpecimenGrab);
            iRR.setPosition(intakeRotateSpecimenGrab);
        }

        if(gamepad1.b) {
            iLR.setPosition(intakeRotateLeftSpecimenScore);
            iRR.setPosition(intakeRotateRightSpecimenScore);
        }
        /*

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
        rightRear.setPower(rightRearPower);*/
    }
}
