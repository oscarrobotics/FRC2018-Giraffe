package frc.team832.robot;

public class Constants {
    public static final double kWheelTurnsPerEncoderTurn = 1.0;
    public static final double kEncoderPulsesPerRevolution = 256.0D;
    public static final double kInchesPerWheelTurn = 6.0 * Math.PI;

    public static final double kInchesPerCount = (1.0 / kEncoderPulsesPerRevolution) * kWheelTurnsPerEncoderTurn * kInchesPerWheelTurn;
    public static final double kCountsPerInch = 1.0 / kInchesPerCount;

    public static final double kFeetPerCount = (1.0 / 12.0) * kInchesPerCount;
    public static final double kCountsPerFoot = kCountsPerInch * 12.0;

    public static final double Elevator1Min = 0;
    public static final double Elevator1Max = 26500;

    public static final double Elevator2Min = 1900;
    public static final double Elevator2Max = 26000 - Elevator2Min;

    public enum ElevatorPosition {
        BOTTOM(Elevator1Min, Elevator2Min),
        SWITCH(Elevator1Max * 0.2, Elevator2Max * 0.2),
        LOW_SCALE(Elevator1Max * 0.85, Elevator2Max * 0.85),
        HIGH_SCALE(Elevator1Max * 0.95, Elevator2Max * 0.95);

        private double elevator1Value, elevator2Value;

        ElevatorPosition(double elevator1Target, double elevator2Target) {
            this.elevator1Value = elevator1Target;
            this.elevator2Value = elevator2Target;
        }

        public double getElevator1Value() {
            return this.elevator1Value;
        }
        public double getElevator2Value() {
            return this.elevator2Value;
        }
    }
}