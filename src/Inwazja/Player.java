

package Inwazja;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


//import java.awt.geom.AffineTransform;

public class Player extends Actor{
    public static final int PLAYER_SPEED =4;
    public static final int MAX_LIFE =200;
    public static final int MAX_BOMBS=5;
    private int score;
    private int life;
    protected int vx;
    protected int vy;
    public static boolean f,up,down,left,right;
    public static boolean shootleft=false, shootright=true;
    private int clusterBombs = 5;
    public int getScore() {return score;}
    public void setScore(int i) {score=i;}
    public int getLife() {return life;}
    public void setLife(int i){life=i;}
    public int getClusterBombs() {return clusterBombs;}
    public void setClusterBombs(int i){clusterBombs=i;}
    private boolean shoot=true,rshoot=true;
    public static boolean move=false;
    public static int knh=2;
    public char player;
    static public int k=1,i=0;

    
    

    public Player(Stage stage) {
        super(stage);
        setSpriteNames(new String[] {"gracz.png","gracz2.png"});
        setFrameSpeed(20);
        clusterBombs=MAX_BOMBS;
        life=MAX_LIFE;
        
            Inwazja.panel.getActionMap().put("down pressed", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    down=true;
                }
            });
            Inwazja.panel.getActionMap().put("down released", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    down=false;
                }
            });
            
            
            
            
            
         Inwazja.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down pressed");
         Inwazja.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,true), "down released");
         
    }
    
    
private void klatki(int j){
    i++;
    if(i%j==0){
        k++;
        if(k>2){
            k=1;
        }
    }
}
    
    
    
    
public void paint(Graphics2D g){
if(Zakupy.rakiety){
        klatki(40);        
        if (k==1) g.drawImage( spriteCache.getSprite("gracz_z_plecakiem.png"), x,y, stage );
        if (k==2) g.drawImage( spriteCache.getSprite("gracz_z_plecakiem2.png"), x,y, stage );       
}else{
        klatki(40);
        if (k==1) g.drawImage( spriteCache.getSprite("gracz.png"), x,y, stage );
        if (k==2) g.drawImage( spriteCache.getSprite("gracz2.png"), x,y, stage );
}
}
 
    
    
    
    
    
    public void collision(Actor a) {
        if (a instanceof Monster) {
        a.remove();
        addScore(40);
        addLife(-40);
        }
        if (a instanceof Laser){
            a.remove();
            addLife(-20);
    }
                if (getLife()<=0)
            stage.gameOver();
    }
    
    public void addLife(int i){life +=i;}
    
    public void addScore(int i) {score+=i;}
    
    public void act() {
        super.act();
        x+=vx;
        y+=vy;
        
       if (x<0)
           x=0;
       if (x>Stage.SZEROKOSC - getWidth())
           x=Stage.SZEROKOSC - getWidth();
       if (y<0)
           y=0;
       if (y>Stage.MIN_GRY-getHeight())
           y=Stage.MIN_GRY-getHeight();
       if (y<Stage.MAX_GRY-getHeight())
           y=Stage.MAX_GRY-getHeight();
       if (x>Stage.SZEROKOSC/2)
       {move=true;
           x=Stage.SZEROKOSC/2;}
       if (x<Stage.SZEROKOSC/2&&x>Stage.SZEROKOSC/2-10)
           move=false;
        if (x<Stage.SZEROKOSC*-1)
       {
          move=true;
        
           x=Stage.SZEROKOSC*-1;}
           
      if (x>Stage.SZEROKOSC&&x<Stage.SZEROKOSC+10)
       move=false;
    }


    
    
    public void fire() {
        {
        Bullet b = new Bullet(stage);
        b.setX(x);
        b.setY(y-b.getHeight());
        stage.addActor(b);
        }
    }
    
    
    
    
    public int getVx() {return vx;}
    public void setVx(int i) {vx=i;}
    public int getVy() {return vy;}
    public void setVy(int i) {vy=i;}
    
    protected void updateSpeed() {
        vx=0;vy=0;
        if(down) vy=PLAYER_SPEED;
        if (up) vy=-PLAYER_SPEED;
        if(left) vx=-PLAYER_SPEED;
        if(right) vx=PLAYER_SPEED;
    }
    
    
    
        
     private class laduj extends Thread{
         public void run(){
            if (shoot== true){
                
              // if (shootright){
         Bullet b = new Bullet(stage);
        // stage.getSoundCache().playSound("shoot.wav");
         b.setX(x+15);
         b.setY(y+33);
         stage.addActor(b);
        
             //  }
     /*         if (shootleft) {
             Bullet2 b2 = new Bullet2(stage);
             b2.setX(x);
                  b2.setY(y);
               stage.addActor(b2);      
              }
               */
         shoot = false;
         czekaj(200);
         shoot=true;
             }
         }
     }
    
    
    
    public void fireCluster() {
        if (clusterBombs ==0)
            return;
   
    clusterBombs--;
    stage.addActor(new Bomb(stage, Bomb.UP_LEFT,x,y));
    stage.addActor(new Bomb(stage, Bomb.UP,x,y));
    stage.addActor(new Bomb (stage, Bomb.UP_RIGHT,x,y));
    stage.addActor(new Bomb (stage, Bomb.LEFT,x,y));
    stage.addActor(new Bomb (stage, Bomb.RIGHT,x,y));
    stage.addActor(new Bomb (stage, Bomb.DOWN_LEFT,x,y));
    stage.addActor(new Bomb (stage, Bomb.DOWN,x,y));
    stage.addActor(new Bomb (stage, Bomb.DOWN_RIGHT,x,y));
}
    
