package indubitables.opmode.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;
import indubitables.config.subsystems.intake.Intake;
import com.pedropathing.follower.Follower;

@Config
@TeleOp(name="intakeTest", group="b")
public class intakeTest extends OpMode {

    private Intake intake;
    private Intake.GrabState intakeGrabState;
    private Intake.RotateState intakeRotateState;
    private Intake.PivotState intakePivotState;

    private Follower follower;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        intake = new Intake(hardwareMap, telemetry, intakeGrabState, intakeRotateState, intakePivotState);
        follower = new Follower(hardwareMap);
        intake.init();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
        intake.start();
    }

    @Override
    public void loop() {
        if(gamepad1.x) {
            intake.transfer();
        }

        if(gamepad1.y) {
            intake.ground();
        }
        if(gamepad1.right_bumper) {
            intake.close();
        } else if (gamepad1.left_bumper) {
            intake.open();
        }

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();


      //  telemetry.addData("armState", arm.state);
        intake.telemetry();
        telemetry.update();
    }
}
