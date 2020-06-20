package LunarLander;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/***
 * This is a font manager which loads and creates fonts from properties
 * files and also draws the strings.
 */
public class FontManager {

    private static FontManager instance;

    static{
        try{
            instance = new FontManager();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static synchronized FontManager getInstance(){return instance;}

    /***
     * Loads the data from the properties file and parse it to a font type.
     *
     * @param fontName name of the font
     * @return created Font
     */
    public Font loadFont(String fontName){
        Font newFont = null;
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT,new File("resources/fonts/"+ fontName +".ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("resources/fonts/"+ fontName +".ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFont;

    }


    /***
     * Draws center string for a button according to the window dimension.
     *
     * @param s text
     * @param y position of a text on y axis
     * @param g Graphics
     * @param font font of a text
     * @param windowWidth width of the window
     * @param buttonHeight height of the window
     */
    public void drawCenterString(String s,int y,Graphics g,Font font,int windowWidth,int buttonHeight){
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (windowWidth - metrics.stringWidth(s))/2;
        g.setFont(font);
        g.drawString(s,x,y - (buttonHeight - metrics.getHeight())/2);

    }

    /***
     * Draws center rectangle for buttons.
     *
     * @param y position of a rectangle on y axis
     * @param g Graphics
     * @param windowWidth width of the window
     * @param buttonWidth width of the button
     * @param buttonHeight height of the button
     */
    public void drawCenterRect(int y,Graphics g,int windowWidth,int buttonWidth,int buttonHeight){

        int x = (windowWidth - buttonWidth)/2;
        g.drawRect(x,y,buttonWidth,buttonHeight);

    }

    /***
     * Draws a string in a button in gold-like proportion of the window.
     *
     * @param s text
     * @param y location of a text on y axis
     * @param g Graphics
     * @param font font of a text
     * @param windowWidth width of the window
     * @param buttonWidth width of the button
     * @param buttonHeight height of the button
     * @param side side of the drawn button (optimal for small window:
     *             side=1 left button, side=3 right button)
     */
    public void drawGoldString(String s, int y, Graphics g,Font font,int windowWidth,int buttonWidth, int buttonHeight,int side){
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (windowWidth - buttonWidth)/4 * side + (buttonWidth - metrics.stringWidth(s))/2;
        g.setFont(font);
        g.drawString(s,x,y - (buttonHeight - metrics.getHeight())/2);

    }

    /***
     * Draws a String specifically in the button.
     *
     * @param s String
     * @param x horizonatal position of the String
     * @param y vertical position of the String
     * @param g Graphics
     * @param font font of the string
     * @param buttonWidth width of the button
     * @param buttonHeight height of the button
     */
    public void drawButtonString(String s,int x, int y, Graphics g,Font font,int buttonWidth, int buttonHeight){
        FontMetrics metrics = g.getFontMetrics(font);
        int x_nowy = x + (buttonWidth - metrics.stringWidth(s))/2;
        g.setFont(font);
        g.drawString(s,x_nowy,y - (buttonHeight - metrics.getHeight())/2);

    }

    /***
     * Draws a rectangle for a button in gold-like proportion of the window.
     *
     * @param y location of a rectangle on y axis
     * @param g Graphics
     * @param windowWidth width of the window
     * @param buttonWidth width of the button
     * @param buttonHeight height of the button
     * @param side side of the button (optimal for small window:
     *             side=1 left button, side right button)
     */
    public void drawGoldRect(int y,Graphics g,int windowWidth,int buttonWidth,int buttonHeight,int side){

        int x = (windowWidth - buttonWidth)/4 * side;
        g.drawRect(x,y,buttonWidth,buttonHeight);

    }

}
