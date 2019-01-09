package org.usfirst.frc.team832.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import org.usfirst.frc.team832.robot.commands.auto.*;
import org.usfirst.frc.team832.robot.commands.auto.elbow.AutoMoveIntakeElbowPos;
import org.usfirst.frc.team832.robot.commands.automodes.AUTOMODE_BaseLine;
import org.usfirst.frc.team832.robot.commands.automodes.AUTOMODE_DoNothing;
import org.usfirst.frc.team832.robot.commands.automodes.AUTOMODE_PlaceOnSwitch;
import org.usfirst.frc.team832.robot.commands.defaults.*;
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

    public static RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode, doubpre;

    private String fieldData;
    private static HashMap<String, String> autoFiles;

    private Command autoCmd;
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();
    private final SendableChooser<Command> teleDriveChooser = new SendableChooser<>();
    private final SendableChooser<String> robotSideChooser = new SendableChooser<>();
    private final SendableChooser<String> autoOrderChooser = new SendableChooser<>();

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
        initAutoFiles();
        autoChooser.addObject("Base Line", new AUTOMODE_BaseLine());
        autoChooser.addObject("Do Nothing", new AUTOMODE_DoNothing());
        autoChooser.addDefault("SuperMegaAuto", null);
        SmartDashboard.putData("Auto mode", autoChooser);

