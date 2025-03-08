package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Timer;

import config.core.Robot;
import config.core.paths.SixSpec;

public class Align extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Align(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        switch (state) {
            case 1:
                robot.getI().rotateDegrees(robot.getV().getBestDetectionAngle());
                robot.getE().toFull();
                Pose temp = robot.getV().getAlignedPose(robot.getF().getPose());
                robot.getF().followPath(SixSpec.sub1(temp));
                setState(2);
            case 2:
                if(!robot.getF().isBusy()) {
                    setState(-1);
                }
        }
    }

    @Override
    public boolean isFinished() {
        return state == -1;
    }

    public void setState(int x) {
        state = x;
        timer.resetTimer();
    }

}

