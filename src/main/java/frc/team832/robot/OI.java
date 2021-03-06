package frc.team832.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team832.robot.commands.teleop.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static final XboxController driverPad = new XboxController(0);
    public final Joystick operatorPad = new Joystick(1);

    // vision tracking
    public static final JoystickButton trackTarget = new JoystickButton(driverPad, 1);

    //intake
//    public final JoystickButton intakeElbowOverride;
//    public final JoystickButton intakeExpand;

    //gear shift
    public final JoystickButton gearShift;

    //Elevator Position Buttons
//    public final JoystickButton elevatorPickupButton;
//    public final JoystickButton elevatorScaleButton;
//    public final JoystickButton elevatorSwitchButton;

    public OI() {

        trackTarget.whileHeld(new TrackVisionTarget(10));

        //intake
//        intakeExpand = new JoystickButton(operatorPad, 3);
//        intakeExpand.whenPressed(new ExpandIntake());
//        intakeExpand.whenReleased(new CloseIntake());

//        intakeElbowOverride = new JoystickButton(driverPad, XButton.kBumperLeft.value);

        //gear shift
        gearShift = new JoystickButton(driverPad, 6);
        gearShift.whenPressed(new ShiftHigh());
        gearShift.whenReleased(new ShiftLow());

        //Elevator Position Buttons
//        elevatorPickupButton = new JoystickButton(operatorPad, 7);
//        elevatorSwitchButton = new JoystickButton(operatorPad, 6);
//        elevatorScaleButton = new JoystickButton(operatorPad, 5);
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