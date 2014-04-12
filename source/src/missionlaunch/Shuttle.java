package missionlaunch;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.awt.geom.*;

public class Shuttle {
    
    private static final double FIXED_TIME_INTERVAL = 60; //In minutes
    
    //ArrayList<TimeStep> flightpath = new ArrayList<TimeStep>();
    double velocity;
    double hdistance;
    double hlongitude;
    ABody destination;
    GregorianCalendar date;
    byte direction = 1;
    double locX, locY;
    
    Visuals missionVisual;
    
    public Shuttle(ABody src, ABody dest, GregorianCalendar d, Visuals mv, double v){
        destination = dest;
        date = d;
        hdistance = src.getDistance(date);
        hlongitude = src.getLongitude(date);
        velocity = v;
        //velocity = dest.getAverageVelocity();
        missionVisual = mv;
        locX = calculateX(hdistance,hlongitude);
        locY = calculateY(hdistance,hlongitude);
    }
    
    private double calculateAngle(double x1, double x2, double y1, double y2){
        if(x2 == x1){
            return Math.PI/2;
        }
        else{
            return Math.atan((y2-y1)/(x2-x1));
        }
    }
    
    private double calculateDistanceNeeded(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    
    public void calculateHDistance(){
        hdistance = Math.sqrt(Math.pow(locX,2)+Math.pow(locY,2));
    }
    
    public void calculateHLongitude(){
        if(locX == 0.0){
           if(locY >= 0){
               hlongitude = Math.PI/2;
           }
           else{
               hlongitude = Math.PI+Math.PI/2;
           }
        }
        else{
            hlongitude = Math.atan(locY/locX);
            if(locX < 0){
                hlongitude = hlongitude + Math.PI;
            }
        } 
    }
    
    public void calculateTravelPath(){
        boolean lastTimeStep = false;
        double travelForce = velocity/60*FIXED_TIME_INTERVAL;
        double magnitude = travelForce;
        double dr = destination.getDistance(date);
        double dt = destination.getLongitude(date);
        double destX = calculateX(dr,dt);
        double destY = calculateY(dr,dt);
        double distanceNeeded = calculateDistanceNeeded(locX,destX,locY,destY);
        double calculatedTimeNeeded = 0.0; //in hours
        int hours;
        double minutes;
        while(distanceNeeded > destination.getRadius() && !lastTimeStep){
            if(magnitude > distanceNeeded){
                lastTimeStep = true;
            }
            //Predict the time needed and where the destination will be at that time
            calculatedTimeNeeded = distanceNeeded/magnitude*(60/FIXED_TIME_INTERVAL);
            hours = (int)calculatedTimeNeeded;
            minutes = calculatedTimeNeeded - hours;
            minutes = 60*minutes;
            System.out.println(distanceNeeded+" distance needed. "+hours+" hours needed. "+minutes+" minutes needed.");
            GregorianCalendar eta = getCopyofDate();
            eta.add(GregorianCalendar.HOUR_OF_DAY, hours);
            eta.add(GregorianCalendar.MINUTE, (int)minutes);
            dr = destination.getDistance(eta);
            dt = destination.getLongitude(eta);
            destX = calculateX(dr,dt);
            destY = calculateY(dr,dt);
            //calculate vectors
            Point2D.Double shuttleVector = calculateVector(locX,destX,locY,destY,magnitude);
            
            //apply vectors to shuttle
            double newX, newY;
            newX = locX + shuttleVector.getX();
            newY = locY + shuttleVector.getY();
            
            //Update time and position
            date.add(GregorianCalendar.MINUTE, (int)FIXED_TIME_INTERVAL);
            Location start = new Location(locX,locY);
            Location end = new Location(newX,newY);
            System.out.println("Distance traveled: "+calculateDistanceNeeded(locX,newX,locY,newY));
            TimeStep t = new TimeStep(velocity,magnitude,start,end,FIXED_TIME_INTERVAL);
            missionVisual.plot(t);
            missionVisual.calculatePlanetPositions(date);
            //flightpath.add(t);
            locX = newX;
            locY = newY;
            calculateHLongitude();
            calculateHDistance();
            
            //Update destination's position and distance needed to reach it
            dr = destination.getDistance(date);
            dt = destination.getLongitude(date);
            destX = calculateX(dr,dt);
            destY = calculateY(dr,dt);
            distanceNeeded = calculateDistanceNeeded(locX,destX,locY,destY);
            
            //Wait and print out data
            try{Thread.sleep(1);}catch(InterruptedException e){}
            System.out.println("Arrived: "+date.get(GregorianCalendar.YEAR)+
                " "+(date.get(GregorianCalendar.MONTH)+1)+
                " "+date.get(GregorianCalendar.DAY_OF_MONTH)+
                " "+date.get(GregorianCalendar.HOUR_OF_DAY)+":"+
                date.get(GregorianCalendar.MINUTE)+" at "+
                hlongitude+" "+hdistance);
            System.out.println("Target location at: "+dt+" "+dr);
        }
    }
    
    private Point2D.Double calculateVector(double x1, double x2, double y1, double y2, double m){
        byte i = 1;
        byte j = 1;
        if(x2 < x1){
            i = -1;
        }
        if(y2 < y1){
            j = -1;
        }
        double theta = Math.abs(calculateAngle(x1,x2,y1,y2));
        double x = m*Math.cos(theta)*i;
        double y = m*Math.sin(theta)*j;
        System.out.println("Vector - theta: "+theta+" i: "+x+" j: "+y);
        return new Point2D.Double(x,y);
    }
    
    private double calculateX(double r, double t){
        return r*Math.cos(t);
    }
    
    private double calculateY(double r, double t){
        return r*Math.sin(t);
    }
    
    private GregorianCalendar getCopyofDate(){
        int minute = date.get(GregorianCalendar.MINUTE);
        int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH);
        return new GregorianCalendar(year,month,day,hour,minute);
    }
    
    //public ArrayList<TimeStep> getFlightPath(){
    //    return flightpath;
    //}
}
