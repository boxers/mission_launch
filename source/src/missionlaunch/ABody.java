package missionlaunch;

import com.mhuss.AstroLib.*;
import java.awt.geom.*;

public class ABody {
    
    int planet;
    double x, y;
    double diameter;
    double gravity;
    double hdistance;
    double hlongitude;
    ObsInfo observerInfo;
    
    public ABody(int p, double d, double g){
        planet = p;
        diameter = d;
        gravity = g;
        observerInfo = new ObsInfo();
    }
    
    public void calculateCoordinates(AstroDate jd, double px, double cx, double cy){
        PlanetData planetData = new PlanetData(planet, jd.jd(), observerInfo);
        try{
            double dist = planetData.getPolarRadius();
            double lon = planetData.getPolarLon();
            double degrees = Math.toDegrees(lon);
            double rad = dist*PlanetData.AU;
            hlongitude = degrees;
            hdistance = rad;
            rad = rad/px;
            Arc2D.Double arc = new Arc2D.Double(cx-rad,cy-rad,
                    rad*2,rad*2,90,degrees, Arc2D.PIE);
            Point2D endpoint = arc.getEndPoint();
            x = endpoint.getX();
            y = endpoint.getY();
        }
        catch(NoInitException nie){
            System.out.println("Error getting heliocentric coordinates.");
        }
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
}
