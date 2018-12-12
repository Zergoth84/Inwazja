package Inwazja;

import static Inwazja.Player.PLAYER_SPEED;

public class Bullet2 extends Actor {
    protected static final int BULLET_SPEED=10;
    
    
     public void collision (Actor a) {
        if (a instanceof Monster)
            remove();
    }
    
    public Bullet2(Stage stage) {
        super(stage);
        setSpriteNames (new String [] {"pocisk.png"});
    }
    
    
    
    public void act() {
        super.act();
            x-=BULLET_SPEED;
        if (x <0||x>Stage.SZEROKOSC)
           remove();
        }
        
       
    
    }
