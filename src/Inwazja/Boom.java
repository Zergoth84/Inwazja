/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Inwazja;


public class Boom extends Actor {
    private int ROCKET_SPEED=5;


    
    public Boom (Stage stage){
        super(stage);
     setSpriteNames (new String [] {"boom.gif"});
    }
    
    
    public void collision(Actor a){
        if (a instanceof Monster || a instanceof Monster2){

        y=y-20;
                setSpriteNames (new String [] {"boom.gif"});
        remove();

        }

    }

    
    
   public void act(){
    super.act();
} 
    
    
}
