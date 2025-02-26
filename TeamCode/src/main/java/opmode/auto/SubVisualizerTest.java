package opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import config.core.SubVis;
@Autonomous
public class SubVisualizerTest extends OpMode {

    SubVis sub = new SubVis(telemetry);
    @Override
    public void init() {

    }

    @Override
    public void init_loop() {
        if(gamepad1.a) {
            sub.moveBackward(1);
        }

        if (gamepad1.b) {
            sub.moveRight(1);
        }

        sub.update();
    }

    @Override
    public void loop() {

    }
}
