package missionlaunch;
import com.mhuss.AstroLib.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;

public class MissionVisual extends JPanel{
    
    BufferedImage board;
    BufferedImage orbits;
    Graphics2D g2d;
    Ellipse2D.Double sunShape = new Ellipse2D.Double(640,512,80,80);
    Ellipse2D.Double earthShape = new Ellipse2D.Double(-50,-50,50,50);
    Ellipse2D.Double mercuryShape = new Ellipse2D.Double(-50,-50,20,20);
    Ellipse2D.Double venusShape = new Ellipse2D.Double(-50,-50,50,50);
    Ellipse2D.Double marsShape = new Ellipse2D.Double(-50,-50,40,40);
    GregorianCalendar date = new GregorianCalendar();
    Thread animator;
    ObsInfo observerInfo = new ObsInfo();
    double px = 249230000/1024;
    
    public MissionVisual(){
        Dimension size = new Dimension(1280, 1024);
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(null);
        //board = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_ARGB);
        //g2d = board.createGraphics();
        //g2d.setColor(Color.WHITE);
        loadImage();
        animate();
    }
    
    public final void animate(){
        animator = new Thread(){
            public void run(){
                int count = 0;
                while(true){
                    try{
                        Thread.sleep(10);
                        date.add(GregorianCalendar.DAY_OF_MONTH, 1);
                        //count++;
                        //if(count == 30000){
                        //    drawOrbit();
                        //    System.out.println("done");
                        //}
                        calculatePlanetPositions();
                        repaint();
                    }
                    catch(InterruptedException e){}
                }
            }
        };
        animator.start();
    }
    
    private void calculatePlanetPositions(){
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH);
        AstroDate jd = new AstroDate(day,month,year,0,0,0);
        PlanetData earth = new PlanetData(Planets.EARTH, jd.jd(), observerInfo);
        PlanetData mars = new PlanetData(Planets.MARS, jd.jd(), observerInfo);
        PlanetData mercury = new PlanetData(Planets.MERCURY, jd.jd(), observerInfo);
        PlanetData venus = new PlanetData(Planets.VENUS, jd.jd(), observerInfo);
        double degrees;
        double rad;
        try{
            double dist = earth.getPolarRadius();
            double lon = earth.getPolarLon();
            degrees = Math.toDegrees(lon);
            rad = dist*PlanetData.AU;
            rad = rad/px;
            Arc2D.Double arc = new Arc2D.Double(sunShape.getX()-rad/2,sunShape.getY()-rad/2,
                    rad,rad,0,degrees, Arc2D.PIE);
            Point2D endpoint = arc.getEndPoint();
            //System.out.println(date.toString());
            //System.out.println("distance = "+rad);
            //System.out.println("degrees = "+degrees);
            //System.out.println(endpoint.getX()+", "+endpoint.getY());
            earthShape.setFrame(endpoint.getX()-25, endpoint.getY()-25, 50, 50);
            //g2d.drawLine((int)endpoint.getX(), (int)endpoint.getY(), (int)endpoint.getX(), (int)endpoint.getY());
            
            dist = mercury.getPolarRadius();
            lon = mercury.getPolarLon();
            degrees = Math.toDegrees(lon);
            rad = dist*PlanetData.AU;
            rad = rad/px;
            arc = new Arc2D.Double(sunShape.getX()-rad/2,sunShape.getY()-rad/2,
                    rad,rad,0,degrees, Arc2D.PIE);
            endpoint = arc.getEndPoint();
            mercuryShape.setFrame(endpoint.getX()-15, endpoint.getY()-15, 30, 30);
            //g2d.drawLine((int)endpoint.getX(), (int)endpoint.getY(), (int)endpoint.getX(), (int)endpoint.getY());
            
            dist = venus.getPolarRadius();
            lon = venus.getPolarLon();
            degrees = Math.toDegrees(lon);
            rad = dist*PlanetData.AU;
            rad = rad/px;
            arc = new Arc2D.Double(sunShape.getX()-rad/2,sunShape.getY()-rad/2,
                    rad,rad,0,degrees, Arc2D.PIE);
            endpoint = arc.getEndPoint();
            venusShape.setFrame(endpoint.getX()-25, endpoint.getY()-25, 50, 50);
            //g2d.drawLine((int)endpoint.getX(), (int)endpoint.getY(), (int)endpoint.getX(), (int)endpoint.getY());
            
            dist = mars.getPolarRadius();
            lon = mars.getPolarLon();
            degrees = Math.toDegrees(lon);
            rad = dist*PlanetData.AU;
            rad = rad/px;
            arc = new Arc2D.Double(sunShape.getX()-rad/2,sunShape.getY()-rad/2,
                    rad,rad,0,degrees, Arc2D.PIE);
            endpoint = arc.getEndPoint();
            marsShape.setFrame(endpoint.getX()-20, endpoint.getY()-20, 40, 40);
            //g2d.drawLine((int)endpoint.getX(), (int)endpoint.getY(), (int)endpoint.getX(), (int)endpoint.getY());
        }
        catch(NoInitException noi){System.out.println("Error getting heliocentric coordinates.");}
    }
    
    private void loadImage(){
        try{
             GraphicsConfiguration gfx_config = GraphicsEnvironment.
                    getLocalGraphicsEnvironment().getDefaultScreenDevice().
                    getDefaultConfiguration();
            BufferedImage img = ImageIO.read(new File("images/board.png"));
            orbits = img;
        }
        catch (IOException e) {}
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(getBackground());
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.drawImage(orbits,0,0,null);
        g2.setColor(Color.YELLOW);
        g2.fill(new Ellipse2D.Double(sunShape.getX()-40,sunShape.getY()-40,80,80));
        g2.setColor(Color.GRAY);
        g2.fill(mercuryShape);
        g2.setColor(Color.PINK);
        g2.fill(venusShape);
        g2.setColor(Color.GREEN);
        g2.fill(earthShape);
        g2.setColor(Color.RED);
        g2.fill(marsShape);
    }
    
    public void drawOrbit(){
        try{
            File outputfile = new File("images/board.png");
            ImageIO.write(board, "png", outputfile);  
        }
        catch (IOException ex) {}  
        finally{g2d.dispose();}
    }
}
