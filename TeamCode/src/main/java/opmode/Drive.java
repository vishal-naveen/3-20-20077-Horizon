package opmode;

import static config.core.Robot.autoEndPose;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import config.core.Alliance;
import config.core.Robot;

@TeleOp(name = "Drive")
public class Drive extends OpMode {

    Robot r;

    @Override
    public void init() {
        r = new Robot(hardwareMap, telemetry, gamepad1 , gamepad2, Alliance.BLUE, autoEndPose);
    }

    @Override
    public void start() {
        r.tStart();
    }

    @Override
    public void loop() {
        r.updateControls();
        r.periodic();
    }
}
