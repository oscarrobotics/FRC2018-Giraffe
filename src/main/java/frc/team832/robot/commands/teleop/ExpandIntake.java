package frc.team832.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team832.robot.Robot;

public class ExpandIntake extends InstantCommand {
    public ExpandIntake() {
        addRequirements(Robot.pneumatics);
    }

    @Override
    public void initialize() {
        Robot.pneumatics.expandIntake();
    }
}
