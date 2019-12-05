package frc.team832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team832.robot.Constants;
import frc.team832.robot.RobotMap;
import frc.team832.robot.func.Calcs;

public class ElevatorStage2 extends SubsystemBase {

    private static final double lowerPosThreshold = 5000;
    private static final int acceptableError = 425;
    public static double targetPosition;
    private static double minEncPos = 1900;
    private static final double maxEncPos = 26000 - minEncPos;

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

    public void setPositionFromSlider (double sliderVal) {
        if (RobotMap.elevatorMotor1.getSensorPosition() >= 1900)
            minEncPos = 0;
        else
            minEncPos = 1900;

        handleSafety();

        targetPosition = Math.round(minEncPos + (Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos));
        RobotMap.elevatorMotorStage2.set(ControlMode.Position, targetPosition);
    }

    public void handleSafety() {
        if (RobotMap.elevatorMotorStage2.getSensorPosition() > lowerPosThreshold) {
            RobotMap.elevatorMotorStage2.setPeakOutputReverse(-100);
        }
        if (RobotMap.elevatorMotorStage2.getSensorPosition() < lowerPosThreshold) {
            RobotMap.elevatorMotorStage2.setPeakOutputReverse(-0.1);
        }
    }

    public void setAutoPos(double targetPos) {
        setPositionFromSlider(targetPos);
    }

    public void setPos(Constants.ElevatorPosition position){
        handleSafety();
        double rawPos = position.getElevator2Value();
        targetPosition = Math.round(Calcs.map(rawPos, -1.0, 1.0, 0.0, 1.0) * maxEncPos);
        RobotMap.elevatorMotor2.set(targetPosition);
    }

    public boolean isFinished() {
        double clError = RobotMap.elevatorMotorStage2.getSensorPosition() - RobotMap.elevatorMotorStage2.getClosedLoopTarget();
        System.out.println("Stage 2 error: " + clError);
        return (Math.abs(clError) <= acceptableError);
    }
}