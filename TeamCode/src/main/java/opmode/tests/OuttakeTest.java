package opmode.tests;

import static com.pedropathing.follower.FollowerConstants.leftFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.leftFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorDirection;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorName;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorDirection;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorName;

import static config.core.RobotConstants.*;
import static config.core.RobotConstants.outtakeRotateSpecimenGrab180;

import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.Arrays;
import java.util.List;

import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;

@TeleOp(group = "TeleOp", name = "Outtake Test")
public class OuttakeTest extends OpMode {
    Servo oLP, oRP, oLR, oRR, oG;

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
        oLR = hardwareMap.get(Servo.class, "oLR");
        oRR = hardwareMap.get(Servo.class, "oRR");
        oG = hardwareMap.get(Servo.class, "oG");

        oLR.setPosition(0.5);
        oRR.setPosition(0.5);

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
        if (gamepad1.dpad_down)
            oG.setPosition(outtakeGrabClose);

        if (gamepad1.dpad_up)
            oG.setPosition(outtakeGrabOpen);

        if (gamepad1.y) {
            oLP.setPosition(outtakePivotSpecimenScore180);
            oRP.setPosition(outtakePivotSpecimenScore180);
        }

        if (gamepad1.x) {
            oLP.setPosition(outtakePivotSpecimenGrab180);
            oRP.setPosition(outtakePivotSpecimenGrab180);
        }

        if(gamepad1.a) {
            oLR.setPosition(outtakeRotateSpecimenGrab180+0.045);
            oRR.setPosition(outtakeRotateSpecimenGrab180);
        }

        if(gamepad1.b) {
            oLR.setPosition(outtakeRotateLeftSpecimenScore180);
            oRR.setPosition(outtakeRotateRightSpecimenScore180);
        }

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
    }
}