private class fireRocket extends Thread{
      public void run(){
          if (Zakupy.rakiety&&rshoot){
  

      Rocket r = new Rocket(stage);
      stage.getSoundCache().playSound("rocket.wav");
      r.setX(x);
        r.setY(y-r.getHeight());
      stage.addActor(r);   
      rshoot=false;
      czekaj(600);
      rshoot=true;
  }   
     
}  
}  
    
    public void pause(){
            Inwazja.panel.getActionMap().put("foo", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    down=true;
                         updateSpeed();
                }
            });

        /* connect two keystrokes with the newly created "foo" action:
           - a
           - CTRL-a
            
        */
                // Define ActionListener
            Action actionListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
            JButton source = (JButton) actionEvent.getSource();
            System.out.println("Activated: " + source.getText());
      }
    };

            KeyStroke space = KeyStroke.getKeyStroke(' ');
            InputMap inputmap  = Inwazja.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputmap.put(space, actionListener);
            System.out.println("asdas");
        
    }
    





    public void keyRelased (KeyEvent e) {
        switch(e.getKeyCode()) {
           // case KeyEvent.VK_S : down=false; break;
            case KeyEvent.VK_W: up=false ;break;
            case KeyEvent.VK_A: left=false; break;
            case KeyEvent.VK_D: right=false; break;
           // case KeyEvent.VK_SPACE: shoot=true; break;
        }
        updateSpeed();
    }
    
  
    
    public void keyPressed (KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: up=true; break;
           // case KeyEvent.VK_S: down=true; break;
            case KeyEvent.VK_A: left=true;shootleft=true; shootright=false; break;
            case KeyEvent.VK_D: right=true;shootleft=false; shootright=true; break;
            case KeyEvent.VK_1: if (knh<12)knh=knh+2; break;
            case KeyEvent.VK_2: if (knh>2) knh=knh-2; break;    
            case KeyEvent.VK_B: fireCluster();break;
            case KeyEvent.VK_E: fireRocket r = new fireRocket();r.start(); break; 
        }
        updateSpeed();

    }

              public void mousePressed(MouseEvent e) {
             if (e.getButton()==MouseEvent.BUTTON1){
                  //my=my/nvd;
                 // mx=mx/nvd;
      laduj t = new laduj();
      t.start();
                  
             }

    }
       
       
    
    
}