package LunarLander;
import javax.swing.*;
import java.awt.*;

/***
 * This is the abstract menu class, which can be inherited by any kind
 * of menu-class-like in the game.
 */

public abstract class abstractMenu extends JPanel {

    protected GameController gameController;

    protected FileManager fileManager = FileManager.getInstance();
    protected PropertiesManager properties = PropertiesManager.getInstance();
    protected FontManager fonts = FontManager.getInstance();

    /***
     * Constructor of the class.
     * <p>
     *     Sets inner object gameController as the passed parameter and sets
     *     preferred size of the component.
     * </p>
     *
     * @param gameController object of the class GameController
     */
    public abstractMenu(GameController gameController) {
        this.gameController = gameController;
        setPreferredSize(new Dimension(properties.WIDTH,properties.HEIGHT));

    }

    protected abstract void select();

    /***
     * Paints the component (only the background).
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);
    }


    /***
     * Paints the background of the component.
     *
     * @param g Graphics
     */
    protected void paintBackground(Graphics g){
        Dimension size = getSize();

        g.setColor(properties.backgroundColor);
        g.fillRect(0,0,size.width,size.height);

    }

    /***
     * Draws a string on the component in the middle of the width.
     *
     * @param s the string whisch is drawn
     * @param y position (height) of the string in pixels
     * @param g Graphics
     * @param font font of the string
     */
    protected void drawCenterString(String s,int y,Graphics g,Font font){
        Dimension size = getSize();
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (size.width - metrics.stringWidth(s))/2;
        g.setFont(font);
        g.drawString(s,x,y - (properties.buttonHeight - metrics.getHeight())/2);

    }



}
