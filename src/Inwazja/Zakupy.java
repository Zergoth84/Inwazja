package Inwazja;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Zakupy extends Actor {
    boolean used =false;
    JFrame shopping;
    protected int vx;
    public static boolean rakiety = false; 

    public Zakupy(Stage stage) {
        super(stage);
        
         setSpriteNames(new String [] {"door.png"});
        setFrameSpeed(50);
        
    }
    
    


   
    
    
    public void collision (Actor a) {
        if (a instanceof Player && shopping == null){
            rakiety = true;
            shopping();
             }
           }
    

    public void shopping(){
        final Pause pause = new Pause();
        pause.pause();
        shopping = new JFrame("Weapon Shop");
        shopping.setBounds(100,100,300,300);
        shopping.setVisible(true);
        shopping.setAlwaysOnTop(true);
        JPanel panel = (JPanel)shopping.getContentPane();
        panel.setBackground(Color.GRAY);
                shopping.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    pause.pause();
                    System.out.println(pause.getPause());
                    shopping=null;
                }
                });

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     
    public void act() {
        super.act();
        if (Player.move&&used==false&&Player.right){
            vx=Player.PLAYER_SPEED;
            used=true;
        }
        if (Player.move==false&&used==true||Player.right==false)
        {
            vx=0;
            used=false;
        }
        x-=vx;
        if (x<-100)
        { remove();
        ;}
    //    if (Math.random()<FIRING_FREQUENCY)
         //   fire();
        
    }
    
    public int getVx() {return vx;}
    public void setVx(int i) {vx=i;}
}