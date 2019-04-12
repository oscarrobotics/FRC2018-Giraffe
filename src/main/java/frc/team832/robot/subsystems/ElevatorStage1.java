package frc.team832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team832.robot.RobotMap;
import frc.team832.robot.func.Calcs;

public class ElevatorStage1 extends Subsystem {

    private static final double maxEncPos = 26500;
    private static final double lowerposthres = 5000;
    private static final int acceptableError = 350;
    public static double targetPosition;

    public boolean getAtBottom() {
        return !RobotMap.elevatorMotor1.getReverseLimitSwitch();
    }

    public void setAtBottom() {
        RobotMap.elevatorMotor1.setSensorPosition(0);
    }

    public void start() {
        RobotMap.elevatorMotor1.set(ControlMode.Position, 0);
    }

    public void stop() {
        RobotMap.elevatorMotor1.set(ControlMode.PercentOutput, 0);
    }

    public void setPos(double sliderVal) {
        RobotMap.elevatorMotor1.setPeakOutputReverse(-100);
        RobotMap.elevatorMotor1.setNominalOutputReverse(0);
//		if(RobotMap.elevatorMotor1.getSelectedSensorPosition(0)>lowerposthres){
//
//			RobotMap.elevatorMotor1.configPeakOutputReverse(-100,0);
//		}
//		if(RobotMap.elevatorMotor1.getSelectedSensorPosition(0)<lowerposthres){
//			RobotMap.elevatorMotor1.configPeakOutputReverse(-0.1,0);
//		}

        targetPosition = Math.round(Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos);
        RobotMap.elevatorMotor1.set(ControlMode.Position, targetPosition);
    }

    public void setAutoPos(double targetPos) {
        targetPosition = targetPos;
        RobotMap.elevatorMotor1.set(ControlMode.Position, targetPosition);
    }

    public boolean isFinished() {
//		int currentError = RobotMap.elevatorMotor1.getClosedLoopError(RobotMap.ElevatorStage1PIDID);
        int currentError = (int) (RobotMap.elevatorMotor1.getTargetPosition() - RobotMap.elevatorMotor1.getSensorPosition());
        return (Math.abs(currentError) <= acceptableError);
    }

    @Override
    protected void initDefaultCommand() {
    }
}