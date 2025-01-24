package indubitables.opmode;

import static indubitables.config.core.Robot.autoEndPose;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import indubitables.config.core.Alliance;
import indubitables.config.core.Robot;
import indubitables.config.core.gamepad.ExGamepad;

public class Drive extends OpMode {

    Robot r;

    @Override
    public void init() {
        r = new Robot(hardwareMap, telemetry, new ExGamepad(gamepad1), new ExGamepad(gamepad2), Alliance.BLUE, autoEndPose);
    }

    @Override
    public void start() {
        r.teleopStart();
    }

    @Override
    public void loop() {
        r.periodic();
    }
}
