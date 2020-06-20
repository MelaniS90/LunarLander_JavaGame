package LunarLander;

import java.awt.*;
import java.util.LinkedList;

/**
 * This is a Meteor with light, an enemy of the rocket.
 */

public class Meteor{

    private PropertiesManager properties = PropertiesManager.getInstance();

    private Color color;
    private float x,y;
    private int  width, height;

    LinkedList<Light> light = new LinkedList<>();

    /**
     * Constructor of the class.
     * <p>
     *     Sets init values of a meteor.
     * </p>
     * @param x init horizontal position
     * @param y init vertical position
     * @param width init width
     * @param height init height
     * @param color init color
     */
    public Meteor(float x, float y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /***
     * Paints meteor and light.
     *
     * @param g Graphics
     */
    public void drawMeteor(Graphics g){

        g.setColor(color);
        g.fillRect((int)x,(int)y,width,height);

        for(int i=0; i<light.size(); i++){
            light.get(i).drawLight(g);
        }

    }

    /***
     * Updates position of the meteor (according to  velocity and window width and height).
     *
     * @param width width of the window
     * @param height height of the window
     */
    public void update(double width, double height){
        if(properties.meteorX < properties.WIDTH/2)
            x+=properties.meteorVelX * (width/properties.WIDTH);
        else
            x-=properties.meteorVelX * (width/properties.WIDTH);

        if(properties.meteorY < properties.HEIGHT/2)
            y+=properties.meteorVelY * (height/properties.HEIGHT);
        else
            y-=properties.meteorVelY * (height/properties.HEIGHT);

        for(int i=0; i<light.size(); i++){
            light.get(i).update();
        }

        light.add(new Light((int)x,(int)y,this.width,this.height, Color.yellow,properties.lightLife));
    }

    /***
     * Resizies the meteor according to the size of the window before resizing and size after resizing.
     *
     * @param width width of the window after resizing
     * @param height height of the window after resizing
     * @param prevWidth width of the window before resizing
     * @param prevHeight height of the window before resizing
     */
    public void resize(double width, double height,int prevWidth, int prevHeight){
        x *= (width/prevWidth);
        y *= (height/prevHeight);
        this.width = (int) (properties.meteorWIDTH * (width/properties.WIDTH));
        this.height =(int) (properties.meteorHEIGHT * (height/properties.HEIGHT));

        if(this.width == 0)
            this.width = 1;
        if(this.height == 0)
            this.height = 1;

        light.clear();
    }

    /**
     * Returns bounds of the meteor.
     *
     * @return rectangle of the meteor
     */
    public Rectangle getBounds(){ return new Rectangle((int)this.x,(int)this.y,this.width,this.height); }

    public float getX(){ return x; }
    public float getY(){return  y;}
    public int getWidth(){return width; }
    public int getHeight(){return height;}

    private class Light{

        private Color color;
        private int width;
        private int height;
        private int x, y;

        private  float alpha = properties.alphaLight;
        private float life;

        public Light(int x,int y, int width,int height, Color color, float life){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.life = life;
        }


        public void drawLight(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setComposite(makeTransparent(alpha));

            g.setColor(color);
            g.fillRect(x, y, width, height);

            g2d.setComposite(makeTransparent(properties.alphaLight));

        }

        public AlphaComposite makeTransparent(float alpha){
            int type = AlphaComposite.SRC_OVER;
            return (AlphaComposite.getInstance(type, alpha));
        }

        public void update() {
            if (alpha > life) {
                alpha -= (life - properties.subLightLife);
            }else
                light.remove(this);
        }

    }




}
