package opmode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import config.subsystems.Lift;
import config.subsystems.Outtake;

@Config
@TeleOp(group = "zzz", name = "Lift Test")
public class LiftTest extends OpMode {
    public static int target = 0;

    Lift l;
    Outtake o;

    @Override
    public void init() {
        l = new Lift(hardwareMap, telemetry);
        o = new Outtake(hardwareMap, telemetry);

        o.score();
    }

    @Override
    public void loop() {
        if (gamepad1.a)
            l.setTarget(target);
        else {
            l.manual(gamepad1.left_trigger, gamepad1.right_trigger);
        }

        o.score();

        l.periodic();
        telemetry.update();
    }
}
