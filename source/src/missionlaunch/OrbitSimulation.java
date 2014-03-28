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

public class OrbitSimulation extends JPanel{
    
    private final static int P_HEIGHT = 1024;
    private final static int P_WIDTH = 1280;
    private final static double LARGEST_APHELION = 249230000.0; 
    double px = LARGEST_APHELION/(P_HEIGHT/2.0);
    double centerX = P_WIDTH/2.0;
    double centerY = P_HEIGHT/2.0;
    BufferedImage board;
    BufferedImage orbits;
    Graphics2D g2d;
    
    ABody mercury = new ABody(Planets.MERCURY, 4879, 0);
    ABody venus = new ABody(Planets.VENUS, 12104, 0);
    ABody earth = new ABody(Planets.EARTH, 12756, 0);
    ABody mars = new ABody(Planets.MARS, 6792, 0);
    
    Ellipse2D.Double sunShape = new Ellipse2D.Double(600,472,80,80);
    Ellipse2D.Double earthShape = new Ellipse2D.Double(-50,-50,50,50);
    Ellipse2D.Double mercuryShape = new Ellipse2D.Double(-50,-50,20,20);
    Ellipse2D.Double venusShape = new Ellipse2D.Double(-50,-50,50,50);
    Ellipse2D.Double marsShape = new Ellipse2D.Double(-50,-50,40,40);
    //Ellipse2D.Double testPlot = new Ellipse2D.Double(630,502+(69800000/px),20,20);
    GregorianCalendar date = new GregorianCalendar();
    Thread animator;
    ObsInfo observerInfo = new ObsInfo();
    
    VisualViewPort visualViewPort;
    
    public OrbitSimulation(JViewport jvp){
        Dimension size = new Dimension(P_WIDTH, P_HEIGHT);
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(null);
        visualViewPort = (VisualViewPort)jvp;
        //board = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_ARGB);
        //g2d = board.createGraphics();
        //g2d.setColor(Color.WHITE);
        loadImage();
        animate();
    }
    
    public final void animate(){
        animator = new Thread(){
            public void run(){
                //int count = 0;
                while(true){
                    try{
                        Thread.sleep(1);
                        date.add(GregorianCalendar.HOUR, 1);
                        /*count++;
                        if(count == 90000){
                            drawOrbit();
                            System.out.println("done");
                        }*/
                        calculatePlanetPositions(date);
                        repaint();
                    }
                    catch(InterruptedException e){}
                }
            }
        };
        animator.start();
    }
    
    public void calculatePlanetPositions(GregorianCalendar d){
        //int second = date.get(GregorianCalendar.SECOND);
        //int minute = date.get(GregorianCalendar.MINUTE);
        int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH)+1;
        AstroDate jd = new AstroDate(day,month,year,hour,0,0);
        mercury.calculateCoordinates(jd, px, centerX, centerY);
        venus.calculateCoordinates(jd, px, centerX, centerY);
        earth.calculateCoordinates(jd, px, centerX, centerY);
        mars.calculateCoordinates(jd, px, centerX, centerY);
        
        mercuryShape.setFrame(mercury.getX()-mercuryShape.getWidth()/2,
                mercury.getY()-mercuryShape.getHeight()/2, 
                mercuryShape.getWidth(), mercuryShape.getHeight());
        //g2d.drawLine((int)mercury.getX(), (int)mercury.getY(), (int)mercury.getX(), (int)mercury.getY());
        venusShape.setFrame(venus.getX()-venusShape.getWidth()/2,
                venus.getY()-venusShape.getHeight()/2, 
                venusShape.getWidth(), venusShape.getHeight());
        //g2d.drawLine((int)venus.getX(), (int)venus.getY(), (int)venus.getX(), (int)venus.getY());
        earthShape.setFrame(earth.getX()-earthShape.getWidth()/2,
                earth.getY()-earthShape.getHeight()/2, 
                earthShape.getWidth(), earthShape.getHeight());
        //g2d.drawLine((int)earth.getX(), (int)earth.getY(), (int)earth.getX(), (int)earth.getY());
        marsShape.setFrame(mars.getX()-marsShape.getWidth()/2,
                mars.getY()-marsShape.getHeight()/2, 
                marsShape.getWidth(), marsShape.getHeight());
        //g2d.drawLine((int)mars.getX(), (int)mars.getY(), (int)mars.getX(), (int)mars.getY());
        visualViewPort.setDate(year+"-"+month+"-"+day+" "+hour+":00 UT");
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
        g2.fill(sunShape);
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
