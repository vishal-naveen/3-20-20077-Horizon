package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class Preload extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Preload(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        setState(0);
    }

    @Override
    public void execute() {
        switch (state) {
            case 0:
                robot.getO().preload();
                robot.getE().toZero();
                setState(1);
                break;
            case 1:
                if (timer.getElapsedTimeSeconds() > 1.4) {
                    robot.getO().afterSpecScore();
                    setState(2);
                }
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 0.25) {
                    robot.getO().specimenGrab180();
                    robot.getE().toZero();
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

