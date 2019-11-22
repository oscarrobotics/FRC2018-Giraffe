package frc.team832.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team832.robot.RobotMap;

public class Pneumatics extends SubsystemBase {

    public static final DoubleSolenoid gearShift = RobotMap.gearShiftSol;
    public static final DoubleSolenoid intakeExpand = RobotMap.intakeArmSol;
    public static boolean lowGear;


    public void shiftToLow() {
        gearShift.set(Value.kForward);
        lowGear = true;
    }

    public void shiftToHigh() {
        gearShift.set(Value.kReverse);
        lowGear = false;
    }

    public void expandIntake() { intakeExpand.set(Value.kForward); }

    public void closeIntake() {
        intakeExpand.set(Value.kReverse);
    }
}
