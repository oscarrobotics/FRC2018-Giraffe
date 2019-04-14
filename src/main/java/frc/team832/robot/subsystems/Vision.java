package frc.team832.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team832.GrouchLib.Sensors.Vision.JevoisTracker;
import frc.team832.GrouchLib.Util.OscarMath;
import frc.team832.robot.RobotMap;

public class Vision extends Subsystem {

	private static final double InchesFromFront = 31;
	private  static final double InchesFromCenter = -7; // left of center is positive

	private static final double Turn_kP = 0.021;
	private static final double Turn_MaxOutput = 0.25;

	private static final double Dist_kP = 0.05;
	private static final double Dist_MaxOutput = 0.5;

	private static final JevoisTracker jevois = RobotMap.jevois;
	private static JevoisData latestData;
	private static ShuffleboardTab visionTab = Shuffleboard.getTab("Vision");

	private static NetworkTableEntry hasTargetNTE;
	private static NetworkTableEntry distanceNTE;
	private static NetworkTableEntry adjustedDistanceNTE;
	private static NetworkTableEntry xOffsetNTE;
	private static NetworkTableEntry adjustedXOffsetNTE;
	private static NetworkTableEntry yawNTE;
	private static NetworkTableEntry turnAdjustOutputNTE;
	private static NetworkTableEntry turnAdjustkPNTE;
	private static NetworkTableEntry distAdjustOutputNTE;
	private static NetworkTableEntry distAdjustkPNTE;

	private static double currentTurn_kP = Turn_kP;
	private static double turnAdjustOutput;

	private static double currentDist_kP = Dist_kP;
	private static double distAdjustOutput;

	public Vision() {
		hasTargetNTE = visionTab.add("HasTarget", false).getEntry();
		distanceNTE = visionTab.add("Distance", 0.0).getEntry();
		adjustedDistanceNTE = visionTab.add("Adj. Dist", 0.0).getEntry();
		xOffsetNTE = visionTab.add("X Offset", 0.0).getEntry();
		adjustedXOffsetNTE = visionTab.add("Adj. X Offset", 0.0).getEntry();
		yawNTE = visionTab.add("Yaw", 0.0).getEntry();
		turnAdjustOutputNTE = visionTab.add("Turn Adj. Out", 0.0).getEntry();
		turnAdjustkPNTE = visionTab.add("Turn Adj. kP", Turn_kP).getEntry();
		distAdjustOutputNTE = visionTab.add("Dist Adj. Out", 0.0).getEntry();
		distAdjustkPNTE = visionTab.add("Dist Adj. kP", Dist_kP).getEntry();
	}

	public void pushData() {
		hasTargetNTE.setBoolean(hasTarget());
		if (hasTarget()) {
			distanceNTE.setDouble(latestData.distance);
			adjustedDistanceNTE.setDouble(latestData.adjustedDistance);
			xOffsetNTE.setDouble(latestData.xOffset);
			adjustedXOffsetNTE.setDouble(latestData.adjustedXOffset);
			yawNTE.setDouble(latestData.yaw);
		}
		turnAdjustOutputNTE.setDouble(turnAdjustOutput);
		distAdjustOutputNTE.setDouble(distAdjustOutput);

		double newTurn_kP = turnAdjustkPNTE.getDouble(Turn_kP);
		if ( newTurn_kP != currentTurn_kP) {
			currentTurn_kP = newTurn_kP;
		}
		turnAdjustkPNTE.setDouble(currentTurn_kP);

		double newDist_kP = distAdjustkPNTE.getDouble(Dist_kP);
		if (newDist_kP != currentDist_kP) {
			currentDist_kP = newDist_kP;
		}
		distAdjustkPNTE.setDouble(currentDist_kP);
	}

	public void main() {
		getLatestData();
		turnAdjustOutput = calculateTurnAdjust();
		distAdjustOutput = calculateDistAdjust();
	}

	public boolean hasTarget() {
		return jevois.detectsTape();
	}

	public double getTurnAdjust() {
		return turnAdjustOutput;
	}

	private double calculateTurnAdjust() {
		if (hasTarget()) {
			double xOff = latestData.adjustedXOffset;
			double val = Math.signum(xOff)*(6*Math.log(Math.abs(xOff*.25) + 15))/50  /*currentTurn_kP * latestData.xOffset*/;
			return OscarMath.clip(val, -Turn_MaxOutput, Turn_MaxOutput);
		}
		else { return 0; }
	}

	public double getDistAdjust() {
		return distAdjustOutput;
	}

	private double calculateDistAdjust() {
		if (hasTarget()) {
			double val = -(12*Math.log(latestData.adjustedDistance*.1) + 15)/150  /*currentDist_kP * -latestData.adjustedDistance*/;
			val *= 3;
			return OscarMath.clip(val, -Dist_MaxOutput, Dist_MaxOutput);
		}
		else { return 0; }
	}

	public JevoisData getData() {
		return latestData;
	}

	private void getLatestData() {
		if (jevois.detectsTape()) {
			latestData = parseData(jevois.getParts());
		}
	}

	private JevoisData parseData(String[] rawDataSplit) {
		JevoisData data = new JevoisData();
		try {
			data.distance = Double.parseDouble(rawDataSplit[0]);
			data.adjustedDistance = Math.abs(data.distance - InchesFromFront);
			data.xOffset = Double.parseDouble(rawDataSplit[1]);
			data.adjustedXOffset = data.xOffset - InchesFromCenter;
			data.yaw = Double.parseDouble(rawDataSplit[2]);
		} catch (Exception ex) {
			DriverStation.reportError("Failed to parse Jevois Data!", ex.getStackTrace());
		}
		return data;
	}

	public static class JevoisData {
		public double distance;
		public double adjustedDistance;
		public double xOffset;
		public double adjustedXOffset;
		public double yaw;
	}

	@Override
	protected void initDefaultCommand () {

	}
}
