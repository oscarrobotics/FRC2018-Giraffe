package frc.team832.robot.commands.teleop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;

public class TrackVisionTarget extends Command {

	private double _minDist;

	public TrackVisionTarget(double minDist) {
		requires(Robot.westCoastDrive);
		requires(Robot.vision);
		_minDist = minDist;
	}

	@Override
	protected void initialize () {

	}

	@Override
	protected void execute () {
		double distPow = Robot.vision.getDistAdjust();
		double rotPow = Robot.vision.getTurnAdjust();

		Robot.westCoastDrive.ArcadeDrive(distPow, rotPow, ControlMode.PercentOutput);
	}

	@Override
	protected boolean isFinished () {
		return Robot.vision.getData().adjustedDistance <= _minDist;
	}
}
