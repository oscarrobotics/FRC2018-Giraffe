package org.usfirst.frc.team832.robot.subsystems;

 import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.commands.defaults.RunIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
	public static VictorSPX intakeMotorLeft = RobotMap.leftIntake;
	public static VictorSPX intakeMotorRight = RobotMap.rightIntake;
	public double intakeStickX, intakeStickY;
	private final double maxSpeed = 1.0;
	
	public void stop(){
		intakeMotorLeft.set(ControlMode.PercentOutput, 0);
		intakeMotorRight.set(ControlMode.PercentOutput, 0);		
	}
	
	public void IntakeAuto(double pow) {
		intakeLinear(pow);
	}
	
	public void IntakeWithStick(double x, double y) {
		boolean limitSwitch = !RobotMap.intakeElbow.getSensorCollection().isRevLimitSwitchClosed();
//		boolean limitSwitch = true;
//		// deadzone
		if(Math.abs(x) < .2)
			x = 0;
		else if(x > .2 && x <= .5)
			x = .5;
		else if (x > .5)
			x = 1.0;
		else if(x <-.2 && x >= -.5)
			x = -.5;
		else if (x < -.5)
			x = -1.0;
		
		if(Math.abs(y) < .2)
			y = 0;
		else if(y > .2 && y <= .5)
			y = .5;
		else if (y > .5)
			y = 1.0;
		else if(y <-.2 && y >= -.5)
			y = -.5;
		else if (y < -.5)
			y = -1.0;
		y = -y;
		SmartDashboard.putNumber("spin", x);
		SmartDashboard.putNumber("linear", y);
//		// limit switch
		//if (limitSwitch) {
			if (Math.abs(y) > 0.2) {
				intakeLinear(y);
			}
			else if(Math.abs(x) > 0.2){
				intakeSpin(x);
			}
			else {
				intakeLinear(-.1);
				
				intakeSpin(0);
			}
		/*}
		else {
			intakeSpin(0);
			if(y < -0.2) intakeLinear(y);
		}*/
	}
	
	private void intakeSpin(double speed){
		intakeMotorLeft.set(ControlMode.PercentOutput, speed * maxSpeed);
		intakeMotorRight.set(ControlMode.PercentOutput, -speed * maxSpeed);
	}
	
	private void intakeLinear(double speed){
		intakeMotorLeft.set(ControlMode.PercentOutput, speed * maxSpeed);
		intakeMotorRight.set(ControlMode.PercentOutput, speed * maxSpeed);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new RunIntake());

	}

}
