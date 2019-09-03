package frc.team832.robot;

import frc.team832.GrouchLib.mechanisms.Positions.*;

public class Constants {
    public static final double kWheelTurnsPerEncoderTurn = 1.0;
    public static final double kEncoderPulsesPerRevolution = 256.0D;
    public static final double kInchesPerWheelTurn = 6.0 * Math.PI;

    public static final double kInchesPerCount = (1.0 / kEncoderPulsesPerRevolution) * kWheelTurnsPerEncoderTurn * kInchesPerWheelTurn;
    public static final double kCountsPerInch = 1.0 / kInchesPerCount;

    public static final double kFeetPerCount = (1.0 / 12.0) * kInchesPerCount;
    public static final double kCountsPerFoot = kCountsPerInch * 12.0;

    public static final int Elevator1Min = 0;
    public static final int Elevator1Max = 26500;

    public static final MechanismPosition[] Elevator1Positions = new MechanismPosition[] {
            new MechanismPosition("BOTTOM", Elevator1Min),
            new MechanismPosition("SWITCH", (int) (Elevator1Max * 0.2)),
            new MechanismPosition("LOW_SCALE", (int) (Elevator1Max * 0.85)),
            new MechanismPosition("HIGH_SCALE", (int) (Elevator1Max * 0.95)),
    };

    public static final MechanismPositionList Elevator1PositionList = new MechanismPositionList(Elevator1Positions);

    public static final int Elevator2Min = 1900;
    public static final double Elevator2Max = 26000 - Elevator2Min;

    public static final MechanismPosition[] Elevator2Positions = new MechanismPosition[] {
            new MechanismPosition("BOTTOM", Elevator2Min),
            new MechanismPosition("SWITCH", (int) (Elevator2Max * 0.2)),
            new MechanismPosition("LOW_SCALE", (int) (Elevator2Max * 0.85)),
            new MechanismPosition("HIGH_SCALE", (int) (Elevator2Max * 0.95)),
    };

    public static final MechanismPositionList Elevator2PositionList = new MechanismPositionList(Elevator2Positions);

    public static MechanismComplexPosition[] GroupedElevatorPositions = new MechanismComplexPosition[] {
            new MechanismComplexPosition("BOTTOM", Elevator1Positions[0], Elevator2Positions[0])

    };
}
