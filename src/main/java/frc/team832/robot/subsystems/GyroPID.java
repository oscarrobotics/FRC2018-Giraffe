package frc.team832.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team832.robot.RobotMap;
import frc.team832.robot.func.MiniPID;

/**
 *
 */
public class GyroPID extends PIDSubsystem {

    private final static double gyrokP = 0.01; // changed from .01 3/18/2018 2:23pm
    private final static double gyrokI = 0;
    private final static double gyrokD = 0;
    private final static double gyrokF = 0;
    private final static double gyrokPer = 0.015;
    private static MiniPID drivePID;
    public double pidOut;

    // Initialize your subsystem here
    public GyroPID() {
        super("GyroPid", gyrokP, gyrokI, gyrokD, gyrokF, gyrokPer);
        setInputRange(-180, 180);
        setOutputRange(-1.0, 1.0);
        getPIDController().setContinuous(false);
        setAbsoluteTolerance(1);
        if (RobotMap.navx.isConnected() && !RobotMap.navx.isCalibrating()) {
            enable();
        }

        //drivePID.setOutputRampRate(1);
        //setSetpoint(0);
    }

    private double getDrivePIDOutput(double gyroPidOutput) {
        drivePID.setSetpoint(this.getSetpoint());
        return drivePID.getOutput(gyroPidOutput);
    }

    public boolean isRollTipping(double rollDelta) {
        double roll = RobotMap.navx.getRoll();
        return (Math.abs(roll) > rollDelta);
    }

    public double rollTipAmount(double rollDelta) {
        double roll = RobotMap.navx.getRoll();
        return (Math.abs(roll) - rollDelta);
    }

    public boolean isPitchTipping(double pitchDelta) {
        double pitch = RobotMap.navx.getPitch();
        return (Math.abs(pitch) > pitchDelta);
    }

    public double pitchTipAmount(double pitchDelta) {
        double pitch = RobotMap.navx.getPitch();
        return (Math.abs(pitch) - pitchDelta);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        return RobotMap.navx.getAngle();
    }

    private double getError() {
        return RobotMap.navx.getAngle() - this.getPIDController().getSetpoint();
    }

    protected void usePIDOutput(double output) {
        pidOut = output;
        SmartDashboard.putBoolean("onTarget", onTarget());
        SmartDashboard.putNumber("GyroPidOut", output);
        SmartDashboard.putNumber("GyroPidError", getError());
    }
}
