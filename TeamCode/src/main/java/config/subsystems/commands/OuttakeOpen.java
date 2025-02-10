package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import config.core.Robot;

public class OuttakeOpen extends CommandBase {
    private final Robot robot;

    public OuttakeOpen(Robot robot) {
        this.robot = robot;

    }

    @Override
    public void initialize() {
        robot.getO().open();
    }
}
