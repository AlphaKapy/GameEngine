/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pack;

import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import javafx.scene.canvas.Canvas;
import javax.swing.JFrame;

/**
 *
 * @author Ghost
 */
public class Game extends Canvas implements Runnable {
    public static int width = 300; //Frame width
    public static int height = width /16 * 9; //Frame height
    public static int scale = 3; //Frame scale

    
    private Thread thread;
    private JFrame frame;
    private boolean running = false;
    
    public Game(){
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);
        
        frame = new JFrame();
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
        while(running) {
            update();
            render();
        }
    }
    
    public void update(){
        
    }
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
    }
    public static void main(String args[]){
        Game game = Game();
        game.frame.setResizable(false);
        game.frame.setTitle("ProtoGame");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
    }
}
