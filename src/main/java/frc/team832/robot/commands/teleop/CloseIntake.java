package frc.team832.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team832.robot.Robot;

public class CloseIntake extends InstantCommand {

    public CloseIntake() {
        addRequirements(Robot.pneumatics);
    }

    public void initialize() {
        Robot.pneumatics.closeIntake();
    }
}
