package indubitables.config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import indubitables.config.core.Robot;

public class OuttakeClose extends CommandBase {
    private final Robot robot;

    public OuttakeClose(Robot robot) {
        this.robot = robot;
        addRequirements(robot);
    }

    @Override
    public void initialize() {
        robot.getO().close();
    }
}
