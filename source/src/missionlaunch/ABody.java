package missionlaunch;

import com.mhuss.AstroLib.*;
import java.util.GregorianCalendar;

public class ABody {
    
    public final static ABody SUN = new ABody(Planets.SUN, 1391000, 0, 0, 0);
    public final static ABody MERCURY = new ABody(Planets.MERCURY, 4879, 0, 4.3, 47.9);
    public final static ABody VENUS = new ABody(Planets.VENUS, 12104, 0, 10.4, 35.0);
    public final static ABody EARTH = new ABody(Planets.EARTH, 12756, 0, 11.2, 29.8);
    public final static ABody MARS = new ABody(Planets.MARS, 6792, 0, 5.0, 24.1);
    public final static ABody JUPITER = new ABody(Planets.JUPITER, 142984, 0, 59.5, 13.1);
    public final static ABody SATURN = new ABody(Planets.SATURN, 120536, 0, 35.5, 9.7);
    public final static ABody URANUS = new ABody(Planets.URANUS, 51118, 0, 21.3, 6.8);
    public final static ABody NEPTUNE = new ABody(Planets.NEPTUNE, 49528, 0, 23.5, 5.4);
    
    int planet;
    double x, y;
    double diameter;
    double averageVelocity;
    double escapeVelocity;
    double gravity;
    double hdistance;
    double hlongitude;
    ObsInfo observerInfo;
    
    public ABody(int p, double d, double g, double ev, double av){
        planet = p;
        diameter = d;
        gravity = g;
        observerInfo = new ObsInfo();
        escapeVelocity = ev*3600;
        averageVelocity = av*3600;
    }
    
    public void calculateCoordinates(AstroDate jd, double px, double cx, double cy){
        PlanetData planetData = new PlanetData(planet, jd.jd(), observerInfo);
        try{
            double dist = planetData.getPolarRadius();
            hlongitude = planetData.getPolarLon();
            if(planet == Planets.EARTH){
                hlongitude = hlongitude - Math.PI;
            }
            double rad = dist*PlanetData.AU;
            hdistance = rad;
            rad = rad/px;
            x = rad*Math.cos(hlongitude)+cx;
            y = -rad*Math.sin(hlongitude)+cy;
        }
        catch(NoInitException nie){
            System.out.println("Error getting heliocentric coordinates.");
        }
    }
    
    public double getDistance(GregorianCalendar date){
        int minute = date.get(GregorianCalendar.MINUTE);
        int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH)+1;
        AstroDate jd = new AstroDate(day,month,year,hour,minute,0);
        PlanetData planetData = new PlanetData(planet, jd.jd(), observerInfo);
        try{
            double dist = planetData.getPolarRadius();
            return dist*PlanetData.AU;
        }
        catch(NoInitException nie){
            System.out.println("Error getting heliocentric distance.");
            return 0.0;
        }
    }
    
    public double getAverageVelocity(){
        return averageVelocity;
    } 
    
    public double getEscapeVelocity(){
        return escapeVelocity;
    } 
    
    public double getLongitude(GregorianCalendar date){
        int minute = date.get(GregorianCalendar.MINUTE);
        int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH)+1;
        AstroDate jd = new AstroDate(day,month,year,hour,minute,0);
        PlanetData planetData = new PlanetData(planet, jd.jd(), observerInfo);
        try{
            double lon = planetData.getPolarLon();
            if(planet == Planets.EARTH){
                lon = lon - Math.PI;
            }
            return lon;
        }
        catch(NoInitException nie){
            System.out.println("Error getting heliocentric longitude.");
            return 0.0;
        }
    }
    
    public double getRadius(){
        return diameter/2;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
}
