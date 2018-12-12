package Inwazja;

import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.TexturePaint;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Inwazja extends Canvas implements Stage, KeyListener, MouseListener, Runnable {

    public long usedTime;
    public BufferStrategy strategia;
    private SpriteCache spriteCache;
    private ArrayList actors;
    private Player player;
    private boolean gameEnded = false;
    private SoundCache soundCache;
    public static int km;
    static public int knh = 6;
    public static JFrame okno;
    private BufferedImage background, backgroundTile;
    private int backgroundY;
    private int fixy;
    public static boolean move = true;
    private boolean raz = true;
    public static JPanel panel;
    public int t;

    public Inwazja() {

        spriteCache = new SpriteCache();
        soundCache = new SoundCache();
        okno = new JFrame(".:Inwazja Swin:.");
        panel = (JPanel) okno.getContentPane();
        setBounds(0, 0, Stage.SZEROKOSC, Stage.WYSOKOSC);
        panel.setLayout(null);
        panel.add(this);
        panel.setFocusable(true);
        okno.setBounds(0, 0, Stage.SZEROKOSC, Stage.WYSOKOSC);
        okno.setVisible(true);

        okno.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        okno.setResizable(false);

        createBufferStrategy(2);
        strategia = getBufferStrategy();
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);

    }

    public SoundCache getSoundCache() {
        return soundCache;

    }

    public void gameOver() {
        gameEnded = true;
    }

    public void paintLife(Graphics2D g) {
        g.setPaint(Color.RED);
        g.fillRect(280, Stage.MIN_GRY, 200, 30);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.WHITE);
        g.drawString(player.getLife() + " ", 370, Stage.MIN_GRY + 20);
        g.setPaint(Color.GREEN);
        g.drawString("LIFE", 170, Stage.MIN_GRY + 20);

    }

    public void paintScore(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.GREEN);
        g.drawString("SCORE:", 20, Stage.MIN_GRY + 20);
        g.setPaint(Color.RED);
        g.drawString(player.getScore() + " ", 100, Stage.MIN_GRY + 20);

    }

    public void paintKM(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.GREEN);
        g.drawString("KM:", 20, Stage.MIN_GRY + 100);
        g.setPaint(Color.RED);
        g.drawString(km + " ", 100, Stage.MIN_GRY + 100);
        g.drawString(t + "t", 100, Stage.MIN_GRY + 150);
        g.drawString(Boss.i + "", 400, Stage.MIN_GRY + 150);

    }

    public void paintAmmo(Graphics2D g) {
        int xBase = 280 + Player.MAX_LIFE + 10;
        for (int i = 0; i < player.getClusterBombs(); i++) {
            BufferedImage bomb = spriteCache.getSprite("pocisk.png");
            g.drawImage(bomb, xBase + i * bomb.getWidth(), Stage.MIN_GRY, this);
        }
    }

    public void paintFps(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.white);
        if (usedTime > 0) {
            g.drawString(String.valueOf(1000 / usedTime) + " fps", Stage.SZEROKOSC - 50, Stage.MIN_GRY);
        } else {
            g.drawString("--- fps", Stage.WIDTH - 50, Stage.MIN_GRY);
        }

    }

    public void paintStatus(Graphics2D g) {
        paintScore(g);
        paintLife(g);
        paintAmmo(g);
        paintFps(g);
        paintKM(g);
    }

    public Player getPlayer() {
        return player;
    }

    ;
    
    
    public void paintGameOver() {
        Graphics2D g = (Graphics2D) strategia.getDrawGraphics();
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("GAME OVER", Stage.SZEROKOSC / 2 - 50, Stage.WYSOKOSC / 2);
        strategia.show();
        System.out.println(move);
    }

    public void initWorld() {
        actors = new ArrayList();
        for (int i = 0; i < 5; i++) {
            Monster m = new Monster(this);
            m.setX(Stage.SZEROKOSC + 100);
            m.setY(Stage.MIN_GRY - 50 + (int) (Math.random() * ((Stage.MAX_GRY - Stage.MIN_GRY + 50))));
            m.setVx((int) (Math.random() * 4) + 1);
            actors.add(m);
        }
        //soundCache.loopSound("Contra.wav");
        player = new Player(this);
        player.setX(-Stage.SZEROKOSC);
        player.setY(Stage.WYSOKOSC - 2 * player.getHeight());

        backgroundTile = spriteCache.getSprite("road_1680x1050.gif");
        background = spriteCache.createCompatible(Stage.SZEROKOSC + backgroundTile.getWidth(), Stage.WYSOKOSC,Transparency.OPAQUE);
        Graphics2D g = (Graphics2D) background.getGraphics();
        g.setPaint(new TexturePaint(backgroundTile, new Rectangle(0, 0, backgroundTile.getWidth(), backgroundTile.getHeight())));
        g.fillRect(0, 0, background.getWidth(), background.getHeight());
        backgroundY = backgroundTile.getWidth() * 2;

    }

    public void checkCollisions() {
        Rectangle playerBounds = player.getBounds();
        for (int i = 0; i < actors.size(); i++) {
            Actor a1 = (Actor) actors.get(i);
            Rectangle r1 = a1.getBounds();
            if (r1.intersects(playerBounds)) {
                player.collision(a1);
                a1.collision(player);

            }
            for (int j = i + 1; j < actors.size(); j++) {
                Actor a2 = (Actor) actors.get(j);
                Rectangle r2 = a2.getBounds();
                if (r1.intersects(r2)) {
                    a1.collision(a2);
                    a2.collision(a1);
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyRelased(e);
    }

    public void mousePressed(MouseEvent e) {
        player.mousePressed(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    //  public void mousePressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
    }

    public void paintWorld() {
        Graphics2D g = (Graphics2D) strategia.getDrawGraphics();
        g.drawImage(background, 0, 0, Stage.SZEROKOSC, Stage.WYSOKOSC, backgroundY, 0, backgroundY - Stage.SZEROKOSC, Stage.WYSOKOSC, this);

        for (int i = 0; i < actors.size(); i++) {
            Actor m = (Actor) actors.get(i);
            m.paint(g);
        }
        player.paint(g);
        paintStatus(g);
        strategia.show();
    }

    public void addActor(Actor a) {
        actors.add(a);
    }

    public void updateWorld() {
        int i = 0;
        while (i < actors.size()) {
            Actor m = (Actor) actors.get(i);
            if (m.isMarkedForRemoval()) {
                actors.remove(i);
            } else {
                m.act();
                i++;
            }
        }
        player.act();

    }

    public SpriteCache getSpriteCache() {
        return spriteCache;
    }

    public void zdarzenia() {

        Random r = new Random();

        if (r.nextInt(101) == 100) {
            Monster m = new Monster(this);
            m.setX(Stage.SZEROKOSC + 100);
            m.setY(Stage.MIN_GRY - 50 + (int) (Math.random() * ((Stage.MAX_GRY - Stage.MIN_GRY + 50))));
            m.setVx((int) (Math.random() * 5) + knh);
            actors.add(m);
        }

        //flying monster
        if (t % 500 == 0 && t != 0) {
            {
                Monster2 m2 = new Monster2(this);
                m2.setX(Stage.SZEROKOSC + 100);
                m2.setY(Stage.MIN_GRY - 500 + (int) (Math.random() * ((Stage.MAX_GRY - Stage.MIN_GRY + 50))));
                m2.setVx((int) (Math.random() * 5) + 3);
                addActor(m2);
            }
        }

        //shop  
        if (t == -1000) {
            Shop m = new Shop(this);
            m.setX(SZEROKOSC);
            m.setY(Stage.MAX_GRY - 230);
            actors.add(m);
            Zakupy z = new Zakupy(this);
            z.setX(SZEROKOSC + 40);
            z.setY(m.getY() + m.getHeight() - z.getHeight() - 10);
            actors.add(z);
        }

        //alien boss
        if (t == -200) {
            Boss b = new Boss(this);
            b.setX(SZEROKOSC);
            b.setY(Stage.MAX_GRY - 150);
            actors.add(b);

        }
        if (t == -250 && raz) {
            move = false;
            raz = false;

        }

    }

    public void run() {
        Pause pause = new Pause();

        t = 0;
        initWorld();
        while (isVisible() && !gameEnded) {
            if (pause.getPause() == false) {

                long startTime = System.currentTimeMillis();
                if (Player.right && Player.move == true && move == true) {

                    backgroundY = backgroundY - player.PLAYER_SPEED;
                    if (backgroundY < SZEROKOSC) {
                        backgroundY = backgroundTile.getWidth() * 2;
                    }

                }
                if (Player.right && Player.move == true && move == true) {
                    t = t - player.knh;
                }
                km = -t / 100;

                updateWorld();
                checkCollisions();
                paintWorld();
                zdarzenia();

                usedTime = System.currentTimeMillis() - startTime;
                do {
                    Thread.yield();
                } while (System.currentTimeMillis() - startTime < 15);

            } else {
                System.out.println("buded");
            }
        }
        paintGameOver();
    }

    public static void main(String[] args) {
        Inwazja inv = new Inwazja();
        Thread game = new Thread(inv);
        game.start();
    }

}
