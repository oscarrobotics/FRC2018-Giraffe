package frc.team832.robot;

import OscarLib.Motion.OscarDiffDrive;
import OscarLib.Motors.OscarCANTalon;
import OscarLib.Motors.OscarCANVictor;
import OscarLib.Motors.OscarSmartMotorGroup;
import OscarLib.Sensors.OscarNavX;
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

    // electronics
    public static final int pdpID = 0;
    public static final int DrivePIDID = 0;
    public static final int ElevatorStage1PIDID = 0;
    public static final double ElevatorStage1kP = 0.5;
    public static final double ElevatorStage1kI = 0.0;
    public static final double ElevatorStage1kD = 0.0;
    public static final double ElevatorStage1kF = 0.0;
    public static final int ElevatorStage2PIDID = 0;
    public static final double ElevatorStage2kP = 0.9;
    public static final double ElevatorStage2kI = 0.0;
    public static final double ElevatorStage2kD = 7.0;
    public static final double ElevatorStage2kF = 0.0;
    public static final int IntakeElbowPIDID = 0;
    public static final double IntakeElbowkP = .6;
    public static final double IntakeElbowkI = .0;
    public static final double IntakeElbowkD = .0;
    public static final double IntakeElbowkF = .0;
    //elevator
    private static final int elevatorMotor1ID = 8;
    private static final int elevatorMotor2ID = 9;
    private static final int elevatorMotorStage2ID = 10;
    //drivetrain
    private static final int left1ID = 2;
    private static final int left2ID = 3;
    private static final int left3ID = 4;
    private static final int right1ID = 5;
    private static final int right2ID = 6;
    private static final int right3ID = 7;
    //intake
    private static final int leftIntakeID = 12;
    private static final int rightIntakeID = 13;
    //intakeElbow
    private static final int intakeElbowID = 11;
    // pneumatics
    private static final int pcmID = 1;
    //navx
    public static OscarNavX navx;
    public static OscarCANTalon elevatorMotor1;
    public static OscarCANVictor elevatorMotor2;

    public static OscarCANTalon elevatorMotorStage2;

    public static OscarCANTalon left1;
    public static OscarCANVictor left2, left3;

    public static OscarCANTalon right1;
    public static OscarCANVictor right2, right3;
    public static OscarSmartMotorGroup leftGroup, rightGroup;
    public static OscarDiffDrive diffDrive;

    public static OscarCANVictor leftIntake, rightIntake;
    public static OscarCANTalon intakeElbow;
    public static DoubleSolenoid gearShiftSol, intakeArmSol;
    private static Compressor compressor;
    private static PowerDistributionPanel powerDP;

    public static void init() {

        navx = new OscarNavX(Port.kOnboard);
        navx.init();

        left1 = new OscarCANTalon(left1ID);
        left2 = new OscarCANVictor(left2ID);
        left3 = new OscarCANVictor(left3ID);

//        left2.follow(left1);
//        left3.follow(left1);

        right1 = new OscarCANTalon(right1ID);
        right2 = new OscarCANVictor(right2ID);
        right3 = new OscarCANVictor(right3ID);

//        right2.follow(right1);
//        right3.follow(right1);

        leftGroup = new OscarSmartMotorGroup(left1, left2, left3);
        rightGroup = new OscarSmartMotorGroup(right1, right2, right3);

        diffDrive = new OscarDiffDrive(leftGroup, rightGroup);

//        leftIntake = new OscarCANVictor(leftIntakeID);
//        rightIntake = new OscarCANVictor(rightIntakeID);
//
//        elevatorMotor1 = new OscarCANTalon(elevatorMotor1ID);
//        elevatorMotor2 = new OscarCANVictor(elevatorMotor2ID);

        // vision
//        CameraServer.getInstance().startAutomaticCapture().setResolution(640, 480);

        // pdp
//        powerDP = new PowerDistributionPanel(0);
//        powerDP.clearStickyFaults();
//
//        // elevator stage 1 motor 1

//        elevatorMotor1.setClosedLoopRamp(.125);
//        elevatorMotor1.setSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
//        elevatorMotor1.setSensorPhase(true);
//        elevatorMotor1.setInverted(true);
//        elevatorMotor1.setNeutralMode(NeutralMode.Brake);
////        elevatorMotor1.configPeakCurrentLimit(40, 0);
////        elevatorMotor1.configPeakCurrentDuration(250, 0);
////        elevatorMotor1.enableCurrentLimit(true);
//
//        // elevator stage 1 motor 2
//        //elevatorMotor2.follow(elevatorMotor1);
//        elevatorMotor2.setNeutralMode(NeutralMode.Brake);
//
//        // elevator stage 2
//        elevatorMotorStage2.setClosedLoopRamp(.125);
//        elevatorMotorStage2.setSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
//        elevatorMotorStage2.setSensorPhase(false);
//        elevatorMotorStage2.setInverted(true);
//        elevatorMotorStage2.setNeutralMode(NeutralMode.Brake);
//
//        //drivetrain


//        left1.setSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
//        right1.setSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
//
//        //TODO: THIS IS DIFFERENT FOR THE TWO ROBOTS
//        //TODO: REMEMBER TO CHANGE THIS
//        //TODO: THIS IS FALSE ON ACTUAL AND IS TRUE ON PRACTICE
//        left1.setSensorPhase(false);
//
//        left1.setClosedLoopRamp(1);
//        right1.setClosedLoopRamp(1);
//
//
//        left1.setInverted(true);
//        left2.setInverted(true);
//        left3.setInverted(true);
//
//        left1.set(ControlMode.PercentOutput, 0.0);
//        right1.set(ControlMode.PercentOutput, 0.0);
//        left2.set(ControlMode.PercentOutput, 0.0);
//        right2.set(ControlMode.PercentOutput, 0.0);
//        left3.set(ControlMode.PercentOutput, 0.0);
//        right3.set(ControlMode.PercentOutput, 0.0);

//        int drvieaccel = 50;
//        int drivespeed = 300;
//
////        left1.configVoltageCompSaturation(10, 0);
////        left1.configVoltageMeasurementFilter(32, 0);
////        left1.configMotionAcceleration(drvieaccel, 0);
////        left1.configMotionCruiseVelocity(drivespeed, 0);
//
////        right1.configVoltageCompSaturation(10, 0);
////        right1.configVoltageMeasurementFilter(32, 0);
////        right1.configMotionAcceleration(drvieaccel, 0);
////        right1.configMotionCruiseVelocity(drivespeed, 0);
//
//        left2.follow(left1);
//        left3.follow(left1);
//        right2.follow(right1);
//        right3.follow(right1);
//
//        left1.setNeutralMode(NeutralMode.Coast);
//        right1.setNeutralMode(NeutralMode.Coast);
//        left2.setNeutralMode(NeutralMode.Coast);
//        right2.setNeutralMode(NeutralMode.Coast);
//        left3.setNeutralMode(NeutralMode.Coast);
//        right3.setNeutralMode(NeutralMode.Coast);
//
//        left1.setClosedLoopRamp(.125);
//        right1.setClosedLoopRamp(.125);
//        left1.setOpenLoopRamp(.125);
//        right1.setOpenLoopRamp(.125);
//
        //intake
//        //leftIntake.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteOscarCANTalon, LimitSwitchNormal.NormallyOpen, intakeElbowID, 0);
//        leftIntake.setNeutralMode(NeutralMode.Coast);
//
//

//        rightIntake.follow(leftIntake);
//        rightIntake.setNeutralMode(NeutralMode.Coast);
//        rightIntake.setInverted(true);
//
//
//        //intakeElbow
//        intakeElbow = new OscarCANTalon(intakeElbowID);
////		intakeElbow.configContinuousCurrentLimit(6, 0);
////		intakeElbow.configPeakCurrentLimit(0, 0);
////		intakeElbow.enableCurrentLimit(true);
//        intakeElbow.setSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
//
//        // pneumatics
//        compressor = new Compressor(pcmID);
//        compressor.setClosedLoopControl(true);
//        gearShiftSol = new DoubleSolenoid(pcmID, 0, 1);
//        intakeArmSol = new DoubleSolenoid(pcmID, 2, 3);
//
//        // PID
////		right1.config_kP(RobotMap.DrivePIDID, 10, 0);
////		right1.config_kI(RobotMap.DrivePIDID, 1, 0);
////		right1.config_kD(RobotMap.DrivePIDID, 1, 0);
////
////		left1.config_kP(RobotMap.DrivePIDID, 10, 0);
////		left1.config_kI(RobotMap.DrivePIDID, 1, 0);
////		left1.config_kD(RobotMap.DrivePIDID, 1, 0);
//
////		elevatorMotor1.config_kP(RobotMap.ElevatorStage1PIDID, ElevatorStage1kP, 0);
////		elevatorMotor1.config_kI(RobotMap.ElevatorStage1PIDID, ElevatorStage1kI, 0);
////		elevatorMotor1.config_kD(RobotMap.ElevatorStage1PIDID, ElevatorStage1kD, 0);
//        elevatorMotor1.setAllowableClosedLoopError(0);
////
////		elevatorMotorStage2.config_kP(RobotMap.ElevatorStage2PIDID, ElevatorStage2kP, 0);
////		elevatorMotorStage2.config_kI(RobotMap.ElevatorStage2PIDID, ElevatorStage2kI, 0);
////		elevatorMotorStage2.config_kD(RobotMap.ElevatorStage2PIDID, ElevatorStage2kD, 0);
//        elevatorMotorStage2.setAllowableClosedLoopError(0);
    }
}
