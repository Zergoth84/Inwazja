/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Inwazja;


public class Rocket extends Actor {
    private int ROCKET_SPEED=5;


    
    public Rocket (Stage stage){
        super(stage);
     setSpriteNames (new String [] {"rocket.png"});
    }
    
    
    public void collision(Actor a){
        if (a instanceof Monster){
                        
        remove();
              Boom b = new Boom(stage);
     // stage.getSoundCache().playSound("rocket.wav");
      b.setX(x);
        b.setY(y-20);
      stage.addActor(b);
        
        }

        if (a instanceof Monster2){
        remove();
              Boom b = new Boom(stage);
     // stage.getSoundCache().playSound("rocket.wav");
      b.setX(x);
        b.setY(y-20);
      stage.addActor(b);
        
        }

    }

    
    
   public void act(){
    super.act();
    x=x+ROCKET_SPEED;
    y=y-ROCKET_SPEED;
        if (y<0||y>Stage.MIN_GRY-20||x<0||x>Stage.SZEROKOSC)
        remove();
} 
    
    
}
