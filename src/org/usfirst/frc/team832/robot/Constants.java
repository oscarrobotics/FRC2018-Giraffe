package org.usfirst.frc.team832.robot;

public class Constants {
    public static final double kWheelTurnsPerEncoderTurn = 1.0 / 2.975;
    public static final double kEncoderPulsesPerRevolution = 256.0D;
    public static final double kInchesPerWheelTurn = 6.0 * Math.PI;

    public static final double kInchesPerCount = (1.0 / kEncoderPulsesPerRevolution) * kWheelTurnsPerEncoderTurn * kInchesPerWheelTurn;
    public static final double kCountsPerInch = 1.0 / kInchesPerCount;

    public static final double kFeetPerCount = (1.0 / 12.0) * kInchesPerCount;
    public static final double kCountsPerFoot = kCountsPerInch * 12.0;


}
