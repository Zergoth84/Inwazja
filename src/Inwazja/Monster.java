package Inwazja;

import java.awt.image.BufferedImage;


public class Monster extends Actor {
    protected int vx;
    protected static final double FIRING_FREQUENCY =0.002;
    boolean used =false;
    
    public Monster(Stage stage) {
        super(stage);
        
         setSpriteNames(new String [] {"swinia.png","swinia2.png"});
        setFrameSpeed(50);
        
    }
    
    
public void spawn(){
 Monster m = new Monster(stage);
// m.setX ((int)(Math.random()*Stage.SZEROKOSC-Stage.SZEROKOSC/2));
 m.setX(Stage.SZEROKOSC+100);
 m.setY( Stage.MIN_GRY-50+(int)(Math.random()*((Stage.MAX_GRY-Stage.MIN_GRY+50))));
 m.setVx((int)(Math.random()*4)+4);
 stage.addActor(m);

}
    
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
        if (a instanceof Bullet ||a instanceof Bomb || a instanceof Boom){
            znikaj c=new znikaj();
            c.start();
          //  spawn();
            stage.getPlayer().addScore(20);
  
             }
           }
    
    public void fire(){
        Laser m = new Laser(stage);
        m.setX(x+getWidth()/2);
        m.setY(y+getHeight());
        stage.addActor(m);
    }
     
    public void act() {
        super.act();
        if (Player.move&&used==false&&Player.right){
            vx=vx+4;
            used=true;
        }
        if (Player.move==false&&used==true)
        {
            vx=vx-4;
            used=false;
        }
        x-=vx;
        if (x<-100)
        { remove();
            spawn();}
    //    if (Math.random()<FIRING_FREQUENCY)
         //   fire();
        
    }
    
    public int getVx() {return vx;}
    public void setVx(int i) {vx=i;}
}