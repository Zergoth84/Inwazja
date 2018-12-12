
package Inwazja;


public class Laser extends Actor{
    protected static final int BULLET_SPEED=12;
    
    public Laser(Stage stage){
        super(stage);
        setSpriteNames(new String[] {"laser.gif"});
        setFrameSpeed(10);
    
    }
    public void act(){
        super.act();
        x-=BULLET_SPEED;
        if (x<0)
            remove();
    }
    }
    
