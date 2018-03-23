
package org.usfirst.frc.team832.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import org.usfirst.frc.team832.robot.commands.auto.AutoDriveDistance;
import org.usfirst.frc.team832.robot.commands.auto.AutoDriveProfile;
import org.usfirst.frc.team832.robot.commands.auto.DynamicAutoCommand;
import org.usfirst.frc.team832.robot.commands.automodes.*;
import org.usfirst.frc.team832.robot.commands.defaults.RobotDrive;
import org.usfirst.frc.team832.robot.commands.defaults.RobotDriveSpeed;
import org.usfirst.frc.team832.robot.func.Calcs;
import org.usfirst.frc.team832.robot.subsystems.*;

import java.util.HashMap;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static WestCoastDrive westCoastDrive;
	public static Intake intake;
	public static IntakeElbow intakeElbow;
	public static ElevatorStage1 elevatorStage1;
	public static ElevatorStage2 elevatorStage2;
	public static Pneumatics pneumatics;
	public static GyroPID gyroPID;
	public static OI oi;

	private static RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode;

	private String fieldData;
	private static HashMap<String, String[]> autoFiles;

	private Command autoCmd;
	private SendableChooser<Command> autoChooser = new SendableChooser<>();
	private SendableChooser<Command> teleDriveChooser = new SendableChooser<>();
	private SendableChooser<String> robotSideChooser = new SendableChooser<>();
	private SendableChooser<String> autoOrderChooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		RobotMap.init();
		westCoastDrive = new WestCoastDrive();
		intake = new Intake();
		intakeElbow = new IntakeElbow();
		elevatorStage1 = new ElevatorStage1();
		elevatorStage2 = new ElevatorStage2();
		pneumatics = new Pneumatics();
		gyroPID = new GyroPID();
		oi = new OI();

		fieldData = DriverStation.getInstance().getGameSpecificMessage();

		autoChooser.addDefault("Base Line", new AUTOMODE_BaseLine());
		autoChooser.addObject("Do Nothing", new AUTOMODE_DoNothing());
		autoChooser.addObject("Scale from Left", new AUTOMODE_PlaceOnScaleFromLeft());
		autoChooser.addObject("Scale from Right", new AUTOMODE_PlaceOnScaleFromRight());
		autoChooser.addObject("Switch from Right", new AUTOMODE_PlaceOnSwitchFromRight());
		SmartDashboard.putData("Auto mode", autoChooser);

		teleDriveChooser.addDefault("Percent Output", new RobotDrive());
		teleDriveChooser.addDefault("Speed PID", new RobotDriveSpeed());

		robotSideChooser.addDefault("left", "L");
		robotSideChooser.addObject("center", "C");
		robotSideChooser.addObject("right", "R");
		SmartDashboard.putData("Robot Position", robotSideChooser);

		autoOrderChooser.addDefault("SwitchOnly", "sw");
		autoOrderChooser.addObject("ScaleOnly", "sc");
		autoOrderChooser.addObject("Switch-Scale", "swsc"); // only difference between Switch-Scale and Scale-Switch is the final heading after the profile runs
		autoOrderChooser.addObject("Scale-Switch", "scsw"); // SWSC will end the profile facing the Switch, then use normal commands to do Scale. Vice Versa for SCSW
	}

	private static void sendData() {
		SmartDashboard.putNumber("Stage1Pos",
				RobotMap.elevatorMotor1.getSelectedSensorPosition(RobotMap.ElevatorStage1PIDID));
		SmartDashboard.putNumber("Stage2Pos",
				RobotMap.elevatorMotor2.getSelectedSensorPosition(RobotMap.ElevatorStage2PIDID));
		SmartDashboard.putNumber("Stage1Target", ElevatorStage1.targetPosition);
		SmartDashboard.putNumber("Stage2Target", ElevatorStage2.targetPosition);
		SmartDashboard.putNumber("Right Motor Encoder", RobotMap.right1.getSelectedSensorPosition(RobotMap.DrivePIDID));
		SmartDashboard.putNumber("Left Motor Encoder", RobotMap.left1.getSelectedSensorPosition(RobotMap.DrivePIDID));
		SmartDashboard.putNumber("Intake Elbow Target", IntakeElbow.intakeElbowTargetPos);
		SmartDashboard.putNumber("Intake Elbow Pos", RobotMap.intakeElbow.getSelectedSensorPosition(RobotMap.IntakeElbowPIDID));

//		SmartDashboard.putNumber("Left Actual Speed", -RobotMap.left1.getSelectedSensorVelocity(RobotMap.DrivePIDID));
//		SmartDashboard.putNumber("Right Actual Speed",-1*RobotMap.right1.getSelectedSensorVelocity(RobotMap.DrivePIDID));
		SmartDashboard.putNumber("Left Error", -RobotMap.left1.getClosedLoopError(RobotMap.DrivePIDID));
		SmartDashboard.putNumber("Right Error",-1*RobotMap.right1.getClosedLoopError(RobotMap.DrivePIDID));
		SmartDashboard.putNumber("Left Target Speed",-1*RobotMap.left1.getClosedLoopTarget(RobotMap.DrivePIDID));
		SmartDashboard.putNumber("Right Target Speed", -1*RobotMap.right1.getClosedLoopTarget(RobotMap.DrivePIDID));

		// SmartDashboard.putNumber("IntakeStickY", intake.intakeStickY);
		// SmartDashboard.putNumber("IntakeElbowPot", IntakeElbow.rv);
		// SmartDashboard.putNumber("IntakeElbowPotMapped",
		// IntakeElbow.intakeElbowTargetPos);
		//SmartDashboard.putData("GyroPID", Robot.gyroPID.getPIDController());
		SmartDashboard.putNumber("GyroYaw", RobotMap.navx.getYaw());
		SmartDashboard.putNumber("GyroPitch", RobotMap.navx.getPitch());
		SmartDashboard.putNumber("GyroRoll", RobotMap.navx.getRoll());
	}

	private static void doRumble() {
		double minPitchAmount = 5, minRollAmount = 5;
		double maxPitchAmount = 25, maxRollAmount = 25;
		double pitchDelta, rollDelta;

		if (gyroPID.isPitchTipping(minPitchAmount)) {
			pitchDelta = gyroPID.pitchTipAmount(minPitchAmount);
			pitchDelta = Calcs.map(pitchDelta, 0, maxPitchAmount, 0.2, 1);
			pitchDelta = Calcs.clip(pitchDelta, 0, 1);
		} else
			pitchDelta = 0;

		if (gyroPID.isRollTipping(minRollAmount)) {
			rollDelta = gyroPID.rollTipAmount(minRollAmount);
			rollDelta = Calcs.map(rollDelta, 0, maxRollAmount, 0.2, 1);
			rollDelta = Calcs.clip(rollDelta, 0, 1);
		} else
			rollDelta = 0;

		if (rollDelta != 0) {
			OI.rumbleDriverPad(rollDelta, rollDelta);
		} else {
			OI.rumbleDriverPad(0, 0);
		}

		if (pitchDelta != 0) {
			OI.rumbleDriverPad(pitchDelta, pitchDelta);
		} else {
			OI.rumbleDriverPad(0, 0);
		}
	}

	private static void globalInit() {
		Robot.pneumatics.shiftToLow();
		Robot.pneumatics.closeIntake();
		Robot.westCoastDrive.resetEncoders();
		RobotMap.left1.setIntegralAccumulator(0.0, RobotMap.DrivePIDID, 0);
		RobotMap.right1.setIntegralAccumulator(0.0, RobotMap.DrivePIDID, 0);
		intakeElbow.setAtTop();
		RobotMap.navx.reset();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		setRobotMode(RobotMode.DISABLED);
		RobotMap.navx.reset();
		OI.rumbleDriverPad(0, 0);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		sendData();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		setRobotMode(RobotMode.AUTONOMOUS);
		globalInit();

		fieldData = DriverStation.getInstance().getGameSpecificMessage();
		autoCmd = autoChooser.getSelected();
		if (autoCmd != null && !(autoCmd instanceof AUTOMODE_BaseLine) && !(autoCmd instanceof AUTOMODE_DoNothing)) {
			autoCmd.start();
		} else {
			Command[] cmdList;

			String switchSide = String.valueOf(fieldData.charAt(0));
			String startSide = robotSideChooser.getSelected();
			if (autoCmd == null) {
				System.out.println("Defaulting to fully automatic auto");
				// generate appropriate command
				String[] autoFiles = Robot.autoFiles.get(startSide + switchSide);
				if (startSide.equals(switchSide)) {
					cmdList = new Command[]{
							new AutoDriveProfile(autoFiles[0], autoFiles[1]),
							// new RemoveCube()
					};
				} else if (startSide.equals("C")) {
					cmdList = new Command[]{
							new AutoDriveProfile(autoFiles[0], autoFiles[1]),
							//new RemoveCube()
					};
				} else {
					cmdList = new Command[]{
							new AutoDriveDistance(0.5, 0.0, 9000, 0) // cross base
					};
				}
				autoCmd = new DynamicAutoCommand(cmdList);
			} else if (autoCmd instanceof AUTOMODE_PlaceOnSwitch) {
				if (switchSide.equals(startSide)) {
					cmdList = new Command[]{
							// new AutoDriveDistance(power, delay, distance, angleIn);
							// new AutoIntakeLinear();
					};
					autoCmd = new DynamicAutoCommand(cmdList);
				} else {
					autoCmd = new AutoDriveDistance(0.5, 0.0, 3000, 0); // test me!
				}
			} else {
				if (!startSide.equals("C")) {
					if (switchSide.equals(startSide)) {
						switch (startSide) {
							case "R":
								cmdList = new Command[]{
										// GAVIN MAKE THESE DO THINGS
										//new DistanceDrive(10.0D + (55.5 / 12.0) - (33.0 / 12.0)),
										//new AngleDrive(-90.0),
										//new RemoveCube()
								};
								break;
							case "L":
								cmdList = new Command[]{
										// GAVIN MAKE THESE DO THINGS
//										new DistanceDrive(10.0D + (55.5 / 12.0) - (33.0 / 12.0)),
//										new AngleDrive(90.0),
//										new RemoveCube()
								};
								break;
							default:
								cmdList = new Command[]{
										new AutoDriveDistance(0.5, 0.0, 3000, 0) // test me!
								};
						}
						autoCmd = new DynamicAutoCommand(cmdList);
					}
				} else {
					autoCmd = new AutoDriveDistance(0.5, 0.0, 3000, 0); // test me!!!!!!!!!!!!!!!!!!!!!!
				}
			}
			autoCmd.start();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		sendData();
	}

	@Override
	public void teleopInit() {
		setRobotMode(RobotMode.TELEOP);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autoCmd != null)
			autoCmd.cancel();

		globalInit();
		RobotMap.intakeElbow.setSelectedSensorPosition(0, RobotMap.IntakeElbowPIDID, 0);

		if (!elevatorStage1.getAtBottom()) {
			RobotMap.elevatorMotor1.set(ControlMode.PercentOutput, -.08);
			while (true) {
				if (elevatorStage1.getAtBottom())
					break;
			}
		}
		elevatorStage1.stop();
		elevatorStage1.setAtBottom();

		// Auto homing stage 2 shouldn't ever be necessary
		/*
		if (!elevatorStage2.getAtBottom()) {
			RobotMap.elevatorMotor1.set(ControlMode.PercentOutput, -.01);
			while (true) {
				if (elevatorStage1.getAtBottom())
					break;
			}
		}
		*/
		elevatorStage2.stop();
		elevatorStage2.setAtBottom();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		sendData();
		doRumble();
		 if(RobotMap.intakeElbow.getSensorCollection().isRevLimitSwitchClosed()){
		 	intakeElbow.setAtBottom();
		 }
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}

	private static void setRobotMode(RobotMode mode) {
		previousRobotMode = currentRobotMode;
		currentRobotMode = mode;
	}
