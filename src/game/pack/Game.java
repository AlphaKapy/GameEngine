/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pack;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import game.pack.graphics.Screen;
import game.pack.input.Keyboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javafx.scene.canvas.Canvas;
import javax.swing.JFrame;

/**
 *
 * @author Alpha Käpy
 */
public class Game extends Canvas implements Runnable {
    public static int width = 300; //Frame width
    public static int height = width /16 * 9; //Frame height = 168
    public static int scale = 3; //Frame scale
    public static String title ="ProtoGame";
    
    
    private Screen screen;
    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private boolean running = false;
    
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    
    public Game(){
        Dimension size = new Dimension(width*scale, height*scale);
        frame.setPreferredSize(size); //added frame. because it gave an error before that
        
        screen = new Screen(width,height);
        frame = new JFrame();
        key = new Keyboard();

        addKeyListener(key);
    }
    
    //Thread
    public synchronized void start() {
        running =true;
        thread = new Thread(this, "Display");
        thread.start();
    }
    
    public synchronized void stop(){
        running = false;
        try {
        thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //Game loop
    public void run(){
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; //we want the game to update 60 times a second
        double delta = 0;
        int frames = 0;
        int updates = 0; //should be 60 at all times
        while(running) {
            long now = System.nanoTime();
            delta += (now-lastTime) /ns;
            lastTime = now;
            while (delta >=1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){ //1000 miliseconds = 1 second
                timer += 1000;
                frame.setTitle(title + " | "+ updates + "ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        } stop(); //terminates the game after one cycle
    }
    
    public void update(){
        key.update();
        //x++;
        //y++;
    }
    public void render(){
        BufferStrategy bs = frame.getBufferStrategy(); //added frame. because it gave an error before that
        if (bs == null){
            frame.createBufferStrategy(3); //added frame. because it gave an error before that
            return;
        }
        screen.clear();
        screen.render();
        
        for (int i =0; i<pixels.length;i++) {
            pixels[i] = screen.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        bs.show(); //show calculated buffer
    }
    public static void main(String args[]){
        Game game = new Game(); //no idea why this doesnt work. I don't think this works as a constructor.
        game.frame.setResizable(false);
        game.frame.setTitle("ProtoGame");
        game.frame.add(game); //help?
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
    }
}
