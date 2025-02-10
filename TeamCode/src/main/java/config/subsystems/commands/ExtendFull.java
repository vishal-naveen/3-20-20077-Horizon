package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import config.core.Robot;

public class ExtendFull extends CommandBase {
    private final Robot robot;

    public ExtendFull(Robot robot) {
        this.robot = robot;

    }

    @Override
    public void initialize() {
        robot.getE().toFull();
    }
}
