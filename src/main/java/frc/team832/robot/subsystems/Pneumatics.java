package frc.team832.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team832.GrouchLib.motion.DoubleSolenoid;
import frc.team832.robot.RobotMap;

public class Pneumatics extends SubsystemBase {

    public static final DoubleSolenoid gearShift = RobotMap.gearShiftSol;
    public static final DoubleSolenoid intakeExpand = RobotMap.intakeArmSol;
    public static boolean lowGear;


    public void shiftToLow() {
        gearShift.forward();
        lowGear = true;
    }

    public void shiftToHigh() {
        gearShift.reverse();
        lowGear = false;
    }

    public void expandIntake() {
        intakeExpand.forward();
    }

    public void closeIntake() {
        intakeExpand.reverse();
    }
}
