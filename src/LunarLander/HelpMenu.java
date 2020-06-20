package LunarLander;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/***
 * This is the help menu of the game. Class inherits from abstractMenu.
 */
public class HelpMenu extends abstractMenu{

    private Button[] options;

    /***
     * Constructor of the class.
     * <p>
     *     Creates buttons, adds and implements MouseListener and MouseMotionListener
     * </p>
     *
     * @param gameController object of GameController for the constructor
     *                       of the inherited class
     */
    public HelpMenu(GameController gameController) {
        super(gameController);

        options = new Button[properties.buttonNumHelp];
        options[0] = new Button(properties.Backbutton,properties.button4Y,properties.buttonFont,properties.fontColor,properties.selectedColor,properties.buttonWidth,properties.buttonHeight);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                boolean clicked = false;
                for(int i = 0;i<properties.buttonNumHelp;i++){
                    if(options[i].intersects(new Rectangle(e.getX(),e.getY(),1,1))){
                        clicked = true;
                    }
                }
                if(clicked) select();
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                for (int i = 0; i < properties.buttonNumHelp; i++) {
                    if (options[i].intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
                        options[i].setSelected(true);

                    } else
                        options[i].setSelected(false);

                }
                    repaint();
            }
        });

    }

    /***
     * Changes the game state (to MENU) when the button is clicked.
     */
    protected void select(){

                gameController.changeState(STATE.MENU);
                System.out.println(properties.Backbutton);
    }

    /***
     * Paints the component.
     * <p>
     *     Draws the title of the component, help text and the back button.
     * </p>
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(properties.fontColor);
        drawCenterString(properties.NAME,properties.TitleY,g,properties.titleFont);

        drawHelpText(g);
        paintButtons(g);

    }


    private void paintButtons(Graphics g){
        Dimension size = getSize();

        options[0].drawCenterButton(g,size);

    }


    private void drawHelpText(Graphics g){
        g.setFont(properties.textFont);
        g.setColor(properties.ComponentColor);
        int y=properties.helpTextY;
        for (String line : properties.helpText.split("\n")) {
            y += g.getFontMetrics().getHeight() + properties.lineBreakY;
            g.drawString(line, properties.helpTextX, y);
        }
    }





}
