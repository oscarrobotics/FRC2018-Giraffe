package frc.team832.robot.commands.defaults;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.OI;
import frc.team832.robot.Robot;
import frc.team832.robot.subsystems.WestCoastDrive;

/**
 *
 */
public class RobotDrive extends Command {

    final WestCoastDrive westCoastDrive = Robot.westCoastDrive;

    public RobotDrive() {
        requires(Robot.westCoastDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double leftStick = .7 * OI.driverPad.getRawAxis(1);
        double rightStick = .7 * OI.driverPad.getRawAxis(4);
        westCoastDrive.ArcadeDrive(leftStick, rightStick, ControlMode.PercentOutput);
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
