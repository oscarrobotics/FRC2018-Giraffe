package frc.team832.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team832.GrouchLib.Motion.OscarDoubleSolenoid;
import frc.team832.robot.RobotMap;

public class Pneumatics extends Subsystem {

    public static final OscarDoubleSolenoid gearShift = RobotMap.gearShiftSol;
    public static final OscarDoubleSolenoid intakeExpand = RobotMap.intakeArmSol;
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

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub

    }

}
