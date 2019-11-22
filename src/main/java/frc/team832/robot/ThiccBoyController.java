package frc.team832.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.team832.lib.util.OscarMath;

public class ThiccBoyController extends GenericHID {

    public enum ThiccButton {
        Green(5),
        Yellow(6),
        Red(7),
        StickLeft(3),
        StickRight(4);

        public final int index;
        
        ThiccButton(int value) {
            index = value;
        }
    }

    private final JoystickButton greenButton = new JoystickButton(this, ThiccButton.Green.index);
    private final JoystickButton yellowButton = new JoystickButton(this, ThiccButton.Yellow.index);
    private final JoystickButton redButton = new JoystickButton(this, ThiccButton.Red.index);
    private final JoystickButton stickLeftButton = new JoystickButton(this, ThiccButton.StickLeft.index);
    private final JoystickButton stickRightButton = new JoystickButton(this, ThiccButton.StickRight.index);

    public ThiccBoyController(int port) {
        super(port);
    }

    @Override
    public double getX(Hand hand) {
        return 0;
    }

    @Override
    public double getY(Hand hand) {
        return getY();
    }

    public double getRawLeftSlider() {
        return getRawAxis(2);
    }

    public double getLeftSlider() {
        return OscarMath.map(getRawLeftSlider(), -1.0, 1.0, 0.0, 1.0);
    }

    public double getRawRightSlider() {
        return getRawAxis(3);
    }

    public double getRightSlider() {
        return OscarMath.map(getRawRightSlider(), -1.0, 1.0, 0.0, 1.0);
    }

    public JoystickButton getGreenButton() {
        return greenButton;
    }

    public JoystickButton getYellowButton() {
        return yellowButton;
    }

    public JoystickButton getRedButton() {
        return redButton;
    }

    public JoystickButton getLeftStickButton() {
        return stickLeftButton;
    }

    public JoystickButton getRightStickButton() {
        return stickRightButton;
    }
}