package org.usfirst.frc.team832.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//navx
	public static AHRS navx;
	
	//elevator
	private static final int elevatorMotor1ID = 8;
	private static final int elevatorMotor2ID = 9;
	private static final int elevatorMotorStage2ID = 10;
	public static TalonSRX elevatorMotor1;
	public static VictorSPX elevatorMotor2;
	public static TalonSRX elevatorMotorStage2;
	
	//drivetrain
	private static final int left1ID = 2;
	private static final int left2ID = 3;
	private static final int left3ID = 4;
	private static final int right1ID = 5;
	private static final int right2ID = 6;
	private static final int right3ID = 7;
	public static TalonSRX left1;
	public static VictorSPX left2;
	public static VictorSPX left3;
	public static TalonSRX right1;
	public static VictorSPX right2;
	public static VictorSPX right3;
	
	//intake
	private static final int leftIntakeID = 12;
	private static final int rightIntakeID = 13;

	public static VictorSPX leftIntake;
	public static VictorSPX rightIntake;
	
	//intakeElbow
	private static final int intakeElbowID = 11;
	public static TalonSRX intakeElbow;
	
	// pneumatics
	private static final int pcmID = 1;
	private static Compressor compressor;
	public static DoubleSolenoid gearShiftSol;
	public static DoubleSolenoid intakeArmSol;
		
	// electronics
	public static final int pdpID = 0;
	private static PowerDistributionPanel powerDP;
	
	public static final int DrivePIDID = 0;
	
	public static final int ElevatorStage1PIDID = 0;
	public static final double ElevatorStage1kP = 0.45;
	public static final double ElevatorStage1kI = 0.0;
	public static final double ElevatorStage1kD = 0.0;
	public static final double ElevatorStage1kF = 0.0;
	
	public static final int ElevatorStage2PIDID = 0;
	public static final double ElevatorStage2kP = 0.6;
	public static final double ElevatorStage2kI = 0.0;
	public static final double ElevatorStage2kD = 7.0;
	public static final double ElevatorStage2kF = 0.0;
	
	public static final int IntakeElbowPIDID = 0;
	public static final double IntakeElbowkP = .6;
	public static final double IntakeElbowkI = .0;
	public static final double IntakeElbowkD = .0;
	public static final double IntakeElbowkF = .0;
	public static void init()
	{
		// navx
		try {
			//navx = new AHRS(SerialPort.Port.kUSB);
			//TODO: UNCOMMENT ME
//			navx = new AHRS(Port.kOnboard);
		} catch (RuntimeException ex) {
			//DriverStation.reportError("Error instantiating navX-Micro:  " + ex.getMessage(), true);
		}
		
		// vision
		CameraServer.getInstance().startAutomaticCapture().setResolution(640,  480);
		
		// pdp
		powerDP = new PowerDistributionPanel(0);
		powerDP.clearStickyFaults();
				
		// elevator stage 1 motor 1
		elevatorMotor1 = new TalonSRX(elevatorMotor1ID);
		elevatorMotor1.configClosedloopRamp(.125, 0);		
		elevatorMotor1.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder,ElevatorStage1PIDID,0);
		elevatorMotor1.setSensorPhase(true);
		elevatorMotor1.setInverted(true);
		elevatorMotor1.setNeutralMode(NeutralMode.Brake);
		elevatorMotor1.configPeakCurrentLimit(40, 0);
		elevatorMotor1.configPeakCurrentDuration(250, 0);
		elevatorMotor1.enableCurrentLimit(true);

		// elevator stage 1 motor 2
		elevatorMotor2 = new VictorSPX(elevatorMotor2ID);
		elevatorMotor2.follow(elevatorMotor1);
		elevatorMotor2.setNeutralMode(NeutralMode.Brake);

		// elevator stage 2
		elevatorMotorStage2 = new TalonSRX(elevatorMotorStage2ID);
		elevatorMotorStage2.configClosedloopRamp(.125, 0);
		elevatorMotorStage2.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder,ElevatorStage2PIDID,0);
		elevatorMotorStage2.setSensorPhase(false);
		elevatorMotorStage2.setInverted(true);
		elevatorMotorStage2.setNeutralMode(NeutralMode.Brake);

		//drivetrain
		left1 = new TalonSRX(left1ID);
		left2 = new VictorSPX(left2ID);
		left3 = new VictorSPX(left3ID);
		right1 = new TalonSRX(right1ID);
		right2 = new VictorSPX(right2ID);
		right3 = new VictorSPX(right3ID);
		
		left1.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder,DrivePIDID,0);
		right1.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder,DrivePIDID,0);

		//TODO: THIS IS DIFFERENT FOR THE TWO ROBOTS
		//TODO: REMEMBER TO CHANGE THIS
		//TODO: THIS IS FALSE ON ACTUAL AND IS TRUE ON PRACTICE
		left1.setSensorPhase(true);

		left1.configClosedloopRamp(1,0);
		right1.configClosedloopRamp(1,0);



		left1.setInverted(true);
		left2.setInverted(true);
		left3.setInverted(true);
		
		left1.set(ControlMode.PercentOutput, 0.0);
		right1.set(ControlMode.PercentOutput, 0.0);	
		left2.set(ControlMode.PercentOutput, 0.0);
		right2.set(ControlMode.PercentOutput, 0.0);
		left3.set(ControlMode.PercentOutput, 0.0);
		right3.set(ControlMode.PercentOutput, 0.0);

		int drvieaccel=50;
		int drivespeed = 300;

		left1.configVoltageCompSaturation(10,0);
		left1.configVoltageMeasurementFilter(32,0);
		left1.configMotionAcceleration(drvieaccel,0);
		left1.configMotionCruiseVelocity(drivespeed,0);

		right1.configVoltageCompSaturation(10,0);
		right1.configVoltageMeasurementFilter(32,0);
		right1.configMotionAcceleration(drvieaccel,0);
		right1.configMotionCruiseVelocity(drivespeed,0);
		
		left2.follow(left1);
		left3.follow(left1);		
		right2.follow(right1);
		right3.follow(right1);
		
		left1.setNeutralMode(NeutralMode.Coast);
		right1.setNeutralMode(NeutralMode.Coast);
		left2.setNeutralMode(NeutralMode.Coast);
		right2.setNeutralMode(NeutralMode.Coast);
		left3.setNeutralMode(NeutralMode.Coast);
		right3.setNeutralMode(NeutralMode.Coast);
		
		left1.configClosedloopRamp(.125, 0);
		right1.configClosedloopRamp(.125, 0);
		left1.configOpenloopRamp(.125, 0);
		right1.configOpenloopRamp(.125, 0);

		//intake
		leftIntake = new VictorSPX(leftIntakeID);
		//leftIntake.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen, intakeElbowID, 0);
		leftIntake.setNeutralMode(NeutralMode.Brake);

		
		rightIntake = new VictorSPX(rightIntakeID);				
		rightIntake.follow(leftIntake);
		rightIntake.setNeutralMode(NeutralMode.Brake);
		rightIntake.setInverted(true);

		
		//intakeElbow
		intakeElbow = new TalonSRX(intakeElbowID);
