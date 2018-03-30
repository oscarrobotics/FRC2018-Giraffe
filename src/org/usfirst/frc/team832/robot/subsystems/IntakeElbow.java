package org.usfirst.frc.team832.robot.subsystems;

import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.RobotMode;
import org.usfirst.frc.team832.robot.commands.defaults.RunIntake;
import org.usfirst.frc.team832.robot.commands.defaults.RunIntakeElbow;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeElbow extends Subsystem {
	
	private static final int maxEncPos = 2200;
	private static final int acceptableError = 90;
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
	
	public boolean getAtBottom() { return RobotMap.intakeElbow.getSensorCollection().isFwdLimitSwitchClosed(); } //TODO: Was forward, set to reverse for practice bot

	public void setAtBottom() 
	{	
		RobotMap.intakeElbow.setSelectedSensorPosition(0, RobotMap.IntakeElbowPIDID, 0); 
	}

	public void setAtTop() 
	{ 
		RobotMap.intakeElbow.setSelectedSensorPosition(2220, RobotMap.IntakeElbowPIDID, 0); 
	}
	
	public void setPos(double rotationVal) {
		//System.out.println("Potentiometer in: " + rotationVal);
		if (rotationVal >= maxPotVal)
			rotationVal = maxPotVal;
		if(rotationVal <= minPotVal)
			rotationVal = minPotVal;
		rv = rotationVal;
		//System.out.println("Potentiometer out: " +rv);
		//intakeElbowTargetPos = maxEncPos - maxEncPos * rotationVal * (1 / maxPotVal);
		intakeElbowTargetPos = 2200 - (2200 * (rv/.65));
		//System.out.println("Intake Target Pos: " + intakeElbowTargetPos);
		//intakeElbowTargetPos = ((2250 - (2250 * rv/.65)) +350);
		RobotMap.intakeElbow.set(ControlMode.Position, intakeElbowTargetPos);
		//System.out.println("Intake Elbow Error: " + RobotMap.intakeElbow.getClosedLoopError(0));
		//System.out.println("Intake Elbow Enc Pos: " + RobotMap.intakeElbow.getSelectedSensorPosition(0));
	}
	
	public void setAutoPos(int targetPos) {
		intakeElbowTargetPos = targetPos;
		RobotMap.intakeElbow.set(ControlMode.Position, intakeElbowTargetPos);

	}
	
	public boolean getIsFinished() {
		int currentError = RobotMap.intakeElbow.getSelectedSensorPosition(RobotMap.IntakeElbowPIDID)-RobotMap.intakeElbow.getClosedLoopTarget(0);
		return (Math.abs(currentError) <= acceptableError);
		//return false; Testing only
	}
	
	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new RunIntakeElbow());
	}
}
