package frc.team832.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;

public class ShiftLow extends Command {

    public ShiftLow() {
        requires(Robot.pneumatics);
    }

    protected void execute() {
        Robot.pneumatics.shiftToLow();
    }

    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        end();
    }
}
