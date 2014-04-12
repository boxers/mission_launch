package missionlaunch;
import java.util.GregorianCalendar;

public interface Visuals {
    void plot(TimeStep t);
    void calculatePlanetPositions(GregorianCalendar d);
    void changeScope(double s);
    void resetDrawingBoard();
}
