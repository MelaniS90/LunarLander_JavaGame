package LunarLander;

import java.awt.*;
import java.util.Random;

public class Lunar {

    private PropertiesManager properties = PropertiesManager.getInstance();

    public Rectangle[] lunars = new Rectangle[10];
    Random r = new Random();

    public Lunar(){
        createLunars();
    }

    public void draw(Graphics g){
        for(int i=0; i<10;i++){
            g.setColor(Color.yellow);
            g.fillRect(lunars[i].x,lunars[i].y,lunars[i].width,lunars[i].height);
        }
    }

    public void updateSize(double width, double height, int prevWidth, int prevHeight){
        for(int i=0; i<lunars.length;i++){
            lunars[i].x = (int) (lunars[i].x * (width/prevWidth));
            lunars[i].y = (int) (lunars[i].y * (height/prevHeight));
            lunars[i].width = (int) (5 * (width/properties.WIDTH));
            lunars[i].height = (int) (5 * (height/properties.HEIGHT));

            if(lunars[i].width == 0){
                lunars[i].width = 1;
            }
            if(lunars[i].height == 0){
                lunars[i].height = 1;
            }
        }
    }


    private void createLunars(){
        int x = 0;
        int y = 0;
        for(int i = 0; i<10;i++){
            x = r.nextInt(properties.WIDTH - 50) + 25;
            for(int j=0;j<properties.terrain.size();j++){
                if(properties.terrain.get(j).x1 < x && properties.terrain.get(j).x2 >= x){
                    if(properties.terrain.get(j).y1 >= properties.terrain.get(j).y1){
                        y = (int) (r.nextDouble() * properties.terrain.get(j).y2);
                    }
                    else
                        y = (int) (r.nextDouble() * properties.terrain.get(j).y1);
                }
            }
            lunars[i] = new Rectangle(x,y,5,5);
        }
    }
}
