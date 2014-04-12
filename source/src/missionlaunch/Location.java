package missionlaunch;

public class Location {
    
    double distance, longitude;
    double x,y;
    
    public Location(double xx, double yy){
        //distance = d;
        //longitude = l;
        x = xx;
        y = yy;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getDistance(){
        return distance;
    }
    
    public double getLongitude(){
        return longitude;
    }
}
