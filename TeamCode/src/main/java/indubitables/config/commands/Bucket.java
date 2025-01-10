package indubitables.config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import indubitables.config.core.Robot;

public class Bucket extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Bucket(Robot robot) {
        this.robot = robot;
        addRequirements(robot);
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        switch (state) {
            case 1:
                robot.getI().transfer();
                robot.getL().toHighBucket();
                robot.getO().close();
                robot.getE().toZero();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 0.5) {
                    robot.getI().hover();
                    robot.getO().score();
                    setState(3);
                }
                break;
            case 3:
                if (timer.getElapsedTimeSeconds() > 1) {
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

