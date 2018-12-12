package Inwazja;

//import  Inwazja.Player.mousePressed;
public class Bullet extends Actor{
    protected static final int BULLET_SPEED=15;
    
    
     public void collision (Actor a) {
        if (a instanceof Monster || a instanceof Boss)
            remove();
    }
    
    public Bullet(Stage stage) {
        super(stage);
        setSpriteNames (new String [] {"pocisk.png"});
    }
    
    
    public void act() {
        super.act();
            x+=BULLET_SPEED;

        if (x <0)
           remove();
        }
        
       
    
    }
