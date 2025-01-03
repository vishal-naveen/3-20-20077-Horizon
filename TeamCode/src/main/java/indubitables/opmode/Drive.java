package indubitables.opmode;

import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.pedropathing.follower.Follower;

import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;
import indubitables.config.old.runmodes.Teleop;


@TeleOp(name="Drive", group="A")
public class Drive extends OpMode {

    private Teleop teleop;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        teleop = new Teleop(hardwareMap, telemetry, new Follower(hardwareMap), observationParkPose, false, gamepad1, gamepad2);
        teleop.init();
    }

    @Override
    public void start() {
        teleop.start();
    }

    @Override
    public void loop() {
        teleop.update();
    }

}
