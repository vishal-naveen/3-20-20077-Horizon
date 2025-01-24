package indubitables.config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import indubitables.config.core.Robot;

public class ExtendFull extends CommandBase {
    private final Robot robot;

    public ExtendFull(Robot robot) {
        this.robot = robot;
        addRequirements(robot);
    }

    @Override
    public void initialize() {
        robot.getE().toFull();
    }
}
