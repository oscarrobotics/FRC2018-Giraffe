package frc.team832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team832.robot.Constants;
import frc.team832.robot.RobotMap;
import frc.team832.robot.func.Calcs;

public class ElevatorStage1 extends SubsystemBase {

    private static final double maxEncPos = 26500;
    private static final double lowerPosThreshold = 5000;
    private static final int acceptableError = 350;
    public static double targetPosition;

    public boolean getAtBottom() {
        return !RobotMap.elevatorMotor1.getReverseLimitSwitch();
    }

    public void setAtBottom() {
        RobotMap.elevatorMotor1.setSensorPosition(0);
    }

    public void start() {
        RobotMap.elevatorMotor1.setPosition(0);
    }

    public void stop() {
        RobotMap.elevatorMotor1.set(0);
    }

    public void setPositionFromSlider(double sliderVal) {
        handleSafety();
        targetPosition = Math.round(Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos);

        RobotMap.elevatorMotor1.setPosition(targetPosition);
    }

    public void setPos(Constants.ElevatorPosition position){
        handleSafety();
        double rawPos = position.getElevator1Value();
        targetPosition = Math.round(Calcs.map(rawPos, -1.0, 1.0, 0.0, 1.0) * maxEncPos);
        RobotMap.elevatorMotor1.setPosition(targetPosition);
    }

    private void handleSafety() {
        RobotMap.elevatorMotor1.setPeakOutputReverse(-100);
        RobotMap.elevatorMotor1.setNominalOutputReverse(0);
        if(RobotMap.elevatorMotor1.getSensorPosition()>lowerPosThreshold){

            RobotMap.elevatorMotor1.setPeakOutputReverse(-100);
        }
        if(RobotMap.elevatorMotor1.getSensorPosition()<lowerPosThreshold){
            RobotMap.elevatorMotor1.setPeakOutputReverse(-0.1);
        }

    }

    public void setAutoPos(double targetPos) {
        targetPosition = targetPos;
        RobotMap.elevatorMotor1.setPosition(targetPosition);
    }

    public boolean isFinished() {
//		int currentError = RobotMap.elevatorMotor1.getClosedLoopError(RobotMap.ElevatorStage1PIDID);
        int currentError = (int) (RobotMap.elevatorMotor1.getClosedLoopTarget() - RobotMap.elevatorMotor1.getSensorPosition());
        return (Math.abs(currentError) <= acceptableError);
    }
}