//TODO: MAKE THIS RIGHT!!!! We need paths generated for all possible modes
	private static void initAutoFiles() {
		autoFiles = new HashMap<>();
		// format: STARTSIDE, SWITCHSIDE, SCALESIDE

		// switch paths
		autoFiles.put("CL", new String[]{
				"/home/lvuser/paths_finder/center/center2left_left.csv",
				"/home/lvuser/paths_finder/center/center2left_right.csv"
		});
		autoFiles.put("CR", new String[]{
				"/home/lvuser/paths_finder/center/center2right_left.csv",
				"/home/lvuser/paths_finder/center/center2right_right.csv"
		});

		autoFiles.put("LL", new String[]{
				"/home/lvuser/paths_finder/left/left2left_left.csv",
				"/home/lvuser/paths_finder/left/left2left_right.csv"
		});
		autoFiles.put("LR", new String[]{
				"/home/lvuser/paths_finder/left/left2right_left.csv",
				"/home/lvuser/paths_finder/left/left2right_right.csv"
		});

		autoFiles.put("RL", new String[]{
				"/home/lvuser/paths_finder/right/right2left_left.csv",
				"/home/lvuser/paths_finder/right/right2left_right.csv"
		});
		autoFiles.put("RR", new String[]{
				"/home/lvuser/paths_finder/right/right2right_left.csv",
				"/home/lvuser/paths_finder/right/right2right_right.csv"
		});

		// scale paths
	}

	public static double[][] pathfinderFormatToTalon(Trajectory t) {
		int i = 0;
		double[][] list = new double[t.length()][3];
		for (Trajectory.Segment s : t.segments) {
			list[i][0] = s.position;
			list[i][1] = s.velocity;
			list[i][2] = s.dt;
			i++;
		}
		return list;
	}
}