//		intakeElbow.configContinuousCurrentLimit(6, 0);
//		intakeElbow.configPeakCurrentLimit(0, 0);
//		intakeElbow.enableCurrentLimit(true);
		intakeElbow.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,IntakeElbowPIDID,0);
		
		// pneumatics
		compressor = new Compressor(pcmID);
		compressor.setClosedLoopControl(true);
		gearShiftSol = new DoubleSolenoid(pcmID, 0, 1);
		intakeArmSol = new DoubleSolenoid(pcmID, 2, 3);
		
		// PID
//		right1.config_kP(RobotMap.DrivePIDID, 10, 0);
//		right1.config_kI(RobotMap.DrivePIDID, 1, 0);
//		right1.config_kD(RobotMap.DrivePIDID, 1, 0);
//
//		left1.config_kP(RobotMap.DrivePIDID, 10, 0);
//		left1.config_kI(RobotMap.DrivePIDID, 1, 0);
//		left1.config_kD(RobotMap.DrivePIDID, 1, 0);
		                                                                     
		elevatorMotor1.config_kP(RobotMap.ElevatorStage1PIDID, ElevatorStage1kP, 0);
		elevatorMotor1.config_kI(RobotMap.ElevatorStage1PIDID, ElevatorStage1kI, 0);
		elevatorMotor1.config_kD(RobotMap.ElevatorStage1PIDID, ElevatorStage1kD, 0);
		elevatorMotor1.configAllowableClosedloopError(0, 100, 0);
		                                                                     
		elevatorMotorStage2.config_kP(RobotMap.ElevatorStage2PIDID, ElevatorStage2kP, 0);
		elevatorMotorStage2.config_kI(RobotMap.ElevatorStage2PIDID, ElevatorStage2kI, 0);
		elevatorMotorStage2.config_kD(RobotMap.ElevatorStage2PIDID, ElevatorStage2kD, 0);
		elevatorMotorStage2.configAllowableClosedloopError(0, 200, 0);
	}
}
