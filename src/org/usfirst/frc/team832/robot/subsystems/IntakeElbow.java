package org.usfirst.frc.team832.robot.subsystems;

import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.commands.defaults.RunIntakeElbow;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeElbow extends Subsystem {
	
	private static final int maxEncPos = 2200;
	private static final int acceptableError = 5;
	private static final double minPotVal = 0;
	private static final double maxPotVal = 0.65;
	public static double intakeElbowTargetPos;
	public static double rv;
	//public static double potMap(double rotationVal) { return Math.round( map(rotationVal, minPotVal, maxPotVal, 0.0, 1.0) * maxEncPos); }

	public void stop()
	{
		RobotMap.intakeElbow.set(ControlMode.PercentOutput, 0);
	}
	
	public void start() 
	{ 
		RobotMap.intakeElbow.set(ControlMode.Position, 1600); 
	}
	
	
	public void setAtBottom() 
	{	
		RobotMap.intakeElbow.setSelectedSensorPosition(0, RobotMap.IntakeElbowPIDID, 0); 
	}

	public void setAtTop() 
	{ 
		RobotMap.intakeElbow.setSelectedSensorPosition(2220, RobotMap.IntakeElbowPIDID, 0); 
	}
	
	public void setPos(double rotationVal) {
		if (rotationVal >= maxPotVal)
			rotationVal = maxPotVal;
		if(rotationVal <= minPotVal)
			rotationVal = minPotVal;
		rv = rotationVal;
		
		//intakeElbowTargetPos = maxEncPos - maxEncPos * rotationVal * (1 / maxPotVal);
		intakeElbowTargetPos = 2200 - (2200 * rv/.65);
		RobotMap.intakeElbow.set(ControlMode.Position, intakeElbowTargetPos);
	}
	
	public void setAutoPos(int targetPos) {
		RobotMap.intakeElbow.set(ControlMode.Position, targetPos);
	}
	
	public boolean getIsFinished() {
		int currentError = RobotMap.intakeElbow.getClosedLoopError(RobotMap.IntakeElbowPIDID);
		//return (Math.abs(currentError) > acceptableError);
		return false;
	}
	
	@Override
	protected void initDefaultCommand() { 
		setDefaultCommand(new RunIntakeElbow());
	}
}
