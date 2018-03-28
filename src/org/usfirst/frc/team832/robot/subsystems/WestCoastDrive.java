package org.usfirst.frc.team832.robot.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team832.robot.Constants;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.commands.defaults.RobotDriveSpeed;

public class WestCoastDrive extends Subsystem {

	private final TalonSRX left1 = RobotMap.left1;
	private final TalonSRX right1 = RobotMap.right1;
	private final int highSpeedRatio = 2*1200;
	private final int lowSpeedRatio = 2*590;
	private final double highSpeedkF = 0.8;
	private final double lowSpeedkF = 1.7;
	private final MotionProfileStatus leftMpStatus= new MotionProfileStatus();
	private final MotionProfileStatus rightMpStatus = new MotionProfileStatus();

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
		if (leftMpStatus.isUnderrun) {
			System.out.println("LEFT UNDERRUN");
		}
		if (rightMpStatus.isUnderrun) {
			System.out.println("RIGHT UNDERRUN");
		}
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
			point.position = -positionRot * (256.0 * 4.0); // Convert Revolutions to Units
			point.velocity = velocityRPM * (256.0 * 4) / 10.0; // Convert RPS to Units/100ms
			point.headingDeg = 0; /* future feature - not used in this example*/
			point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			point.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
			point.timeDur = GetTrajectoryDuration((int) profile[i][2]);
			point.zeroPos = i == 0;
			point.isLastPoint = false; // HACK: isLastPoint points seem to not play nice with MP

			this.left1.pushMotionProfileTrajectory(point);
		}
		System.out.println("LP: " + point.isLastPoint);
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
			point.isLastPoint = false; // HACK: isLastPoint points seem to not play nice with MP

			this.right1.pushMotionProfileTrajectory(point);
		}
		System.out.println("LP: " + point.isLastPoint);
		System.out.println(String.format("Pushed %d points to right.", size));
	}

	@Override
	protected void initDefaultCommand() {}
	
	public double getMin()
    {
    	return Math.min(Math.abs(RobotMap.left1.getSelectedSensorPosition(RobotMap.DrivePIDID)), Math.abs(RobotMap.right1.getSelectedSensorPosition(RobotMap.DrivePIDID)));
    }
    
    public double getMax()
    {
    	return Math.max(RobotMap.left1.getSelectedSensorPosition(RobotMap.DrivePIDID), RobotMap.right1.getSelectedSensorPosition(RobotMap.DrivePIDID));
    }
    
    public void stop(){
		left1.set(ControlMode.PercentOutput, 0);
		right1.set(ControlMode.PercentOutput, 0);
    }

}
