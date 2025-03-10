package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Timer;

import config.core.ManualInput;
import config.core.ManualPose;
import config.core.Robot;
import config.core.paths.SixSpec;

public class Align extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();
    private ManualPose manualPose;

    public Align(Robot robot, ManualPose manualPose) {
        this.robot = robot;
        this.manualPose = manualPose;
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        robot.getT().addData("a state", state);
        robot.getT().update();
        switch (state) {
            case 1:
                robot.getI().rotateDegrees(manualPose.getRotation());//robot.getV().getBestDetectionAngle());
                robot.getE().toFull();
                SixSpec.sub2 = manualPose.getPose().copy();
                robot.getF().followPath(SixSpec.sub2());
                setState(2);
                break;
            case 2:
             //   if(robot.getF().getPose().roughlyEquals(SixSpec.sub1, 0.25)) {
                    if(!robot.getF().isBusy()) {
                        setState(-1);
                    }
             //   }
                break;
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

