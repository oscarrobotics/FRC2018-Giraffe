package frc.team832.robot.subsystems;

import frc.team832.GrouchLib.Motors.CANVictor;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team832.robot.RobotMap;

public class Intake extends Subsystem {
    public static final CANVictor intakeMotorLeft = RobotMap.leftIntake;
    public static final CANVictor intakeMotorRight = RobotMap.rightIntake;
    private final double maxSpeed = 1.0;
    public double intakeStickX, intakeStickY;

    public void stop() {
        intakeMotorLeft.set(ControlMode.PercentOutput, 0);
        intakeMotorRight.set(ControlMode.PercentOutput, 0);
    }

    public void IntakeAuto(double pow) {
        intakeLinear(pow);
    }

    public void IntakeWithStick(double x, double y) {
        boolean limitSwitch = !RobotMap.intakeElbow.getReverseLimitSwitch();
//		boolean limitSwitch = true;
//		// deadzone
        if (Math.abs(x) < .2)
            x = 0;
        else if (x > .2 && x <= .5)
            x = .5;
        else if (x > .5)
            x = 0.7;
        else if (x < -.2 && x >= -.5)
            x = -.5;
        else if (x < -.5)
            x = -0.7;

        if (Math.abs(y) < .2)
            y = 0;
        else if (y > .2 && y <= .5)
            y = .5;
        else if (y > .5)
            y = .7;
        else if (y < -.2 && y >= -.5)
            y = -.5;
        else if (y < -.5)
            y = -.7;

        //TODO: THIS IS ONLY NEEDED ON THE PRACTICE BOT
        //TODO: THIS SHOULD BE COMMENTED ON THE ACTUAL BOT
        //y = -y;

        SmartDashboard.putNumber("spin", x);
        SmartDashboard.putNumber("linear", y);
//		// limit switch
        //if (limitSwitch) {
        if (Math.abs(y) > 0.2) {
            intakeLinear(y);
        } else if (Math.abs(x) > 0.2) {
            intakeSpin(x);
        } else {
            intakeLinear(-.1);

            intakeSpin(0);
        }
		/*}
		else {
			intakeSpin(0);
			if(y < -0.2) intakeLinear(y);
		}*/
    }

    private void intakeSpin(double speed) {
        intakeMotorLeft.set(ControlMode.PercentOutput, speed * maxSpeed);
        intakeMotorRight.set(ControlMode.PercentOutput, -speed * maxSpeed);
    }

    private void intakeLinear(double speed) {
        intakeMotorLeft.set(ControlMode.PercentOutput, speed * maxSpeed);
        intakeMotorRight.set(ControlMode.PercentOutput, speed * maxSpeed);
    }

    @Override
    protected void initDefaultCommand() {
    }

}
