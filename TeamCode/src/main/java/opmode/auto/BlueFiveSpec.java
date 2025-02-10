package opmode.auto;

import config.commands.Chamber;
import config.commands.Specimen;
import config.core.Alliance;
import config.core.OpModeCommand;
import config.core.paths.FiveSpec;
import config.subsystems.commands.ExtendFull;
import config.subsystems.commands.OuttakeClose;
import config.subsystems.commands.OuttakeOpen;
import config.core.Robot;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Five Spec")
public class BlueFiveSpec extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, FiveSpec.startPose);
        r.getT().addData("init", true);
        r.getT().update();
    }

    @Override
    public void start() {
        schedule(
                new RunCommand(r::periodic),
                new SequentialCommandGroup(
                        new Chamber(r),
                        new FollowPath(r.getF(), FiveSpec.preload(), true, 1).alongWith(new InstantCommand(() -> r.getT().addData("f", true))),
                        new FollowPath(r.getF(), FiveSpec.pushSamples(), true, 1)
                                .alongWith(
                                        new SequentialCommandGroup(
                                                new OuttakeOpen(r),
                                                new WaitCommand(500),
                                                new Specimen(r)
                                        )
                                ),
                        new WaitCommand(500).andThen(new OuttakeClose(r)),
                        new WaitCommand(250)
                                .andThen(
                                        new FollowPath(r.getF(), FiveSpec.specimen1(), true, 1)
                                                .alongWith(new Chamber(r))
                                ),
                        new FollowPath(r.getF(), FiveSpec.grab2(), true, 1)
                                .alongWith(
                                        new SequentialCommandGroup(
                                                new WaitCommand(700)
                                                        .andThen(new Specimen(r)),
                                                new WaitCommand(1500)
                                                        .andThen(new OuttakeClose(r))
                                        )
                                ),
                        new WaitCommand(250)
                                .andThen(
                                        new FollowPath(r.getF(), FiveSpec.specimen2(), true, 1)
                                )
                                .alongWith(
                                        new Chamber(r)
                                ),
                        new FollowPath(r.getF(), FiveSpec.grab3(), true, 1)
                                .alongWith(
                                        new SequentialCommandGroup(
                                                new WaitCommand(700)
                                                        .andThen(new Specimen(r)),
                                                new WaitCommand(1500)
                                                        .andThen(new OuttakeClose(r))
                                        )
                                ),
                        new WaitCommand(250)
                                .andThen(
                                        new FollowPath(r.getF(), FiveSpec.specimen3(), true, 1)
                                )
                                .alongWith(
                                        new Chamber(r)
                                ),
                        new FollowPath(r.getF(), FiveSpec.grab4(), true, 1)
                                .alongWith(
                                        new SequentialCommandGroup(
                                                new WaitCommand(700)
                                                        .andThen(new Specimen(r)),
                                                new WaitCommand(1500)
                                                        .andThen(new OuttakeClose(r))
                                        )
                                ),
                        new WaitCommand(250)
                                .andThen(
                                        new FollowPath(r.getF(), FiveSpec.specimen4(), true, 1)
                                )
                                .alongWith(
                                        new Chamber(r)
                                ),
                        new FollowPath(r.getF(), FiveSpec.park(), true, 1)
                                .alongWith(
                                        new SequentialCommandGroup(
                                                new OuttakeOpen(r),
                                                new ExtendFull(r),
                                                new InstantCommand(() -> r.getI().hover())
                                        )
                                )
                )
        );
    }

    @Override
    public void loop() {
        super.loop();
        r.periodic();
    }

    @Override
    public void stop() {
        super.stop();
        r.stop();
    }
}
