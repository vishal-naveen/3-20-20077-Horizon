package indubitables.config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import indubitables.config.core.Robot;
import indubitables.config.subsystems.outtake.Outtake;

public class Transfer extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Transfer(Robot robot) {
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
                robot.getI().close();
                robot.getO().transferHigh();
                robot.getI().transfer();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 0.1) {
                    robot.getO().setRotateState(Outtake.RotateState.TRANSFER);
                    robot.getE().toZero();
                    setState(3);
                }
                break;
            case 3:
                if (timer.getElapsedTimeSeconds() > 0.2) {
                    robot.getO().transfer();
                    setState(4);
                }
                break;
            case 4:
                if (timer.getElapsedTimeSeconds() > 0.25) {
                    robot.getO().close();
                    setState(5);
                }
                break;
            case 5:
                if (timer.getElapsedTimeSeconds() > 0.5) {
                    robot.getO().score();
                    setState(6);
                }
                break;
            case 6:
                if (timer.getElapsedTimeSeconds() > 0) {
                    robot.getI().open();
                    setState(7);
                }
                break;
            case 7:
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
