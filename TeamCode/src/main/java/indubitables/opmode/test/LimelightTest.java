package indubitables.opmode.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import indubitables.config.pedro.constants.FConstants;
import indubitables.config.pedro.constants.LConstants;
import indubitables.config.old.subsystem.LimelightSubsystem;
import indubitables.config.old.util.FieldConstants;

import com.pedropathing.localization.Pose;

@TeleOp
@Config
public class LimelightTest extends OpMode {

    private LimelightSubsystem limelight;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        limelight = new LimelightSubsystem(hardwareMap, telemetry, FieldConstants.observationStartPose, new Pose(36,72, Math.toRadians(0)));
        limelight.init();
    }

    @Override
    public void loop() {

    }
}
