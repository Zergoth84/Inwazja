/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Inwazja;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Boss extends Actor{
   
    protected int vx=3;
    public static int bossHP=30;
    protected static final double FIRING_FREQUENCY =0.010;
    boolean used =false;
    public static int i;
    private int raz =2;
    
    public Boss(Stage stage) {
        super(stage);
        
         setSpriteNames(new String [] {"boss.png","boss2.png"});
        setFrameSpeed(50);
        
    }
    
    
    Random r = new Random();
    
    class znikaj extends Thread{
     public void run(){
         
        BufferedImage image = spriteCache.getSprite("gracz.png");
      
       setSpriteNames(new String[] {"swinia3.png","swinia3.png"});
        setFrameSpeed(100);
     
         czekaj(50);
         setSpriteNames(new String [] {"swinia4.png","swinia4.png"});
         setFrameSpeed(100);

          czekaj(100);
         remove();
          
    }
    
    }
    
   
    
    
    public void collision (Actor a) {
        if (a instanceof Bullet ||a instanceof Bomb || a instanceof Rocket){
         
            bossHP=bossHP-1;
            if (bossHP<0){
                znikaj c=new znikaj();
                c.start();
                Inwazja.move=true;
            }
            stage.getPlayer().addScore(20);
  
             }
           }
    
    public void fire(){
        Laser m = new Laser(stage);
        m.setX(x+getWidth()/2);
        m.setY(y+getHeight()/2);
        stage.addActor(m);
    }
     
    public void act() {
        super.act();

        x-=vx;
        if (x<900){
            x=900;

            y=y-raz;
            i=r.nextInt(20);
        if (i>18||y<100){
                raz=-raz;

             
            
          }

               
        }

        
           if (Math.random()<FIRING_FREQUENCY)
           fire();
        
    }
    
    public int getVx() {return vx;}
    public void setVx(int i) {vx=i;}
} 
    
    
    
    
    
    

