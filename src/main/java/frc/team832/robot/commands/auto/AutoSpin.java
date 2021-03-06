package frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;

/**
 *
 */
public class AutoSpin extends Command {
    protected final double angle;

    public AutoSpin(double angle) {
        this.angle = angle;
        requires(Robot.westCoastDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Robot.gyroPID.setSetpoint(angle);
        //Robot.gyroPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //Robot.westCoastDrive.AutoRotateSpeed(Robot.gyroPID.pidOut);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
