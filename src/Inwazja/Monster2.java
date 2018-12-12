package Inwazja;

import java.awt.image.BufferedImage;


public class Monster2 extends Actor {
    protected int vx;
    protected static final double FIRING_FREQUENCY =0.010;
    
    public Monster2(Stage stage) {
        super(stage);
        
        setSpriteNames(new String [] {"fly_swinia.png","fly_swinia2.png"});
        setFrameSpeed(20);
        
    }
    
    
public void spawn(){
    if (Inwazja.km%10==0){
 Monster2 m = new Monster2(stage);
// m.setX ((int)(Math.random()*Stage.SZEROKOSC-Stage.SZEROKOSC/2));
 m.setX(Stage.SZEROKOSC+100);
 m.setY( Stage.MIN_GRY-500+(int)(Math.random()*((Stage.MAX_GRY-Stage.MIN_GRY+50))));
 m.setVx((int)(Math.random()*5)+3);
 stage.addActor(m);
    }
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
        if (a instanceof Bullet ||a instanceof Bomb|| a instanceof Boom){
            znikaj c=new znikaj();
            c.start();
            spawn();
            stage.getPlayer().addScore(20);
  
             }
           }
    
    public void fire(){
        Kupa m = new Kupa(stage);
        m.setX(x+getWidth()/2);
        m.setY(y+getHeight());
        stage.addActor(m);
    }
     
    public void act() {
        super.act();
        x-=vx;
        if (x<-100)
          //  vx=-vx;
        { remove();
            spawn();}
        if (Math.random()<FIRING_FREQUENCY)
            fire();
        
    }
    
    public int getVx() {return vx;}
    public void setVx(int i) {vx=i;}
}