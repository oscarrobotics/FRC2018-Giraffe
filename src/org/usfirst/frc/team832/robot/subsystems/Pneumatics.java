package org.usfirst.frc.team832.robot.subsystems;

import org.usfirst.frc.team832.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {

	public static DoubleSolenoid gearShift = RobotMap.gearShiftSol;
	public static DoubleSolenoid intakeExpand = RobotMap.intakeArmSol;
	public static boolean lowGear;

	
	public void shiftToLow() {
		//gearShift.set(Value.kOff);
		gearShift.set(Value.kForward);
		lowGear = true;
	}
	public void shiftToHigh() {
		//gearShift.set(Value.kOff);
		gearShift.set(Value.kReverse);
		lowGear = false;
	}
	
	public void expandIntake(){
//		intakeExpand.set(Value.kOff);
		intakeExpand.set(Value.kForward);
	}
	
	public void closeIntake(){
//		intakeExpand.set(Value.kOff);
		intakeExpand.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
