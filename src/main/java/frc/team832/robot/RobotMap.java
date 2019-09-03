package frc.team832.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team832.GrouchLib.control.*;
import frc.team832.GrouchLib.mechanisms.LinearMechanism;
import frc.team832.GrouchLib.mechanisms.Positions.MechanismPosition;
import frc.team832.GrouchLib.motion.*;
import frc.team832.GrouchLib.motorcontrol.*;
import frc.team832.GrouchLib.sensors.NavXMicro;
import frc.team832.GrouchLib.sensors.NavXMicro.NavXPort;

import java.util.HashMap;
import java.util.Map;

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
    public static NavXMicro navx;

    public static CANTalon elevatorMotor1;
    public static CANVictor elevatorMotor2;
    public static SmartCANMC elevator1Group;
    public static LinearMechanism mainElevator;

    public static CANTalon elevatorMotorStage2;

    public static CANTalon left1;
    public static CANVictor left2, left3;

    public static CANTalon right1;
    public static CANVictor right2, right3;
    public static SmartCANMCGroup leftGroup, rightGroup;
    public static SmartDifferentialDrive diffDrive;

    public static CANVictor leftIntake, rightIntake;
    public static CANTalon intakeElbow;
    public static DoubleSolenoid gearShiftSol, intakeArmSol;
    private static PCM pcm;
    private static PDP pdp;

    public enum Elevator1Position {
        BOTTOM(0, 0),
        SWITCH(1, 400),
        LOW_SCALE(2, 1500),
        HIGH_SCALE( 3, 1800);

        private final int index;
        private final int value;
        private Elevator1Position(int index, int value) {
            this.value = value;
            this.index = index;
        }
    }

    public static void init() {

        navx = new NavXMicro(NavXPort.I2C_onboard);
        navx.init();

        left1 = new CANTalon(left1ID);
        left2 = new CANVictor(left2ID);
        left3 = new CANVictor(left3ID);

        right1 = new CANTalon(right1ID);
        right2 = new CANVictor(right2ID);
        right3 = new CANVictor(right3ID);

        leftGroup = new SmartCANMCGroup(left1, left2, left3);
        rightGroup = new SmartCANMCGroup(right1, right2, right3);

        diffDrive = new SmartDifferentialDrive(leftGroup, rightGroup, 5840);

        pdp = new PDP(pdpID);

        pcm = new PCM(pcmID);
        pcm.setEnabled(true);

        gearShiftSol = new DoubleSolenoid(pcm, 0, 1);
        intakeArmSol = new DoubleSolenoid(pcm, 2, 3);

        elevatorMotor1 = new CANTalon(elevatorMotor1ID);
        elevatorMotor1.setClosedLoopRamp(.125);
        elevatorMotor1.setSensorType(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
        elevatorMotor1.setSensorPhase(true);
        elevatorMotor1.setInverted(true);

        elevatorMotor2 = new CANVictor(elevatorMotor2ID);

        elevator1Group = new SmartCANMCGroup(elevatorMotor1, elevatorMotor2);
        elevator1Group.setNeutralMode(NeutralMode.kBrake);

        mainElevator = new LinearMechanism(elevator1Group, Constants.Elevator1PositionList);

        leftIntake = new CANVictor(leftIntakeID);
        rightIntake = new CANVictor(rightIntakeID);

        // elevator stage 2
        elevatorMotorStage2 = new CANTalon(elevatorMotorStage2ID);
        elevatorMotorStage2.setClosedLoopRamp(.125);
        elevatorMotorStage2.setSensorType(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
        elevatorMotorStage2.setSensorPhase(false);
        elevatorMotorStage2.setInverted(true);
        elevatorMotorStage2.setNeutralMode(NeutralMode.kBrake);

        //drivetrain
        left1.setSensorType(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
        right1.setSensorType(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
//
//        //TODO: THIS IS DIFFERENT FOR THE TWO ROBOTS
//        //TODO: REMEMBER TO CHANGE THIS
//        //TODO: THIS IS FALSE ON ACTUAL AND IS TRUE ON PRACTICE
//        left1.setSensorPhase(false);
//
//        int drvieaccel = 50;
//        int drivespeed = 300;
//
//        left1.configVoltageCompSaturation(10, 0);
//        left1.configVoltageMeasurementFilter(32, 0);
//        left1.configMotionAcceleration(drvieaccel, 0);
//        left1.configMotionCruiseVelocity(drivespeed, 0);
//
//        right1.configVoltageCompSaturation(10, 0);
//        right1.configVoltageMeasurementFilter(32, 0);
//        right1.configMotionAcceleration(drvieaccel, 0);
//        right1.configMotionCruiseVelocity(drivespeed, 0);
//
//
        left1.setClosedLoopRamp(.125);
        right1.setClosedLoopRamp(.125);
        left1.setOpenLoopRamp(.125);
        right1.setOpenLoopRamp(.125);

//        leftIntake.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteCANTalon, LimitSwitchNormal.NormallyOpen, intakeElbowID, 0);
        leftIntake.setNeutralMode(NeutralMode.kCoast);
        rightIntake.setNeutralMode(NeutralMode.kCoast);

        //intakeElbow
        intakeElbow = new CANTalon(intakeElbowID);
//		  intakeElbow.configContinuousCurrentLimit(6, 0);
//		  intakeElbow.configPeakCurrentLimit(0, 0);
//		  intakeElbow.enableCurrentLimit(true);
        intakeElbow.setSensorType(FeedbackDevice.CTRE_MagEncoder_Relative);
        intakeElbow.setSensorPhase(true);
        intakeElbow.setInverted(true);

        // PID
//        right1.setkP(10, RobotMap.DrivePIDID);
//		right1.setkI(1, RobotMap.DrivePIDID);
//		right1.setkD(1, RobotMap.DrivePIDID);
//
//	    left1.setkP(RobotMap.DrivePIDID, 10, 0);
//		left1.setkI(RobotMap.DrivePIDID, 1, 0);
//		left1.setkD(RobotMap.DrivePIDID, 1, 0);

		elevatorMotor1.setkP(ElevatorStage1kP, RobotMap.ElevatorStage1PIDID);
		elevatorMotor1.setkI(ElevatorStage1kI, RobotMap.ElevatorStage1PIDID);
		elevatorMotor1.setkD(ElevatorStage1kD, RobotMap.ElevatorStage1PIDID);
        elevatorMotor1.setAllowableClosedLoopError(0);

		elevatorMotorStage2.setkP(ElevatorStage2kP , RobotMap.ElevatorStage2PIDID);
		elevatorMotorStage2.setkI(ElevatorStage2kI , RobotMap.ElevatorStage2PIDID);
		elevatorMotorStage2.setkD(ElevatorStage2kD , RobotMap.ElevatorStage2PIDID);
        elevatorMotorStage2.setAllowableClosedLoopError(0);
    }
}
