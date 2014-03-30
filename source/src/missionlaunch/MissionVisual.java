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
    
    private final static int P_HEIGHT = 1024;
    private final static int P_WIDTH = 1280;
    final static double MARS_APHELION = 249230000.0; 
    final static double JUPITER_APHELION = 816600000.0;
    final static double SATURN_APHELION = 1514500000.0; 
    final static double URANUS_APHELION = 3003600000.0; 
    final static double NEPTUNE_APHELION = 4545700000.0; 
    
    int scopeIndex = 0;
    
    double aphelion = MARS_APHELION;
    double px = aphelion/(P_HEIGHT/2.0);
    double centerX = P_WIDTH/2.0;
    double centerY = P_HEIGHT/2.0;
    
    BufferedImage[] orbits = new BufferedImage[5];
    
    ABody sun = new ABody(Planets.SUN, 1391000, 0);
    ABody mercury = new ABody(Planets.MERCURY, 4879, 0);
    ABody venus = new ABody(Planets.VENUS, 12104, 0);
    ABody earth = new ABody(Planets.EARTH, 12756, 0);
    ABody mars = new ABody(Planets.MARS, 6792, 0);
    ABody jupiter = new ABody(Planets.JUPITER, 142984, 0);
    ABody saturn = new ABody(Planets.SATURN, 120536, 0);
    ABody uranus = new ABody(Planets.URANUS, 51118, 0);
    ABody neptune = new ABody(Planets.NEPTUNE, 49528, 0);
    
    Ellipse2D.Double sunShape = new Ellipse2D.Double(600,472,80,80);
    Ellipse2D.Double earthShape = new Ellipse2D.Double(-500,-500,50,50);
    Ellipse2D.Double mercuryShape = new Ellipse2D.Double(-500,-500,20,20);
    Ellipse2D.Double venusShape = new Ellipse2D.Double(-500,-500,50,50);
    Ellipse2D.Double marsShape = new Ellipse2D.Double(-500,-500,40,40);
    Ellipse2D.Double jupiterShape = new Ellipse2D.Double(-500,-500,40,40);
    Ellipse2D.Double saturnShape = new Ellipse2D.Double(-500,-500,40,40);
    Ellipse2D.Double uranusShape = new Ellipse2D.Double(-500,-500,40,40);
    Ellipse2D.Double neptuneShape = new Ellipse2D.Double(-500,-500,40,40);
    
    GregorianCalendar visualDate = new GregorianCalendar();
    ObsInfo observerInfo = new ObsInfo();
    
    VisualViewPort visualViewPort;
    
    public MissionVisual(JViewport jvp){
        Dimension size = new Dimension(P_WIDTH, P_HEIGHT);
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(null);
        visualViewPort = (VisualViewPort)jvp;
        loadImages();
    }
    
    public void calculatePlanetPositions(GregorianCalendar date){
        if(visualDate != date){
            visualDate = date;
        }
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
        jupiter.calculateCoordinates(jd, px, centerX, centerY);
        saturn.calculateCoordinates(jd, px, centerX, centerY);
        uranus.calculateCoordinates(jd, px, centerX, centerY);
        neptune.calculateCoordinates(jd, px, centerX, centerY);
        
        mercuryShape.setFrame(mercury.getX()-mercuryShape.getWidth()/2,
                mercury.getY()-mercuryShape.getHeight()/2, 
                mercuryShape.getWidth(), mercuryShape.getHeight());
        
        venusShape.setFrame(venus.getX()-venusShape.getWidth()/2,
                venus.getY()-venusShape.getHeight()/2, 
                venusShape.getWidth(), venusShape.getHeight());
        
        earthShape.setFrame(earth.getX()-earthShape.getWidth()/2,
                earth.getY()-earthShape.getHeight()/2, 
                earthShape.getWidth(), earthShape.getHeight());
        
        marsShape.setFrame(mars.getX()-marsShape.getWidth()/2,
                mars.getY()-marsShape.getHeight()/2, 
                marsShape.getWidth(), marsShape.getHeight());
        
        jupiterShape.setFrame(jupiter.getX()-jupiterShape.getWidth()/2,
                jupiter.getY()-jupiterShape.getHeight()/2, 
                jupiterShape.getWidth(), jupiterShape.getHeight());
        
        saturnShape.setFrame(saturn.getX()-saturnShape.getWidth()/2,
                saturn.getY()-saturnShape.getHeight()/2, 
                saturnShape.getWidth(), saturnShape.getHeight());
        
        uranusShape.setFrame(uranus.getX()-uranusShape.getWidth()/2,
                uranus.getY()-uranusShape.getHeight()/2, 
                uranusShape.getWidth(), uranusShape.getHeight());
        
        neptuneShape.setFrame(neptune.getX()-neptuneShape.getWidth()/2,
                neptune.getY()-neptuneShape.getHeight()/2, 
                neptuneShape.getWidth(), neptuneShape.getHeight());
        
        repaint();
        visualViewPort.setDate(year+"-"+month+"-"+day+" "+hour+":00 UT");
    }
    
    public void changeScope(double aphel){   
        aphelion = aphel;
        px = aphelion/(P_HEIGHT/2.0);
        centerX = P_WIDTH/2.0;
        centerY = P_HEIGHT/2.0;
        
        if(aphel == MARS_APHELION){
            scopeIndex= 0;
            sunShape.setFrame(600,472,80,80);
            earthShape.setFrame(-500,-500,40,40);
            mercuryShape.setFrame(-500,-500,20,20);
            venusShape.setFrame(-500,-500,40,40);
            marsShape.setFrame(-500,-500,30,30);
            jupiterShape.setFrame(-500,-500,60,60);
            saturnShape.setFrame(-500,-500,60,60);
            uranusShape.setFrame(-500,-500,50,50);
            neptuneShape.setFrame(-500,-500,50,50);
        }
        else if(aphel == JUPITER_APHELION){
            scopeIndex= 1;
            sunShape.setFrame(628,500,24,24);
            earthShape.setFrame(-500,-500,16,16);
            mercuryShape.setFrame(-500,-500,10,10);
            venusShape.setFrame(-500,-500,16,16);
            marsShape.setFrame(-500,-500,14,14);
            jupiterShape.setFrame(-500,-500,20,20);
            saturnShape.setFrame(-500,-500,20,20);
            uranusShape.setFrame(-500,-500,18,18);
            neptuneShape.setFrame(-500,-500,18,18);
        }
        else if(aphel == SATURN_APHELION){
            scopeIndex= 2;
            sunShape.setFrame(632,504,16,16);
            earthShape.setFrame(-500,-500,10,10);
            mercuryShape.setFrame(-500,-500,6,6);
            venusShape.setFrame(-500,-500,10,10);
            marsShape.setFrame(-500,-500,8,8);
            jupiterShape.setFrame(-500,-500,14,14);
            saturnShape.setFrame(-500,-500,14,14);
            uranusShape.setFrame(-500,-500,12,12);
            neptuneShape.setFrame(-500,-500,12,12);
        }
        else if(aphel == URANUS_APHELION){
            scopeIndex= 3;
            sunShape.setFrame(636,508,8,8);
            earthShape.setFrame(-500,-500,5,5);
            mercuryShape.setFrame(-500,-500,4,4);
            venusShape.setFrame(-500,-500,5,5);
            marsShape.setFrame(-500,-500,5,5);
            jupiterShape.setFrame(-500,-500,10,10);
            saturnShape.setFrame(-500,-500,10,10);
            uranusShape.setFrame(-500,-500,10,10);
            neptuneShape.setFrame(-500,-500,10,10);
        }
        else{
            scopeIndex= 4;
            sunShape.setFrame(637,509,6,6);
            earthShape.setFrame(-500,-500,4,4);
            mercuryShape.setFrame(-500,-500,3,3);
            venusShape.setFrame(-500,-500,4,4);
            marsShape.setFrame(-500,-500,4,4);
            jupiterShape.setFrame(-500,-500,8,8);
            saturnShape.setFrame(-500,-500,10,10);
            uranusShape.setFrame(-500,-500,10,10);
            neptuneShape.setFrame(-500,-500,10,10);
        }
        calculatePlanetPositions(visualDate);
    }
    
    private void loadImages(){
        try{
            for(int i = 0; i < orbits.length; i++){
                BufferedImage img = ImageIO.read(new File("images/board"+i+".png"));
                orbits[i] = img;
            }
        }
        catch (IOException e) {}
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(getBackground());
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.drawImage(orbits[scopeIndex],0,0,null);
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
        g2.setColor(Color.LIGHT_GRAY);
        g2.fill(jupiterShape);
        g2.setColor(Color.ORANGE);
        g2.fill(saturnShape);
        g2.setColor(Color.CYAN);
        g2.fill(uranusShape);
        g2.setColor(Color.BLUE);
        g2.fill(neptuneShape);
    }
}
