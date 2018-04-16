package org.usfirst.frc.team832.robot.subsystems;

import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.commands.defaults.RunElevatorStage2;
import org.usfirst.frc.team832.robot.func.Calcs;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorStage2 extends Subsystem {

	private static double minEncPos = 1900;
	private static final double maxEncPos = 26000 - minEncPos ;
	private static final double lowerposthres = 5000;
	private static final int acceptableError = 425;
	public static double targetPosition;
	
	public boolean getAtBottom() {
		return !RobotMap.elevatorMotorStage2.getSensorCollection().isRevLimitSwitchClosed();
	}
	
	public void setAtBottom() {
		RobotMap.elevatorMotorStage2.setSelectedSensorPosition(0, RobotMap.ElevatorStage2PIDID, 0);
	}
	
	public void start() { RobotMap.elevatorMotorStage2.set(ControlMode.Position, 0); }
		
	public void stop() { RobotMap.elevatorMotorStage2.set(ControlMode.PercentOutput, 0); }
	
	public void setPos(double sliderVal) {
		if(RobotMap.elevatorMotor1.getSelectedSensorPosition(0) >= 1900)
			minEncPos = 0;
		else
			minEncPos = 1900;

		if(RobotMap.elevatorMotorStage2.getSelectedSensorPosition(0)>lowerposthres){
			RobotMap.elevatorMotorStage2.configPeakOutputReverse(-100,0);
		}
		if(RobotMap.elevatorMotorStage2.getSelectedSensorPosition(0)<lowerposthres){
			RobotMap.elevatorMotorStage2.configPeakOutputReverse(-0.1,0);
		}


		targetPosition = Math.round( minEncPos + (Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos));
		RobotMap.elevatorMotorStage2.set(ControlMode.Position, targetPosition);
	}
	
	public void setAutoPos(double targetPos) {
		setPos(targetPos);
	}
	
	public boolean isFinished() {
		double clError = RobotMap.elevatorMotorStage2.getSelectedSensorPosition(0) - RobotMap.elevatorMotorStage2.getClosedLoopTarget(0);
		System.out.println("Stage 2 error: " +  clError);
		return (Math.abs(clError) <= acceptableError);
	}
	
	@Override
	protected void initDefaultCommand() {}
}