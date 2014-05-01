package missionlaunch;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.awt.geom.*;

public class Shuttle {
    
    ABody sun = ABody.SUN;
    ABody mercury = ABody.MERCURY;
    ABody venus = ABody.VENUS;
    ABody earth = ABody.EARTH;
    ABody mars = ABody.MARS;
    ABody jupiter = ABody.JUPITER;
    ABody saturn = ABody.SATURN;
    ABody uranus = ABody.URANUS;
    ABody neptune = ABody.NEPTUNE;
    
    //private static final double FIXED_TIME_INTERVAL = 60; //In minutes
    private static final double GRAVITATIONAL_CONSTANT = 6.674*Math.pow(10,-11);

    boolean collision = false;
    boolean lastTimeStep = false;
    boolean stopped = false;
    
    //ArrayList<TimeStep> flightpath = new ArrayList<TimeStep>();
    double angle = 0.0;
    double mass = 2050000.0;
    double velocity;
    double hdistance;
    double hlongitude;
    int fuel = 15;
    int sourcePlanet;
    ABody destination;
    GregorianCalendar date;
    GregorianCalendar startDate;
    byte direction = 1;
    double locX, locY;
    double maxAcceleration = 2.16*3600;
    
    
    String body = "";
    
    Visuals missionVisual;
    
    public Shuttle(ABody src, ABody dest, GregorianCalendar d, Visuals mv, double v){
        destination = dest;
        date = d;
        startDate = getCopyofDate();
        hdistance = src.getDistance(date);
        hlongitude = src.getLongitude(date);
        velocity = v;
        //velocity = dest.getAverageVelocity();
        missionVisual = mv;
        locX = calculateX(hdistance,hlongitude);
        locY = calculateY(hdistance,hlongitude);
        sourcePlanet = src.getPlanet();
    }
    
    private double calculateAngle(double x1, double x2, double y1, double y2){
        /*
        if(x2 == x1){
            return Math.PI/2;
        }
        else{
            return Math.atan((y2-y1)/(x2-x1));
        }*/
        if(x2 == x1){
           if(y2 >= y1){
               return Math.PI/2;
           }
           else{
               return Math.PI+Math.PI/2;
           }
        }
        else{
            double t = Math.atan((y2-y1)/(x2-x1));
            if(x2 < x1){
                return (t + Math.PI);
            }
            else{
                return t;
            }
        } 
    }
    
