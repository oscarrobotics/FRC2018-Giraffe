package org.usfirst.frc.team832.robot.commands.defaults;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.RobotMode;

public class RunElevatorStage1 extends Command {
		
	public RunElevatorStage1(){
		requires(Robot.elevatorStage1);
	}
	
	protected void initialize() {
		//while(Robot.currentRobotMode != RobotMode.AUTONOMOUS)

		Robot.elevatorStage1.stop();

		Robot.elevatorStage1.start();
	}
	
	protected void execute() {
//		if (Robot.oi.operatorPad.getRawButton(5))
//			Robot.elevatorStage1.setPos(1.0);
//		else if (Robot.oi.operatorPad.getRawButton(6))
//			Robot.elevatorStage1.setPos(0.5);
//		else if (Robot.oi.operatorPad.getRawButton(7))
//			Robot.elevatorStage1.setPos(-1.0);
//		else
//			Robot.elevatorStage1.setPos(sliderVal);



		double sliderVal = Robot.oi.operatorPad.getRawAxis(2);
		if (Robot.oi.operatorPad.getRawButton(5))
			Robot.elevatorStage1.setPos(1.0);
		else if (Robot.oi.operatorPad.getRawButton(6))
			Robot.elevatorStage1.setPos(0.5);
		else if (Robot.oi.operatorPad.getRawButton(7))
			Robot.elevatorStage1.setPos(-1.0);
		else
			Robot.elevatorStage1.setPos(sliderVal);
	}
		
	protected boolean isFinished() {
		return false;
	}

	protected  void end() {
		Robot.elevatorStage1.stop();
	}
	
	protected void interrupted() {
		end();
	}
}
