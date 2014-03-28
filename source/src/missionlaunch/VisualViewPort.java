package missionlaunch;
import javax.swing.*;
import java.awt.*;

public class VisualViewPort extends JViewport {
    
    private String date = "test";
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(Color.WHITE);
        g2.drawString(date,50,50);
    }
    
    public void setDate(String d){
        date = d;
        repaint();
    }
}