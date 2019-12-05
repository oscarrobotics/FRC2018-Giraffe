package frc.team832.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team832.robot.commands.auto.AutoMoveFullElevator;
import frc.team832.robot.commands.teleop.CloseIntake;
import frc.team832.robot.commands.teleop.ExpandIntake;
import frc.team832.robot.commands.teleop.ShiftHigh;
import frc.team832.robot.commands.teleop.ShiftLow;
import frc.team832.robot.subsystems.ElevatorStage1;
import frc.team832.robot.subsystems.ElevatorStage2;

import static frc.team832.robot.Robot.intake;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static final XboxController driverPad = new XboxController(0);
    // public final Joystick operatorPad = new Joystick(1);
    public static final ThiccBoyController operatorPad = new ThiccBoyController(1);
	public static final StratComInterface stratComInterface = new StratComInterface(1);

    //intake
    public final JoystickButton intakeElbowOverride;
//    public final JoystickButton intakeExpand;

    //gear shift
    public final JoystickButton gearShift;

    public OI() {
        //intake
		stratComInterface.getArcadeBlackLeft().whenHeld(new ExpandIntake());
		stratComInterface.getArcadeBlackLeft().whenReleased(new CloseIntake());
        stratComInterface.getArcadeWhiteLeft().whenHeld(new StartEndCommand(() -> intake.intakeLinear(-1.0), intake::stop, intake));
        stratComInterface.getArcadeWhiteRight().whenHeld(new StartEndCommand(() -> intake.intakeLinear(1.0), intake::stop, intake));
        intakeElbowOverride = new JoystickButton(driverPad, XButton.kBumperLeft.value);

        //gear shift
        gearShift = new JoystickButton(driverPad, XButton.kBumperRight.value);
        gearShift.whenPressed(new ShiftHigh());
        gearShift.whenReleased(new ShiftLow());

        //elevator
		stratComInterface.getSC1().whenPressed(new AutoMoveFullElevator(Constants.ElevatorPosition.SWITCH, Robot.elevatorStage1, Robot.elevatorStage2));
		stratComInterface.getSC2().whenPressed(new AutoMoveFullElevator(Constants.ElevatorPosition.SWITCH, Robot.elevatorStage1, Robot.elevatorStage2));
//		stratComInterface.getSC3().whenPressed();
    }

    public static void rumbleDriverPad(double leftRumble, double rightRumble) {
        driverPad.setRumble(RumbleType.kLeftRumble, leftRumble);
        driverPad.setRumble(RumbleType.kRightRumble, rightRumble);
    }

    private enum XButton {
        kBumperLeft(5),
        kBumperRight(6),
        kStickLeft(9),
        kStickRight(10),
        kA(1),
        kB(2),
        kX(3),
        kY(4),
        kBack(7),
        kStart(8);

        @SuppressWarnings("MemberName")
        private final int value;

        XButton(int value) {
            this.value = value;
        }
    }
}