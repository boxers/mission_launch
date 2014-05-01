package missionlaunch;

public class TimeStep {
    double angle;
    double distanceTraveled;
    //double theta;
    double velocity;
    double timeElapsed; //in hours
    Location start;
    Location end;
    
    public TimeStep(double v, double d, Location st, Location e, double te, double a){
        velocity = v;
        distanceTraveled = d;
        start = st;
        end = e;
        timeElapsed = te;
        angle = a;
    }
    
    public double getAngle(){
        return angle;
    }
    
    public Location getStartLocation(){
        return start;
    }
    
    public Location getEndLocation(){
        return end;
    }
    
    public String toString(){
        return distanceTraveled+" km traveled.";
    }
    
}
