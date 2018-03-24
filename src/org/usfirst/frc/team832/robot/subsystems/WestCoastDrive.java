package org.usfirst.frc.team832.robot.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team832.robot.Constants;
import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.commands.auto.AutoDriveProfile;
import org.usfirst.frc.team832.robot.commands.defaults.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WestCoastDrive extends Subsystem {

	TalonSRX left1 = RobotMap.left1;
	TalonSRX right1 = RobotMap.right1;
	VictorSPX left2 = RobotMap.left2;
	VictorSPX right2 = RobotMap.right2;
	VictorSPX left3 = RobotMap.left3;
	VictorSPX right3 = RobotMap.right3;
	int highSpeedRatio = 2*1200;
	int lowSpeedRatio = 2*590;
	double highSpeedkF = 0.8;
	double lowSpeedkF = 1.7;
	MotionProfileStatus leftMpStatus= new MotionProfileStatus();
	MotionProfileStatus rightMpStatus = new MotionProfileStatus();

//	public enum Gear {
//    	HIGH,
//    	LOW;
//    }
//	public Gear currentGear() {
//    	if(RobotMap.gearShiftSol.get() == Value.kReverse) return Gear.HIGH;
//    	else return Gear.LOW;
//    }
	
	public void TankDrive(double leftPow, double rightPow) {
		left1.set(ControlMode.PercentOutput, leftPow);
		right1.set(ControlMode.PercentOutput, rightPow);
	}
	public void TankDriveSpeed(double leftPow, double rightPow){
//		if(RobotMap.gearShiftSol.get() == Value.kReverse){
//			left1.set(ControlMode.Velocity, leftPow*highSpeedRatio);
//			right1.set(ControlMode.Velocity, rightPow*highSpeedRatio);
//		}
//		else {
//			left1.set(ControlMode.Velocity, leftPow*lowSpeedRatio);
//			right1.set(ControlMode.Velocity, rightPow*lowSpeedRatio);
//		}
	}
	
	public void ArcadeDrive(double pow, double rot, ControlMode ctrlMode) {
		double leftMotorSpeed, rightMotorSpeed;
		double moveValue = pow;
		double rotateValue = rot;
		if (moveValue > 0.0) {
		      if (rotateValue > 0.0) {
		        leftMotorSpeed = moveValue - rotateValue;
		        rightMotorSpeed = Math.max(moveValue, rotateValue);
		      } else {
		        leftMotorSpeed = Math.max(moveValue, -rotateValue);
		        rightMotorSpeed = moveValue + rotateValue;
		      }
		    } else {
		      if (rotateValue > 0.0) {
		        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
		        rightMotorSpeed = moveValue + rotateValue;
		      } else {
		        leftMotorSpeed = moveValue - rotateValue;
		        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
		      }
		    }
		left1.set(ctrlMode, leftMotorSpeed);
		right1.set(ctrlMode, rightMotorSpeed);
	}
	
	public void AutoRotate(double yotation) {
		ArcadeDrive(0, yotation, ControlMode.PercentOutput);
	}

	public void AutoRotateSpeed(double rotation) {ArcadeDriveSpeed(0, rotation);}
	
	public void ArcadeDriveSpeed(double power, double rot){
		if(RobotMap.gearShiftSol.get() == Value.kReverse){
			power = power*highSpeedRatio;
			rot = rot*highSpeedRatio;
			left1.config_kF(0, highSpeedkF, 0);
			right1.config_kF(0, highSpeedkF, 0);
		}
		else {
			power = power*lowSpeedRatio;
			rot = rot*lowSpeedRatio;
			left1.config_kF(0, lowSpeedkF, 0);
			right1.config_kF(0, lowSpeedkF, 0);
		}
		ArcadeDrive(power, rot, ControlMode.Velocity );
	}

	public void ArcadeDriveSpeedStraight(double speed){
	    ArcadeDrive(speed, 0, ControlMode.Velocity);
    }

    @Override
	public void periodic() {
		this.left1.processMotionProfileBuffer();
		this.right1.processMotionProfileBuffer();

		this.left1.getMotionProfileStatus(this.leftMpStatus);
		this.right1.getMotionProfileStatus(this.rightMpStatus);
	}

    public void resetEncoders() {
		left1.setSelectedSensorPosition(0, 0, 0);
		right1.setSelectedSensorPosition(0, 0, 0);
	}

	public MotionProfileStatus getLeftMpStatus() {
		return leftMpStatus;
	}

	public MotionProfileStatus getRightMpStatus() {
		return rightMpStatus;
	}

	private TrajectoryPoint.TrajectoryDuration GetTrajectoryDuration(int durationMs) {
		/* create return value */
		TrajectoryPoint.TrajectoryDuration retval = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
		retval = retval.valueOf(durationMs);
		/* check that it is valid */
		if (retval.value != durationMs) {
			DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
		}
		/* pass to caller */
		return retval;
	}

	public void leftMpControl(SetValueMotionProfile v) {
		left1.set(ControlMode.MotionProfile, v.value);
	}

	public void rightMpControl(SetValueMotionProfile v) {
		right1.set(ControlMode.MotionProfile, v.value);
	}

	public void startFillingLeft(double[][] profile, int size) {
		this.left1.clearMotionProfileTrajectories();
		TrajectoryPoint point = new TrajectoryPoint();

		this.left1.configMotionProfileTrajectoryPeriod(50, 10);

		for (int i = 0; i < size; i++) {
			double positionRot = profile[i][0] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
			double velocityRPM = profile[i][1] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
			/* for each point, fill our structure and pass it to API */
			point.position = -positionRot * (256.0 * 4.0); // Convert Revolutions to Units TODO: Was *4
			point.velocity = velocityRPM * (256.0 * 4) / 10.0; // Convert RPS to Units/100ms
			point.headingDeg = 0; /* future feature - not used in this example*/
			point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			point.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
			point.timeDur = GetTrajectoryDuration((int) profile[i][2]);
			point.zeroPos = i == 0;
			point.isLastPoint = (i == size); // HACK: isLastPoint points seem to not play nice with MP

			this.left1.pushMotionProfileTrajectory(point);
		}
		System.out.println(String.format("Pushed %d points to left.", size));
	}

	public void startFillingRight(double[][] profile, int size) {
		this.right1.clearMotionProfileTrajectories();
		TrajectoryPoint point = new TrajectoryPoint();

		this.right1.configMotionProfileTrajectoryPeriod(50, 10);

		for (int i = 0; i < size; i++) {
			double positionRot = profile[i][0] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
			double velocityRPM = profile[i][1] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
			/* for each point, fill our structure and pass it to API */
			point.position = -positionRot * (256.0 * 4.0); // Convert Revolutions to Units
			point.velocity = velocityRPM * (256.0 * 4.0) / 10.0; // Convert RPS to Units/100ms
			point.headingDeg = 0; /* future feature - not used in this example*/
			point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			point.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
			point.timeDur = GetTrajectoryDuration((int) profile[i][2]);
			point.zeroPos = i == 0;
			point.isLastPoint = (i == size); // HACK: isLastPoint points seem to not play nice with MP

			this.right1.pushMotionProfileTrajectory(point);
		}
		System.out.println(String.format("Pushed %d points to right.", size));
	}

    public void goStraight(double distance){
		RobotMap.right1.set(ControlMode.MotionMagic,RobotMap.right1.getSelectedSensorPosition(0)+distance);
		RobotMap.left1.set(ControlMode.MotionMagic, RobotMap.left1.getSelectedSensorPosition(0)+distance);
	}

	public void turn15(){
		double distance = 12.5/6*1024/12;
		double last =  RobotMap.right1.getSelectedSensorPosition(0);
		RobotMap.right1.set(ControlMode.MotionMagic, RobotMap.right1.getSelectedSensorPosition(0)+distance);
		RobotMap.left1.set(ControlMode.MotionMagic, RobotMap.left1.getSelectedSensorPosition(0)-distance);
		while(Math.abs(Math.abs(RobotMap.right1.getSelectedSensorPosition(0))-Math.abs(last+distance))>10);

	}

	public void turnAngle(double angle){

		final double dis = 12.5*2*Math.PI/6*1024;
		double last =  RobotMap.right1.getSelectedSensorPosition(0);
		double rad = angle/180*Math.PI;
		double distance = dis * rad;
		RobotMap.right1.set(ControlMode.MotionMagic, RobotMap.right1.getSelectedSensorPosition(0)+distance);
		RobotMap.left1.set(ControlMode.MotionMagic, RobotMap.left1.getSelectedSensorPosition(0)-distance);
		while(Math.abs(Math.abs(RobotMap.right1.getSelectedSensorPosition(0))-Math.abs(last+distance))>100);

	}

	public void gyroturnAngle(double angle){
		double currentAngle = RobotMap.navx.getYaw(); //gyro posiron
		double delt = (currentAngle-angle)%180;
		turnAngle(delt);
		currentAngle = RobotMap.navx.getYaw();
		while(currentAngle-angle>10){
			delt = (currentAngle-angle)%180;
			turnAngle(delt);
			currentAngle = RobotMap.navx.getYaw();
		}
	}


	@Override
	protected void initDefaultCommand() {
//		setDefaultCommand(new RobotDriveMP());
		setDefaultCommand(new RobotDriveSpeed());
		//setDefaultCommand(new AutoDriveProfile( "/home/lvuser/paths/2FT90_2FT_right_detailed.csv", "/home/lvuser/paths/2FT90_2FT_left_detailed.csv"));
	}
	
	public double getMin()
    {
    	return Math.min(Math.abs(RobotMap.left1.getSelectedSensorPosition(RobotMap.DrivePIDID)), Math.abs(RobotMap.right1.getSelectedSensorPosition(RobotMap.DrivePIDID)));
    }
    
    public double getMax()
    {
    	return Math.max(RobotMap.left1.getSelectedSensorPosition(RobotMap.DrivePIDID), RobotMap.right1.getSelectedSensorPosition(RobotMap.DrivePIDID));
    }
    
    public void stop(){
    	Robot.westCoastDrive.TankDriveSpeed(0, 0);
    }

}
