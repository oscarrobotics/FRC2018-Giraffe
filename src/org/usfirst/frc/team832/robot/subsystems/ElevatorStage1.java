package org.usfirst.frc.team832.robot.subsystems;

import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.commands.defaults.RunElevatorStage1;
import org.usfirst.frc.team832.robot.func.Calcs;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorStage1 extends Subsystem {
	
	private static final double maxEncPos = 26500;
	private static final double lowerposthres = 5000;
	private static final int acceptableError = 100;
	public static double targetPosition;
	
	public boolean getAtBottom() {
		return !RobotMap.elevatorMotor1.getSensorCollection().isRevLimitSwitchClosed();
	}

	public void setAtBottom() {
		RobotMap.elevatorMotor1.setSelectedSensorPosition(0, RobotMap.ElevatorStage1PIDID, 0);
	}	
	
	public void start() { RobotMap.elevatorMotor1.set(ControlMode.Position, 0); }
	
	public void stop() { RobotMap.elevatorMotor1.set(ControlMode.PercentOutput, 0); }
	
	public void setPos(double sliderVal) {
		RobotMap.elevatorMotor1.configPeakOutputReverse(-100,0);
		RobotMap.elevatorMotor1.configNominalOutputReverse(0, 0);
//		if(RobotMap.elevatorMotor1.getSelectedSensorPosition(0)>lowerposthres){
//
//			RobotMap.elevatorMotor1.configPeakOutputReverse(-100,0);
//		}
//		if(RobotMap.elevatorMotor1.getSelectedSensorPosition(0)<lowerposthres){
//			RobotMap.elevatorMotor1.configPeakOutputReverse(-0.1,0);
//		}

		targetPosition = Math.round( Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos);
		RobotMap.elevatorMotor1.set(ControlMode.Position, targetPosition);
	}
	
	public void setAutoPos(double targetPos) {
		targetPosition = targetPos;
		RobotMap.elevatorMotor1.set(ControlMode.Position, targetPosition);
	}
	
	public boolean isFinished() {
		int currentError = RobotMap.elevatorMotor1.getClosedLoopError(RobotMap.ElevatorStage1PIDID);
		return (Math.abs(currentError) > acceptableError);
	}
	
	@Override
	protected void initDefaultCommand() {}
}