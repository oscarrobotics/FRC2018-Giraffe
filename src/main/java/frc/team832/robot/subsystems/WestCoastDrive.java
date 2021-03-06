package frc.team832.robot.subsystems;

import frc.team832.GrouchLib.Motors.CANTalon;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team832.robot.RobotMap;
import frc.team832.robot.commands.defaults.RobotDrive;

public class WestCoastDrive extends Subsystem {

    private final CANTalon left1 = RobotMap.left1;
    private final CANTalon right1 = RobotMap.right1;
    private final int highSpeedRatio = 2 * 1200;
    private final int lowSpeedRatio = 2 * 590;
    private final double highSpeedkF = 0.8;
    private final double lowSpeedkF = 1.7;
    private final MotionProfileStatus leftMpStatus = new MotionProfileStatus();
    private final MotionProfileStatus rightMpStatus = new MotionProfileStatus();

    private double pastp = 0;
    private double pastr = 0;
    private double filtp = 0.8;
    private double filtr = 0.0;

    public void ArcadeDrive(double pow, double rot, ControlMode ctrlMode) {
        double leftMotorSpeed, rightMotorSpeed;
        //low pass filter
        pastp = pastp * filtp + (1 - filtp) * pow;
        pastr = pastr * filtr + (1 - filtr) * rot;


        double moveValue = pastp; //pow
        double rotateValue = pastr; // rot

        rotateValue = Math.max(Math.sqrt(Math.abs(rotateValue * (Math.abs(moveValue)))) * 1.2, Math.abs(rotateValue)) * Math.signum(rotateValue);

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

    public void AutoRotateSpeed(double rotation) {
        ArcadeDriveSpeed(0, rotation);
    }

    public void ArcadeDriveSpeed(double power, double rot) {
        if (RobotMap.gearShiftSol.get() == Value.kReverse) {
            power = power * highSpeedRatio;
            rot = rot * highSpeedRatio;
            left1.setkF(highSpeedkF);
            right1.setkF(highSpeedkF);
        } else {
            power = power * lowSpeedRatio;
            rot = rot * lowSpeedRatio;
            left1.setkF(lowSpeedkF);
            right1.setkF(lowSpeedkF);
        }
        ArcadeDrive(power, rot, ControlMode.Velocity);
    }

    public void ArcadeDriveSpeedStraight(double speed) {
        ArcadeDrive(speed, 0, ControlMode.Velocity);
    }

    @Override
    public void periodic() {
        // TODO: implement OscarGeniusMotor class
//        this.left1.processMotionProfileBuffer();
//        this.right1.processMotionProfileBuffer();
//
//        this.left1.getMotionProfileStatus(this.leftMpStatus);
//        this.right1.getMotionProfileStatus(this.rightMpStatus);
        if (leftMpStatus.isUnderrun && leftMpStatus.btmBufferCnt != 0) {
            System.out.println("LEFT UNDERRUN");
        }
        if (rightMpStatus.isUnderrun && rightMpStatus.btmBufferCnt != 0) {
            System.out.println("RIGHT UNDERRUN");
        }
    }

    public void resetEncoders() {
        left1.setSensorPosition(0);
        right1.setSensorPosition(0);
    }

    public MotionProfileStatus getLeftMpStatus() {
        return leftMpStatus;
    }

    public MotionProfileStatus getRightMpStatus() {
        return rightMpStatus;
    }

    public void leftMpControl(SetValueMotionProfile v) {
        left1.set(ControlMode.MotionProfile, v.value);
    }

    public void rightMpControl(SetValueMotionProfile v) {
        right1.set(ControlMode.MotionProfile, v.value);
    }

    // TODO: implement OscarGeniusMotor class
//    public void startFillingLeft(double[][] profile, int size) {
//        this.left1.clearMotionProfileTrajectories();
//        TrajectoryPoint point = new TrajectoryPoint();
//
//        this.left1.changeMotionControlFramePeriod(25);
//        this.left1.configMotionProfileTrajectoryPeriod(25, 10);
//
//        for (int i = 0; i < size; i++) {
//            double positionRot = profile[i][0] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
//            double velocityRPM = profile[i][1] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
//            /* for each point, fill our structure and pass it to API */
//            point.position = -positionRot * (256.0 * 4.0); // Convert Revolutions to Units
//            point.velocity = velocityRPM * (256.0 * 4) / 10.0; // Convert RPS to Units/100ms
//            point.headingDeg = 0; /* future feature - not used in this example*/
//            point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
//            point.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
//            point.timeDur = GetTrajectoryDuration((int) profile[i][2]);
//            point.zeroPos = i == 0;
//            point.isLastPoint = false; // HACK: isLastPoint points seem to not play nice with MP
//
//            this.left1.pushMotionProfileTrajectory(point);
//        }
//        System.out.println("LP: " + point.isLastPoint);
//        System.out.println(String.format("Pushed %d points to left.", size));
//    }
//
//    public void startFillingRight(double[][] profile, int size) {
//        this.right1.clearMotionProfileTrajectories();
//        TrajectoryPoint point = new TrajectoryPoint();
//
//        this.right1.changeMotionControlFramePeriod(25);
//        this.right1.configMotionProfileTrajectoryPeriod(25, 10);
//
//        for (int i = 0; i < size; i++) {
//            double positionRot = profile[i][0] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
//            double velocityRPM = profile[i][1] * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
//            /* for each point, fill our structure and pass it to API */
//            point.position = -positionRot * (256.0 * 4.0); // Convert Revolutions to Units
//            point.velocity = velocityRPM * (256.0 * 4.0) / 10.0; // Convert RPS to Units/100ms
//            point.headingDeg = 0; /* future feature - not used in this example*/
//            point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
//            point.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
//            point.timeDur = GetTrajectoryDuration((int) profile[i][2]);
//            point.zeroPos = i == 0;
//            point.isLastPoint = false; // HACK: isLastPoint points seem to not play nice with MP
//
//            this.right1.pushMotionProfileTrajectory(point);
//        }
//        System.out.println("LP: " + point.isLastPoint);
//        System.out.println(String.format("Pushed %d points to right.", size));
//    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RobotDrive());
    }

    public double getMin() {
        return Math.min(Math.abs(RobotMap.left1.getSensorPosition()), Math.abs(RobotMap.right1.getSensorPosition()));
    }

    public double getMax() {
        return Math.max(RobotMap.left1.getSensorPosition(), RobotMap.right1.getSensorPosition());
    }

    public void stop() {
        left1.set(ControlMode.PercentOutput, 0);
        right1.set(ControlMode.PercentOutput, 0);
    }

}
