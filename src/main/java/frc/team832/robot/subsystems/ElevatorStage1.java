package frc.team832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team832.robot.RobotMap;
import frc.team832.robot.func.Calcs;

public class ElevatorStage1 extends SubsystemBase {

    private static final double maxEncPos = 26500;
    private static final double lowerPosThreshold = 5000;
    private static final int acceptableError = 350;
    public static double targetPosition;

    public boolean getAtBottom() {
        return !RobotMap.elevator1Group.getReverseLimitSwitch();
    }

    public void setAtBottom() {
        RobotMap.elevator1Group.setSensorPosition(0);
    }

    public void start() {
        RobotMap.elevator1Group.setPosition(0);
    }

    public void stop() {
        RobotMap.elevator1Group.set(0);
    }

    public void setPos(double sliderVal) {
        RobotMap.elevator1Group.setPeakOutputReverse(-100);
        RobotMap.elevator1Group.setNominalOutputReverse(0);
		if(RobotMap.elevator1Group.getSensorPosition()>lowerPosThreshold){

			RobotMap.elevator1Group.setPeakOutputReverse(-100);
		}
		if(RobotMap.elevator1Group.getSensorPosition()<lowerPosThreshold){
			RobotMap.elevator1Group.setPeakOutputReverse(-0.1);
		}

        targetPosition = Math.round(Calcs.map(sliderVal, -1.0, 1.0, 0.0, 1.0) * maxEncPos);

        RobotMap.elevator1Group.setPosition(targetPosition);
    }

    public void setAutoPos(double targetPos) {
        targetPosition = targetPos;
        RobotMap.elevator1Group.setPosition(targetPosition);
    }

    public boolean isFinished() {
//		int currentError = RobotMap.elevator1Group.getClosedLoopError(RobotMap.ElevatorStage1PIDID);
        int currentError = (int) (RobotMap.elevator1Group.getTargetPosition() - RobotMap.elevator1Group.getSensorPosition());
        return (Math.abs(currentError) <= acceptableError);
    }
}