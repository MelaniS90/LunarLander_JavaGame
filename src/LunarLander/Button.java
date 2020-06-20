package LunarLander;
import java.awt.*;


/***
 * This is a button for the menus of the game.
 */
public class Button extends Rectangle {

    private FontManager fontManager = FontManager.getInstance();

    private String text;
    private Font font;
    private Color color;
    private Color selectedColor;
    private int buttonWidth;
    private int buttonHeight;
    private boolean selected;


    /***
     * Constructor of the class.
     * <p>
     *     Sets the variables of the button as the parameters.
     * </p>
     *
     * @param text text inside the button
     * @param y location (height) of the buton
     * @param font font of the text
     * @param color default color of the button
     * @param selectedColor color of the selected button
     * @param buttonWidth button width
     * @param buttonHeight button height
     */
    public Button(String text,int y,Font font, Color color, Color selectedColor,int buttonWidth,int buttonHeight){
        this.text = text;
        this.y = y - buttonHeight;
        this.font = font;
        this.color = color;
        this.selectedColor = selectedColor;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
    }

    /***
     * Sets the selection to true or false.
     *
     * @param selected if true, variable selected is being set
     *                 to true. if false, variable selected
     *                 is being set to false.
     */
    public void setSelected(boolean selected){
        this.selected = selected;
    }

    /***
     * Draws the button in the middle of the window/component
     *
     * @param g Graphics
     * @param windowDimension dimension of the window or component on which
     *                        the button is drawn
     */
    public void drawCenterButton(Graphics g,Dimension windowDimension){
        if(selected)
            g.setColor(selectedColor);
        else
            g.setColor(color);

        fontManager.drawCenterString(text, y + buttonHeight, g, font, windowDimension.width, buttonHeight);
        fontManager.drawCenterRect(y,g,windowDimension.width,buttonWidth,buttonHeight);

        this.x = (windowDimension.width - buttonWidth)/2;
        this.width = buttonWidth;
        this.height = buttonHeight;

    }

    /***
     * Draws the button in the gold-like proportion of the window/component
     *
     * @param g Graphics
     * @param windowDimension dimension of the window or component on which
     *                        the button is drawn
     * @param side the number represents the location (width) o the drawn button,
     *             optimal for small window: sied=1 left button, side=3 right button
     */
    public void drawGoldButton(Graphics g,Dimension windowDimension,int side){
        if(selected)
            g.setColor(selectedColor);
        else
            g.setColor(color);

        fontManager.drawGoldString(text, y + buttonHeight, g, font, windowDimension.width, buttonWidth,buttonHeight,side);
        fontManager.drawGoldRect(y,g,windowDimension.width,buttonWidth,buttonHeight,side);

        this.x = (windowDimension.width - buttonWidth)/4 * side;
        this.width = buttonWidth;
        this.height = buttonHeight;

    }


    /***
     * Draws the button.
     *
     * @param g Graphics
     * @param x horizontal position of the button
     */
    public void drawButton(Graphics g,int x){
        if(selected)
            g.setColor(selectedColor);
        else
            g.setColor(color);

        fontManager.drawButtonString(text, x,y + buttonHeight, g, font, buttonWidth,buttonHeight);
        g.drawRect(x,y,buttonWidth,buttonHeight);

        this.x = x;
        this.width = buttonWidth;
        this.height = buttonHeight;

    }


    /**
     * Sets the color of the button when selected
     *
     * @param selectedColor color of selected button
     */
    public void setSelectedColor(Color selectedColor){ this.selectedColor = selectedColor; }

    /**
     * Sets the color of the button when not selected
     *
     * @param color color of the button
     */
    public void setColor(Color color){ this.color = color; }



}
