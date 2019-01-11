package frc.team832.robot.func;

import frc.team832.robot.Constants;

public class Calcs {
    public static double map(double value, double in_min, double in_max, double out_min, double out_max) {
        return (value - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double clip(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static boolean inRange(int value, int min, int max) {
        return (value >= min) && (value <= max);
    }

    public static double feetToEncoderTicks(double feet) {
        return inchesToEncoderTicks(feet * 12);
    }

    public static double inchesToEncoderTicks(double inches) {
        return inches / Constants.kInchesPerWheelTurn * Constants.kEncoderPulsesPerRevolution;
    }

    public static double rpmToTicksPerTenth(double rpm) {
        return rpm * Constants.kEncoderPulsesPerRevolution / 600;
    }
}