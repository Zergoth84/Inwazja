package Inwazja;

public class Shop extends Actor {
    protected int vx;
    protected static final double FIRING_FREQUENCY =0.002;
    boolean used =false;
    
    public Shop(Stage stage) {
        super(stage);
        
         setSpriteNames(new String [] {"shop.gif"});
        setFrameSpeed(50);
        
    }
    
    


   
    
    
    public void collision (Actor a) {
        if (a instanceof Bullet ||a instanceof Bomb || a instanceof Boom){
          //  spawn();
            stage.getPlayer().addScore(20);
  
             }
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