    private double calculateDistance(double x1, double y1, ABody body){
        double dr,dt;
        if(body.getPlanet() == sun.getPlanet()){
            dr = 0;
            dt = 0;
        }
        else{
            dr = body.getDistance(date);
            dt = body.getLongitude(date);
        }
        double x2 = calculateX(dr,dt);
        double y2 = calculateY(dr,dt);
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
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
    
    public Point2D.Double calculateGravitationalForces(double x1, double y1){
        double gc = GRAVITATIONAL_CONSTANT;
        Point2D.Double gf2;
        Point2D.Double gf1 = new Point2D.Double(0,0);
        double dist;
        double g;
        //Sun
        dist = calculateDistance(x1,y1,sun)*1000;
        if(dist > sun.getRadius()*1000){
            g = gc*sun.getMass()/dist/dist/1000*3600*3600;
            gf1 = calculateVector(x1,0,y1,0,g);
        }
        //Mercury
        dist = calculateDistance(x1,y1,mercury)*1000;
        if(dist > jupiter.getRadius()*1000){
            g = gc*mercury.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,mercury,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Venus
        dist = calculateDistance(x1,y1,venus)*1000;
        if(dist > venus.getRadius()*1000){
            g = gc*venus.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,venus,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Earth
        dist = calculateDistance(x1,y1,earth)*1000;
        //if(dist < earth.getRadius()*1000)
        //    dist = earth.getRadius()*1000*2;
        if(dist > earth.getRadius()*1000){
            g = gc*earth.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,earth,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Mars
        dist = calculateDistance(x1,y1,mars)*1000;
        if(dist > mars.getRadius()*1000){
            g = gc*mars.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,mars,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Jupiter
        dist = calculateDistance(x1,y1,jupiter)*1000;
        if(dist > jupiter.getRadius()*1000){
            g = gc*jupiter.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,jupiter,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Saturn
        dist = calculateDistance(x1,y1,saturn)*1000;
        if(dist > saturn.getRadius()*1000){
            g = gc*saturn.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,saturn,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Uranus
        dist = calculateDistance(x1,y1,uranus)*1000;
        if(dist > uranus.getRadius()*1000){
            g = gc*uranus.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,uranus,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        //Neptune
        dist = calculateDistance(x1,y1,neptune)*1000;
        if(dist > neptune.getRadius()*1000){
            g = gc*neptune.getMass()/dist/dist/1000*3600*3600;
            gf2 = calculateVector(x1,y1,neptune,g);
            gf1.setLocation(gf1.getX()+gf2.getX(), gf1.getY()+gf2.getY());
        }
        return gf1;
    }
    
    public void calculateTravelPath(){
        lastTimeStep = false;
        stopped = false;
        collision = false;
        //double travelForce = velocity/60*FIXED_TIME_INTERVAL;
        double travelForce = velocity;
        double magnitude = travelForce;
        double dr = destination.getDistance(date);
        double dt = destination.getLongitude(date);
        double destX = calculateX(dr,dt);
        double destY = calculateY(dr,dt);
        double distanceNeeded = calculateDistanceNeeded(locX,destX,locY,destY);
        double calculatedTimeNeeded = 0.0; //in hours
        int hours;
        double minutes;
        fuel = 15;
        while(distanceNeeded > destination.getRadius() && !lastTimeStep){
            if(magnitude > distanceNeeded){
                lastTimeStep = true;
            }
            //Predict the time needed and where the destination will be at that time
            //calculatedTimeNeeded = distanceNeeded/magnitude*(60/FIXED_TIME_INTERVAL);
            calculatedTimeNeeded = distanceNeeded/magnitude;
            hours = (int)calculatedTimeNeeded;
            minutes = calculatedTimeNeeded - hours;
            minutes = 60*minutes;
            //System.out.println(distanceNeeded+" distance needed. "+hours+" hours needed. "+minutes+" minutes needed.");
            GregorianCalendar eta = getCopyofDate();
            eta.add(GregorianCalendar.HOUR_OF_DAY, hours);
            eta.add(GregorianCalendar.MINUTE, (int)minutes);
            if(destination.getPlanet() == sun.getPlanet()){
                dr = 0;
                dt = 0;
            }
            else{
                dr = destination.getDistance(eta);
                dt = destination.getLongitude(eta);
            }
            destX = calculateX(dr,dt);
            destY = calculateY(dr,dt);
            //calculate initial vector
            Point2D.Double shuttleVector = calculateVector(locX,destX,locY,destY,magnitude);
            
            //apply vectors to shuttle
            double newX, newY;
            newX = locX + shuttleVector.getX();
            newY = locY + shuttleVector.getY();
            Point2D.Double gf = calculateGravitationalForces(locX,locY);
            newX = newX+gf.getX();
            newY = newY+gf.getY();
            //apply accelaration to readjust shuttle
            if(fuel > 0){
                Point2D.Double av = calculateVector(locX,destX,locY,destY,maxAcceleration);
                fuel --;
                newX = newX+av.getX();
                newY = newY+av.getY();
            }
            //Update time and position
            date.add(GregorianCalendar.HOUR_OF_DAY, 1);
            Location start = new Location(locX,locY);
            Location end = new Location(newX,newY);
            //System.out.println("Distance traveled: "+calculateDistanceNeeded(locX,newX,locY,newY));
            angle = calculateAngle(locX,newX,locY,newY);
            TimeStep t = new TimeStep(velocity,magnitude,start,end,1,angle);
            missionVisual.plot(t);
            missionVisual.calculatePlanetPositions(date);
            //flightpath.add(t);
            //Update velocity
            magnitude = calculateDistanceNeeded(locX,newX,locY,newY);
            //System.out.println(magnitude);
            locX = newX;
            locY = newY;
            calculateHLongitude();
            calculateHDistance();
            
            //Update destination's position and distance needed to reach it
            if(destination.getPlanet() == sun.getPlanet()){
                dr = 0;
                dt = 0;
            }
            else{
                dr = destination.getDistance(date);
                dt = destination.getLongitude(date);
            }
            destX = calculateX(dr,dt);
            destY = calculateY(dr,dt);
            distanceNeeded = calculateDistanceNeeded(locX,destX,locY,destY);
            
            //detect collision
            body = "";
            if(!collision && detectCollision(locX,locY,sun)){
                collision = true;
                body = "the Sun.";
            }
            if(!collision && detectCollision(locX,locY,mercury)){
                collision = true;
                body = "Mercury.";
            }
            if(!collision && detectCollision(locX,locY,venus)){
                collision = true;
                body = "Venus.";
            }
            if(!collision && detectCollision(locX,locY,earth)){
                collision = true;
                body = "Earth.";
            }
            if(!collision && detectCollision(locX,locY,mars)){
                collision = true;
                body = "Mars.";
            }
            if(!collision && detectCollision(locX,locY,jupiter)){
                collision = true;
                body = "Jupiter.";
            }
            if(!collision && detectCollision(locX,locY,saturn)){
                collision = true;
                body = "Saturn.";
            }
            if(!collision && detectCollision(locX,locY,uranus)){
                collision = true;
                body = "Uranus.";
            }
            if(!collision && detectCollision(locX,locY,neptune)){
                collision = true;
                body = "Neptune.";
            }
            if(collision){
                lastTimeStep = true;
                //System.out.println("Mission failed.");
                //System.out.println("The shuttle collided with "+body);
            }
            //Wait and print out data
            try{Thread.sleep(1);}catch(InterruptedException e){lastTimeStep = true;}
            //System.out.println("Arrived: "+date.get(GregorianCalendar.YEAR)+
            //    " "+(date.get(GregorianCalendar.MONTH)+1)+
            //    " "+date.get(GregorianCalendar.DAY_OF_MONTH)+
            //    " "+date.get(GregorianCalendar.HOUR_OF_DAY)+":"+
            //    date.get(GregorianCalendar.MINUTE)+" at "+
            //    hlongitude+" "+hdistance);
            //System.out.println("Target location at: "+dt+" "+dr);
        }
    }
    
    private Point2D.Double calculateVector(double x1, double x2, double y1, double y2, double m){
        /*byte i = 1;
        byte j = 1;
        if(x2 < x1){
            i = -1;
        }
        if(y2 < y1){
            j = -1;
        }*/
        //double theta = Math.abs(calculateAngle(x1,x2,y1,y2));
        double theta = calculateAngle(x1,x2,y1,y2);
        double x = m*Math.cos(theta);
        double y = m*Math.sin(theta);
        if(sourcePlanet < destination.getPlanet()){
            double dr = destination.getDistance(date);
            double dt = destination.getLongitude(date);
            double dx = calculateX(dr,dt);
            double dy = calculateY(dr,dt);
            if((x > 0 && y < 0 && locX > dx && locY < dy)
               ||(x < 0 && y < 0 && locX < dx && locY < dy)
               ||(x < 0 && y > 0 && locX < dx && locY > dy)
               ||(x > 0 && y > 0 && locX > dx && locY > dy)
               ){
                x *= -1;
                y *= -1;
            }
        }
        //System.out.println("Vector - theta: "+theta+" i: "+x+" j: "+y);
        return new Point2D.Double(x,y);
    }
    
    private Point2D.Double calculateVector(double x1, double y1, ABody body, double m){
        double dr = body.getDistance(date);
        double dt = body.getLongitude(date);
        double x2 = calculateX(dr,dt);
        double y2 = calculateY(dr,dt);
        double theta = calculateAngle(x1,x2,y1,y2);
        double x = m*Math.cos(theta);
        double y = m*Math.sin(theta);
        //System.out.println("Vector - theta: "+theta+" i: "+x+" j: "+y);
        return new Point2D.Double(x,y);
    }
    
    private double calculateX(double r, double t){
        return r*Math.cos(t);
    }
    
    private double calculateY(double r, double t){
        return r*Math.sin(t);
    }
    
    private boolean detectCollision(double x1, double y1, ABody body){
        if(body.getPlanet() != sourcePlanet && body.getPlanet() != destination.getPlanet()){
            if(body == ABody.SUN && calculateDistance(x1,y1,sun) <= body.getRadius()){
                return true;
            }
            else if(calculateDistance(x1,y1,body) <= body.getRadius()){                
                return true;
            }
        }
        return false;
    }
    
    public String getCollision(){
        return body;
    }
    
    private GregorianCalendar getCopyofDate(){
        int minute = date.get(GregorianCalendar.MINUTE);
        int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH);
        return new GregorianCalendar(year,month,day,hour,minute);
    }
    
    public GregorianCalendar getDate(){
        return date;
    }
    
    public boolean isStopped(){
        return stopped;
    }
    
    public boolean isCollision(){
        return collision;
    }
    
    //public ArrayList<TimeStep> getFlightPath(){
    //    return flightpath;
    //}
    
    public void stopShuttle(){
        if(!lastTimeStep){
            lastTimeStep = true;
            stopped = true;
        }
    }
}
