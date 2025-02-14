package opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import config.core.SubmersibleVisualization;
@Autonomous
public class SubVisualizerTest extends OpMode {

    SubmersibleVisualization sub = new SubmersibleVisualization(telemetry);
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
