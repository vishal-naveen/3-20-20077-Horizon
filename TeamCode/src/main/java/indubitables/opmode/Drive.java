package indubitables.opmode;

import static indubitables.config.core.Robot.autoEndPose;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import indubitables.config.core.Alliance;
import indubitables.config.core.OpModeCommand;
import indubitables.config.core.Robot;
import indubitables.config.core.gamepad.ExGamepad;

@TeleOp(name = "Drive")
public class Drive extends OpMode {

    Robot r;

    @Override
    public void init() {
        r = new Robot(hardwareMap, telemetry, gamepad1 , gamepad2, Alliance.BLUE, autoEndPose);
    }

    @Override
    public void start() {
        r.teleopStart();
    }

    @Override
    public void loop() {
        r.updateControls(gamepad1, gamepad2);
        r.periodic();
    }


}
