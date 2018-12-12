
package Inwazja;


public class Kupa extends Actor{
    protected static final int BULLET_SPEED=3;
    
    public Kupa(Stage stage){
        super(stage);
        setSpriteNames(new String[] {"kupa.png"});
        setFrameSpeed(10);
    
    }
    public void act(){
        super.act();
        y+=BULLET_SPEED;
        if (y>Stage.WYSOKOSC)
            remove();
    }
    }
    
