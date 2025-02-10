package opmode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import config.core.gamepad.ExGamepad;
import config.core.gamepad.GamepadKeys;

@TeleOp
public class GamepadTest extends OpMode {
    private ExGamepad g1;
    private ExGamepad g2;


    @Override
    public void init() {
        g1 = new ExGamepad(gamepad1);
        g2 = new ExGamepad(gamepad2);

        telemetry.addData("Gamepad Test", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("gamepad1", g1);
        telemetry.addData("gamepad2", g2);
        telemetry.addData("g1 pressed", g1.wasJustPressed(GamepadKeys.Button.A));
        telemetry.update();
    }
}
