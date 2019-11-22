package frc.team832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team832.robot.RobotMap;

public class IntakeElbow extends Subsystem {

    private static final int maxEncPos = 2200;
    private static final int acceptableError = 90;
    private static final double minPotVal = 0;
    private static final double maxPotVal = 0.65;
    public static double intakeElbowTargetPos;
    public static double rv;
    //public static double potMap(double rotationVal) { return Math.round( map(rotationVal, minPotVal, maxPotVal, 0.0, 1.0) * maxEncPos); }

    public void stop() {
        RobotMap.intakeElbow.set(ControlMode.PercentOutput, 0);
    }

    public void start() {
        RobotMap.intakeElbow.set(ControlMode.Position, 1600);
    }

    public boolean getAtBottom() {
        return RobotMap.intakeElbow.getReverseLimitSwitch();
    } //TODO: Was forward, set to reverse for practice bot

    public void setAtBottom() {
        RobotMap.intakeElbow.setSensorPosition(0);
    }

    public void setAtTop() {
        RobotMap.intakeElbow.setSensorPosition(2220);
    }

    public void setPos(double rotationVal) {
        if (rotationVal >= .95) {
            RobotMap.intakeElbow.set(ControlMode.PercentOutput, -.1);
            if (RobotMap.intakeElbow.getSensorPosition() < 0)
                this.setAtBottom();
        } else if (rotationVal <= -.9) {
            RobotMap.intakeElbow.set(ControlMode.PercentOutput, .3);
            if (RobotMap.intakeElbow.getSensorPosition() < 0)
                this.setAtTop();
        } else {

            //System.out.println("Potentiometer in: " + rotationVal);
            if (rotationVal >= maxPotVal)
                rotationVal = maxPotVal;
            if (rotationVal <= minPotVal)
                rotationVal = minPotVal;
            rv = rotationVal;
            //System.out.println("Potentiometer out: " +rv);
            //intakeElbowTargetPos = maxEncPos - maxEncPos * rotationVal * (1 / maxPotVal);
            intakeElbowTargetPos = 2300 - (2300 * (rv / .65));
            //System.out.println("Intake Target Pos: " + intakeElbowTargetPos);
            //intakeElbowTargetPos = ((2250 - (2250 * rv/.65)) +350);
            RobotMap.intakeElbow.set(ControlMode.Position, intakeElbowTargetPos);
            //System.out.println("Intake Elbow Error: " + RobotMap.intakeElbow.getClosedLoopError(0));
            //System.out.println("Intake Elbow Enc Pos: " + RobotMap.intakeElbow.getSelectedSensorPosition(0));

        }
    }

    public void setAutoPos(int targetPos) {
        intakeElbowTargetPos = targetPos;
        RobotMap.intakeElbow.set(ControlMode.Position, intakeElbowTargetPos);

    }

    public boolean getIsFinished() {
        int currentError = (int) (RobotMap.intakeElbow.getSensorPosition() - RobotMap.intakeElbow.getClosedLoopTarget());
        return (Math.abs(currentError) <= acceptableError);
        //return false; Testing only
    }

    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new RunIntakeElbow());
    }
}
