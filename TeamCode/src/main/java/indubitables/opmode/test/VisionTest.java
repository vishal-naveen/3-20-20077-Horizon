package indubitables.opmode.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;
import indubitables.config.subsystems.intake.Intake;
import indubitables.config.old.subsystem.VisionSubsystem;
import com.pedropathing.follower.Follower;

@Config
@TeleOp(name="visionTest", group="b")
public class VisionTest extends OpMode {

    private Intake intake;
    private Intake.GrabState intakeGrabState;
    private Intake.RotateState intakeRotateState;
    private Intake.PivotState intakePivotState;

    private VisionSubsystem vision;

    private Follower follower;
    private Gamepad currentGamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        intake = new Intake(hardwareMap, telemetry, intakeGrabState, intakeRotateState, intakePivotState);
        follower = new Follower(hardwareMap);
        intake.init();
        vision = new VisionSubsystem(hardwareMap, telemetry, "blue", intake);
        vision.init();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
        intake.start();
    }

    @Override
    public void loop() {
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);

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

        if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down) {
            vision.clawAlign();
        }

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();


        //  telemetry.addData("armState", arm.state);
        intake.telemetry();
        telemetry.update();
    }
}
