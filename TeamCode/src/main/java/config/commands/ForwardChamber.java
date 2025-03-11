package config.commands;

import static config.core.RobotConstants.liftAfterHighChamber;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class ForwardChamber extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public ForwardChamber(Robot robot) {
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
                if(timer.getElapsedTimeSeconds() > 0) {
                    robot.getO().close();
                    setState(1);
                }
                break;
            case 1:
                if (timer.getElapsedTimeSeconds() > 0.1) {
                    robot.getO().specimenScore0();
                    robot.getI().hover();
                    robot.getE().toFull();
                    robot.getL().toChamber();
                    setState(2);
                }
                break;
            case 2:
                if (!robot.getF().isBusy()) {
                    robot.getL().toZero();
                    setState(3);
                }
                break;
            case 3:
                if (robot.getL().getPos() <= 500) {
                    robot.getO().open();
                    setState(4);
                }
                break;
            case 4:
                if (timer.getElapsedTimeSeconds() > 0.25) {
                    robot.getO().transfer();
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