//		teleDriveChooser.addDefault("Percent Output", new RobotDrive());
//		teleDriveChooser.addDefault("Speed PID", new RobotDriveSpeed());

        robotSideChooser.addObject("left", "L");
        robotSideChooser.addDefault("center", "C");
        robotSideChooser.addObject("right", "R");
        SmartDashboard.putData("Robot Position", robotSideChooser);

        autoOrderChooser.addDefault("SwitchOnly", "sw");
        autoOrderChooser.addObject("ScaleOnly", "sc");
        autoOrderChooser.addObject("Switch-Scale", "swsc"); // only difference between Switch-Scale and Scale-Switch is the final heading after the profile runs
        autoOrderChooser.addObject("Scale-Switch", "scsw"); // SWSC will end the profile facing the Switch, then use normal commands to do Scale. Vice Versa for SCSW
        SmartDashboard.putData(" Auto Priority", autoOrderChooser);
    }

    private static void sendData(boolean isDisabled) {
        SmartDashboard.putNumber("Stage1Pos", RobotMap.elevatorMotor1.getSensorPosition());
        SmartDashboard.putNumber("Stage2Pos", RobotMap.elevatorMotor2.getSensorPosition());
        SmartDashboard.putNumber("Stage1Target", ElevatorStage1.targetPosition);
        SmartDashboard.putNumber("Stage2Target", ElevatorStage2.targetPosition);
        SmartDashboard.putNumber("Right Motor Encoder", RobotMap.right1.getSensorPosition());
        SmartDashboard.putNumber("Left Motor Encoder", RobotMap.left1.getSensorPosition());
        SmartDashboard.putNumber("Intake Elbow Target", IntakeElbow.intakeElbowTargetPos);
        SmartDashboard.putNumber("Intake Elbow Pos", RobotMap.intakeElbow.getSensorPosition());

        if (currentRobotMode.equals(RobotMode.TELEOP)) {
            SmartDashboard.putNumber("Left Error", -RobotMap.left1.getClosedLoopError());
            SmartDashboard.putNumber("Right Error", -1 * RobotMap.right1.getClosedLoopError());
            SmartDashboard.putNumber("Left Target Speed", -1 * RobotMap.left1.getClosedLoopTarget());
            SmartDashboard.putNumber("Right Target Speed", -1 * RobotMap.right1.getClosedLoopTarget());
        }
        SmartDashboard.putData("GyroPID", Robot.gyroPID.getPIDController());
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
        //RobotMap.navx.reset();
        Robot.pneumatics.shiftToLow();
        Robot.pneumatics.closeIntake();
        Robot.westCoastDrive.resetEncoders();
//        RobotMap.left1.setIntegralAccumulator(0.0);
//        RobotMap.right1.setIntegralAccumulator(0.0);
//        RobotMap.intakeElbow.setIntegralAccumulator(0.0);
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
        Robot.westCoastDrive.resetEncoders();
        OI.rumbleDriverPad(0, 0);
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        sendData(true);
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        setRobotMode(RobotMode.AUTONOMOUS);
        globalInit();
        elevatorStage2.setAtBottom();

        if (!Robot.elevatorStage1.getAtBottom()) {
            RobotMap.elevatorMotor1.set(ControlMode.PercentOutput, -.08);
            while (true) {
                if (Robot.elevatorStage1.getAtBottom()) {
                    break;
                }
            }
        }

        Robot.elevatorStage1.setAtBottom();

        RobotMap.intakeElbow.setSensorPosition(2220);

        fieldData = DriverStation.getInstance().getGameSpecificMessage();
        autoCmd = autoChooser.getSelected();
        if (autoCmd != null && ((autoCmd instanceof AUTOMODE_BaseLine) || (autoCmd instanceof AUTOMODE_DoNothing))) {
            autoCmd.start();
        } else {
            Command[] cmdList;

            String switchSide = String.valueOf(fieldData.charAt(0));
            String scaleSide = String.valueOf(fieldData.charAt(1));
            String startSide = robotSideChooser.getSelected();

            if (autoCmd == null) {
                System.out.println("Defaulting to fully automatic auto");
                // generate appropriate command
                String autoFiles;
                if (autoOrderChooser.getSelected().equals("sw")) { // switch only
                    System.out.print(String.format("Running %s switch auto ", switchSide));
                    autoFiles = Robot.autoFiles.get(startSide + switchSide);
                    if (startSide.equals(switchSide)) {
                        System.out.println(String.format("from %s position: ", startSide) + autoFiles);
                        cmdList = new Command[]{
                                new AutoMoveIntakeElbowPos(2100),
                                new AutoDriveProfile(autoFiles),
                                new AutoMoveElevatorStage2(0.5),
                                new AutoMoveIntakeElbowPos(0),
                                new AutoIntakeLinear(-.4, 500)
                        };
                    } else if (startSide.equals("C")) {
                        System.out.println("from C position: " + autoFiles);
                        cmdList = new Command[]{
                                new AutoMoveIntakeElbowPos(2100),
                                new AutoDriveProfile(autoFiles),
//								new MoveOnPath(autoFiles),
                                new AutoMoveElevatorStage2(0.5),
                                new AutoMoveIntakeElbowPos(0),
                                new AutoIntakeLinear(-.4, 500),
                                new AutoMoveIntakeElbowPos(2200)
                        };
                    } else {
                        cmdList = new Command[]{
                                //new AutoDriveDistance(0.5, 0.0, 9000, 0) // cross base
                        };
                    }
                    autoCmd = new DynamicAutoCommand(cmdList);
                } else if (autoOrderChooser.getSelected().equals("sc")) {
                    autoFiles = Robot.autoFiles.get(startSide + switchSide + scaleSide);
                    System.out.println(String.format("AutoData: %s, %s, %s", startSide, switchSide, scaleSide));
                    if (startSide.equals(scaleSide)) {
                        System.out.println(String.format("Running %s Scale auto from %s position", scaleSide, startSide));
                        cmdList = new Command[]{
                                new AutoMoveIntakeElbowPos(2100),
//								new MoveOnPath(autoFiles),
                                new AutoDriveProfile(autoFiles),
                                new AutoMoveFullElevator(1.0),
                                new AutoMoveIntakeElbowPos(1000),
                                new AutoIntakeLinear(-.5, 1000),
                                new AutoMoveIntakeElbowPos(2200),
                                new AutoMoveFullElevator(-1)
                        };
                        autoCmd = new DynamicAutoCommand(cmdList);
                    } else {
                        System.out.println(String.format("Running %s far Scale auto from %s position", scaleSide, startSide));
                        cmdList = new Command[]{
                                /*

                                        new AutoMoveIntakeElbowPos(2100),
                                    new AutoDriveProfile(autoFiles[0], autoFiles[1]),
                                    new AutoMoveElevatorStage2(1),
                                    new AutoMoveElevatorStage1(1),
                                    new MoveElbowToBottom(),
                                    new AutoIntakeLinear(-.5, 1000),
                                */
                                new AutoDriveDistance(0.5, 0.0, 7000, 0)

                        };
                        autoCmd = new DynamicAutoCommand(cmdList);
                    }
                } else if (autoOrderChooser.getSelected().equals("scsw")) {
                    autoFiles = Robot.autoFiles.get(startSide + switchSide + scaleSide);
                    System.out.println(String.format("AutoData: %s, %s, %s", startSide, switchSide, scaleSide));
                    if (startSide.equals(scaleSide)) {
                        System.out.println(String.format("Running %s Scale auto from %s position, path: %s", scaleSide, startSide, autoFiles));
                        cmdList = new Command[]{
                                //Normal Switch
                                new AutoMoveIntakeElbowPos(2100),
                                new AutoDriveProfile(autoFiles),
                                new AutoMoveFullElevator(1.0),
                                new AutoMoveIntakeElbowPos(0),
                                new AutoIntakeLinear(-.5, 1000),
                                new AutoMoveIntakeElbowPos(2200),
                                new AutoMoveFullElevator(-1)


                        };
                        autoCmd = new DynamicAutoCommand(cmdList);
                    } else {
                        System.out.println(String.format("Running %s Scale auto from %s position", scaleSide, startSide));
                        cmdList = new Command[]{
                                /*
                                    new AutoMoveIntakeElbowPos(2100),
                                    new AutoDriveProfile(autoFiles[0], autoFiles[1]),
                                    new AutoMoveElevatorStage2(1),
                                    new AutoMoveElevatorStage1(1),
                                    new MoveElbowToBottom(),
                                    new AutoIntakeLinear(-.5, 1000),
                                */
                                new AutoDriveDistance(0.5, 0.0, 9000, 0)


                        };
                        autoCmd = new DynamicAutoCommand(cmdList);
                    }
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
                        autoCmd = new AutoDriveDistance(0.5, 0.0, 6000, 0); // test me!!!!!!!!!!!!!!!!!!!!!!
                    }
                }
                autoCmd.start();
            }
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        sendData(false);
    }

    @Override
    public void teleopInit() {
        setRobotMode(RobotMode.TELEOP);
        if (autoCmd != null)
            autoCmd.cancel();

        System.out.println("Begin Scheduler");

        Scheduler.getInstance().add(new RobotDrive());
        Scheduler.getInstance().add(new RunIntake());
        Scheduler.getInstance().add(new RunIntakeElbow());
        Scheduler.getInstance().add(new RunElevatorStage1());
        Scheduler.getInstance().add(new RunElevatorStage2());

        System.out.println("Finished Scheduler");

        elevatorStage2.stop();

//		if(doubpre.equals(RobotMode.AUTONOMOUS));
//		else {
//			elevatorStage2.setAtBottom();
//			intakeElbow.setAtBottom();
//		}
        System.out.println("Stage 2 INITed");

        //RobotMap.intakeElbow.setSensorPosition(0, RobotMap.IntakeElbowPIDID, 0);

        elevatorStage2.setPos(-.8);

//		if (!intakeElbow.getAtBottom()) {
//			RobotMap.intakeElbow.set(ControlMode.PercentOutput, -.1);
//			while (true) {
//				if (intakeElbow.getAtBottom())
//					break;
//				else if(OI.driverPad.getStartButton());
//					RobotMap.intakeElbow.setSensorPosition(2200, 0, 0);
//					break;
//			}
//		}
//		intakeElbow.stop();
//		intakeElbow.setAtBottom();
//		System.out.println("Elbow Finished");

        //RobotMap.intakeElbow.setSensorPosition(2300, 0, 0);

        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        globalInit();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        System.out.println("Begin Tele");
        Scheduler.getInstance().run();
        //sendData(false);
        doRumble();
//		 if(RobotMap.intakeElbow.getSensorCollection().isRevLimitSwitchClosed()){
//		 	intakeElbow.setAtBottom();
//		 }
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }

    private static void setRobotMode(RobotMode mode) {
        doubpre = previousRobotMode;
        previousRobotMode = currentRobotMode;
        currentRobotMode = mode;
    }

    //TODO: MAKE THIS RIGHT!!!! We need paths generated for all possible modes
    private static void initAutoFiles() {
        autoFiles = new HashMap<>();
        // format: STARTSIDE, SWITCHSIDE, SCALESIDE

        autoFiles.put("CL", "CenterToLeft");

        autoFiles.put("CR", "CenterToRight");

        autoFiles.put("LL", "LeftSwitch");

        autoFiles.put("RR", "RightSwitchRight");

        // scale paths
        autoFiles.put("LLL", "LeftToLeftScale");

        autoFiles.put("LRL", "LeftToLeftScale");

        autoFiles.put("RRR", "RightToRightScale");

        autoFiles.put("RLR", "RightToRightScale");

        autoFiles.put("LLR", "LeftToRightScale");

        autoFiles.put("LRR", "LeftToRightScale");

        autoFiles.put("RRL", "RightToLeftScale");

        autoFiles.put("RLL", "RightToLeftScale");
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
