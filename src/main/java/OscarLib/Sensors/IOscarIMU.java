package OscarLib.Sensors;

public interface IOscarIMU extends IOscarGyro {

    double getXAccel();

    double getYAccel();

    double getZAccel();

    double getXDisplacement();

    double getYDisplacement();

    double getZDisplacement();

    double getXMag();

    double getYMag();

    double getZMag();
}
