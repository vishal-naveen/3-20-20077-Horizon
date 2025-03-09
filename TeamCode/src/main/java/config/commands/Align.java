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
    private Pose temp;

    public Align(Robot robot) {
        this.robot = robot;
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
                robot.getI().rotateDegrees(0);//robot.getV().getBestDetectionAngle());
                robot.getE().toFull();
                robot.getF().followPath(SixSpec.sub2());
              //  temp = robot.getV().getPose(robot.getF().getPose());
               // robot.getF().holdPoint(SixSpec.sub1);
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

