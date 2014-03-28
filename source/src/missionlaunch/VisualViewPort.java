package missionlaunch;
import javax.swing.*;
import java.awt.*;

public class VisualViewPort extends JViewport {
    
    private String date = "test";
    Thread animator;
    
    public VisualViewPort(){
        animate();
    }
    
    public final void animate(){
        animator = new Thread(){
            public void run(){
                while(true){
                    try{
                        Thread.sleep(1);
                        repaint();
                    }
                    catch(InterruptedException e){}
                }
            }
        };
        animator.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(Color.WHITE);
        g2.drawString(date,50,50);
    }
    
    public void setDate(String d){
        date = d;
    }
}