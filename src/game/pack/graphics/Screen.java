/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pack.graphics;

import java.util.Random;

/**
 *
 * @author Eurybus
 */
public class Screen {
    
    
    private int width, height;
    public int[] pixels;
    public final int MAP_SIZE = 8;
    public final int MAP_SIZE_MASK = 8;
    public int[] tiles = new int[MAP_SIZE*MAP_SIZE ];
    
    private Random random = new Random();
    
    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width*height]; // 50400 pixels ( 0...50399)
        
        for (int i=0; i<MAP_SIZE*MAP_SIZE;i++){
            tiles[i] = random.nextInt(0xffffff);
        }
    }
    
    public void clear(){
        for(int i =0; i < pixels.length;i++){
            pixels[i]=0;
        }
    }
    
    public void render(){
        for(int y =0; y< height;y++) {
            int yy = y;
            if(yy < 0 ||yy >=height)
                break;
            for(int x =0;x<width;x++) {
                int xx =x;
                if(xx < 0 ||xx >=width)
                    break;
                int tileIndex = ((xx >>4)& MAP_SIZE_MASK)+ ((yy >>4) * MAP_SIZE_MASK);//bitwise operation: >>, meaning x and y are divided by 16 
                pixels[x+y*width] = tiles[tileIndex]; //tiles are 16x16 large
            }//x loop end
        } //y loop end
    }//render end
}//class end
