package org.usfirst.frc.team832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team832.robot.RobotMap;
import org.usfirst.frc.team832.robot.func.Calcs;

public class ElevatorStage2 extends Subsystem {

    private static double minEncPos = 1900;
    private static final double maxEncPos = 26000 - minEncPos;
    private static final double lowerPosThreshold = 5000;
    private static final int acceptableError = 425;
    public static double targetPosition;

    public boolean getAtBottom() {
        return !RobotMap.elevatorMotorStage2.getReverseLimitSwitch();
    }

    public void setAtBottom() {
        RobotMap.elevatorMotorStage2.setSensorPosition(0);
    }

    public void start() {
        RobotMap.elevatorMotorStage2.set(ControlMode.Position, 0);
    }

    public void stop() {
        RobotMap.elevatorMotorStage2.set(ControlMode.PercentOutput, 0);
    }

    public void setPos(double sliderVal) {
        if (RobotMap.elevatorMotor1.getSensorPosition() >= 1900)
            minEncPos = 0;
        else
            minEncPos = 1900;

        if (RobotMap.elevatorMotorStage2.getSensorPosition() > lowerPosThreshold) {
            RobotMap.elevatorMotorStage2.setPeakOutputReverse(-100);
        }
        if (RobotMap.elevatorMotorStage2.getSensorPosition() < lowerPosThreshold) {
            RobotMap.elevatorMotorStage2.setPeakOutputReverse(-0.1);
        }


        targetPosition = Math.round(minEncPos + (Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos));
        RobotMap.elevatorMotorStage2.set(ControlMode.Position, targetPosition);
    }

    public void setAutoPos(double targetPos) {
        setPos(targetPos);
    }

    public boolean isFinished() {
        double clError = RobotMap.elevatorMotorStage2.getSensorPosition() - RobotMap.elevatorMotorStage2.getClosedLoopTarget();
        System.out.println("Stage 2 error: " + clError);
        return (Math.abs(clError) <= acceptableError);
    }

    @Override
    protected void initDefaultCommand() {
    }
}