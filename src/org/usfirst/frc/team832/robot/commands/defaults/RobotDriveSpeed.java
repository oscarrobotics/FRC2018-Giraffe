package org.usfirst.frc.team832.robot.commands.defaults;

import org.usfirst.frc.team832.robot.OI;
import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.subsystems.WestCoastDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RobotDriveSpeed extends Command {
	
	public final int brakingHeightThreshold = 15000; // change to adjust coast/brake switch height

	WestCoastDrive westCoastDrive = Robot.westCoastDrive;
    public RobotDriveSpeed() {
        requires(Robot.westCoastDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftStick = 1 * Math.pow(OI.driverPad.getRawAxis(1),5); // linear
    	double rightStick = .4 * OI.driverPad.getRawAxis(4); // rotation

        if(OI.driverPad.getRawButton(1))
            westCoastDrive.ArcadeDriveSpeedStraight(-250);
        else
            westCoastDrive.ArcadeDriveSpeed(leftStick, rightStick);
    	
    	// Implemented by George on 3/16/2018
    	// Alternates between brake and coast depending on elevator height 
/*    	double target = Robot.elevatorStage1.targetPosition + Robot.elevatorStage2.targetPosition;
    	if(target > brakingHeightThreshold)
    	{
    		RobotMap.left1.setNeutralMode(NeutralMode.Coast);
    		RobotMap.left2.setNeutralMode(NeutralMode.Coast);
    		RobotMap.left3.setNeutralMode(NeutralMode.Coast);
    		RobotMap.right1.setNeutralMode(NeutralMode.Coast);
    		RobotMap.right2.setNeutralMode(NeutralMode.Coast);
    		RobotMap.right3.setNeutralMode(NeutralMode.Coast);
    	}
    	else
    	{
    		RobotMap.left1.setNeutralMode(NeutralMode.Brake);
    		RobotMap.left2.setNeutralMode(NeutralMode.Brake);
    		RobotMap.left3.setNeutralMode(NeutralMode.Brake);
    		RobotMap.right1.setNeutralMode(NeutralMode.Brake);
    		RobotMap.right2.setNeutralMode(NeutralMode.Brake);
    		RobotMap.right3.setNeutralMode(NeutralMode.Brake);
    		
    	} */
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
