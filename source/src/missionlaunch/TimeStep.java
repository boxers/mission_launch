package missionlaunch;

public class TimeStep {
    double distanceTraveled;
    //double theta;
    double velocity;
    double timeElapsed; //in minutes
    Location start;
    Location end;
    
    public TimeStep(double v, double d, Location st, Location e, double te){
        velocity = v;
        distanceTraveled = d;
        start = st;
        end = e;
        timeElapsed = te;
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
