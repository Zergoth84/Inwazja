/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inwazja;

/**
 *
 * @author z.moszko
 */
public class Pause implements Runnable{
    private static boolean pause=false;
    
    
   public boolean pause(){
       
       if (pause==false){
           pause=true;
       }else{
           pause=false;
       }
       return pause;
   }
   
   public boolean getPause(){
       return pause;
   }
   
   public void run(){
       pause();
   }
   
   
}
