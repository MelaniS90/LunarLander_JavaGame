package LunarLander;
import java.awt.*;
import java.awt.event.*;

/***
 * This is the main menu of the game. Class inherits from abstractMenu.
 */

public class MainMenu extends abstractMenu{

    /***
     * Array of buttons for the menu
     */
    private final Button[] options = new Button[properties.buttonNumMenu];

    private int currentSelection;

    /***
     * Constructor of the class.
     * <p>
     *     Creates buttons for the menu, adds and implements MauseListener and MouseMotionListener
     *     to the component.
     * </p>
     *
     * @param gameController object of GameController for the constructor of the
     *                       inherited class
     */
    public MainMenu(GameController gameController) {
        super(gameController);


        options[0] = new Button(properties.StartButton,properties.button1Y,properties.buttonFont,properties.fontColor,properties.selectedColor,properties.buttonWidth,properties.buttonHeight);
        options[1] = new Button(properties.RankingButton,properties.button2Y,properties.buttonFont,properties.fontColor,properties.selectedColor,properties.buttonWidth,properties.buttonHeight);
        options[2] = new Button(properties.HelpButton,properties.button3Y,properties.buttonFont,properties.fontColor,properties.selectedColor,properties.buttonWidth,properties.buttonHeight);
        options[3] = new Button(properties.ExitButton,properties.button4Y,properties.buttonFont,properties.fontColor,properties.selectedColor,properties.buttonWidth,properties.buttonHeight);


        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(properties.GameState != STATE.LOGING){
                    boolean clicked = false;
                    for(int i = 0;i<properties.buttonNumMenu;i++){
                        if(options[i].intersects(new Rectangle(e.getX(),e.getY(),1,1))){
                         clicked = true;
                        }
                    }
                    if(clicked) select();
                }
            }


        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if(properties.GameState != STATE.LOGING) {
                    for (int i = 0; i < properties.buttonNumMenu; i++) {
                        if (options[i].intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
                            currentSelection = i + 1;
                            options[i].setSelected(true);

                        } else
                            options[i].setSelected(false);

                    }
                }repaint();
            }
        });
    }

    /***
     * Sets the new state according to the clicked button.
     */
    protected void select(){

        switch (currentSelection){
            case 1:
                gameController.changeState(STATE.LOGING);
                gameController.logingIn.initLoginWindow();
                break;
            case 2:
                gameController.changeState(STATE.RANKING);
                break;
            case 3:
                gameController.changeState(STATE.HELP);
                break;
            case 4:
                System.exit(0);
                break;

        }currentSelection = 0;
    }

    /***
     * Paints the component.
     * <p>
     *     Draws the title and buttons.
     * </p>
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension size = getSize();

        g.setColor(properties.fontColor);
        drawCenterString(properties.NAME,properties.TitleY,g,properties.titleFont);

        drawOptions(g);

    }

    /***
     * Draws buttons.
     *
     * @param g Graphics
     */
    private void drawOptions(Graphics g){
        Dimension size = getSize();

        for(int i=0;i<options.length;i++){
            options[i].drawCenterButton(g,size);
        }
    }

}
