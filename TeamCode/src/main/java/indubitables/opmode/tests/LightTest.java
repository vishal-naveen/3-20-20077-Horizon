package indubitables.opmode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import indubitables.config.core.OpModeCommand;
import indubitables.config.core.gamepad.ExGamepad;
import indubitables.config.core.gamepad.GamepadKeys;
import indubitables.config.subsystems.Light;

@TeleOp(group = "TeleOp", name = "Light Test")
public class LightTest extends OpModeCommand {
    private Light b;
    ExGamepad g;

    @Override
    public void initialize() {
        b = new Light(hardwareMap, telemetry);
        g = new ExGamepad(gamepad1);
        register(b);
    }

    @Override
    public void loop() {


        g.getGamepadButton(GamepadKeys.Button.A)
                .whenActive(b::on).negate().whenActive(b::off);

        super.loop();


    }
}
