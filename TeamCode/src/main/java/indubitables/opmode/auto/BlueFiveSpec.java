package indubitables.opmode.auto;

import indubitables.config.commands.*;
import indubitables.config.core.Robot;
import indubitables.config.core.paths.*;
import indubitables.config.core.*;
import indubitables.config.subsystems.commands.*;
import com.arcrobotics.ftclib.command.*;
import com.pedropathing.commands.FollowPath;

public class BlueFiveSpec extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, FiveSpec.startPose);
        r.register();
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().schedule(
                new ExtendZero(r)
                        .alongWith(new Chamber(r)),
                new FollowPath(r.getF(), FiveSpec.preload(), true, 1),
                new FollowPath(r.getF(), FiveSpec.pushSamples(), true, 1)
                        .alongWith(new OuttakeOpen(r))
                        .alongWith(new WaitCommand(500)
                                .andThen(new Specimen(r))),
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
                                new OuttakeOpen(r),
                                new ExtendFull(r),
                                new InstantCommand(() -> r.getI().hover())
                        )
        );
        CommandScheduler.getInstance().enable();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
        r.stop();
    }
}
