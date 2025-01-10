package indubitables.config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import indubitables.config.core.Robot;

public class Submersible extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Submersible(Robot robot) {
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
                robot.getI().ground();
                robot.getI().open();
                robot.getO().transfer();
                setState(2);
                break;
            case 2:
                if(timer.getElapsedTimeSeconds() > 0.3) {
                    robot.getI().close();
                    setState(3);
                }
                break;
            case 3:
                if (timer.getElapsedTimeSeconds() > 0.25) {
                    robot.getI().hover();
                    setState(-1);
                }
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

