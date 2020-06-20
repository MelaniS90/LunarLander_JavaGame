package LunarLander;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/***
 * This is the end game window shown after the end of the game along with the main window.
 */
public class EndGame extends abstractMenu implements Runnable{

    private Button[] options;
    private JFrame frame;
    private boolean win;
    private String[] winnerInfo;

    public Thread thread;

    private int currentSelection;

    /***
     * Constructor of the calss.
     * <p>
     *     Sets the dimention of the component, adds and implements MouseListener
     *     and MouseMotionListener, initialize the window.
     * </p>
     *
     * @param gameController
     */
    public EndGame(GameController gameController) {
        super(gameController);

        setPreferredSize(new Dimension(properties.mediumWIDTH,properties.mediumHEGIHT));


        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                boolean clicked = false;
                for(int i = 0;i<properties.buttonNumLog;i++){
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
                for (int i = 0; i < properties.buttonNumLog; i++) {
                    if (options[i].intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
                        currentSelection = i + 1;
                        options[i].setSelected(true);

                    } else
                        options[i].setSelected(false);

                }
                repaint();
            }
            });

        initEndWindow();
    }

    /***
     * Sets the game state according to the clicked button.
     * Closes the window.
     */
    @Override
    protected void select() {
            switch (currentSelection) {
                case 1:
                    frame.setVisible(false);
                    frame.dispose();
                    gameController.changeState(STATE.MENU);
                    break;
                case 2:
                    frame.setVisible(false);
                    frame.dispose();
                    gameController.changeState(STATE.GAME);
                    break;
                default:
                    break;
            }
    }

    /***
     * Paints the component.
     * <p>
     *     Draws the background, title, buttons and end game information.
     * </p>
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension size = getSize();

        paintBackground(g,size);

        printTitle(g);
        drawButtons(g,size);
        drawInfo(g);
    }

    /***
     * Paints the background.
     *
     * @param g Graphics
     * @param size dimension of the window
     */
    protected void paintBackground(Graphics g,Dimension size){
        g.setColor(properties.shopBgColor);
        g.fillRect(0,0,size.width,size.height);
    }


    private void printTitle(Graphics g){
        if(win){
            g.setColor(properties.selectedColor);
            drawCenterString(properties.winString,properties.winStringY,g,properties.buttonFont);
        }
        else{
            g.setColor(Color.RED);
            drawCenterString(properties.loseString,properties.winStringY,g,properties.buttonFont);
        }
    }


    private void drawButtons(Graphics g, Dimension size){
            options[0].drawGoldButton(g,size,1);
            options[1].drawGoldButton(g,size,3);
    }


    private void drawInfo(Graphics g){
        g.setColor(properties.ComponentColor);
        g.setFont(properties.textFont);
        drawCenterString(properties.endString0 + winnerInfo[0],properties.endString0Y,g,properties.textFont);
        drawCenterString(properties.endString1 + (Integer.parseInt(winnerInfo[1])-1) ,properties.endString1Y,g,properties.textFont);
        drawCenterString(properties.endString2 + winnerInfo[2],properties.endString2Y,g,properties.textFont);
        drawCenterString(properties.endString3 + winnerInfo[3],properties.endString3Y,g,properties.textFont);
        if(Integer.parseInt(winnerInfo[4]) <= 0){
            g.setColor(Color.RED);
            drawCenterString(properties.outOfFuelString,properties.endString3Y+20,g,properties.textFont);
        }

        else if(Float.parseFloat(winnerInfo[5]) > properties.velCond
                && Float.parseFloat(winnerInfo[6]) > properties.velCond
                && Float.parseFloat(winnerInfo[7]) > 0
                && Boolean.parseBoolean(winnerInfo[8])) {
            g.setColor(Color.RED);
            drawCenterString(properties.tooFastYString + winnerInfo[6] + properties.velXString + winnerInfo[5], properties.endString3Y + properties.endStringGap, g, properties.textFont);
        }
        else if(Float.parseFloat(winnerInfo[5]) > properties.velCond
                && Float.parseFloat(winnerInfo[7]) > 0
                && Boolean.parseBoolean(winnerInfo[8])){
            g.setColor(Color.RED);
            drawCenterString(properties.tooFastXString +winnerInfo[5],properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }
        else if(Float.parseFloat(winnerInfo[6]) > properties.velCond
                && Float.parseFloat(winnerInfo[7]) > 0
                && Boolean.parseBoolean(winnerInfo[8])){
            g.setColor(Color.RED);
            drawCenterString(properties.tooFastYString +winnerInfo[6],properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }
        else if(Float.parseFloat(winnerInfo[7]) <= 0){
            g.setColor(Color.RED);
            drawCenterString(properties.outOfTimeString,properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }else if(Boolean.parseBoolean(winnerInfo[9])){
            g.setColor(Color.RED);
            drawCenterString(properties.inSpaceString,properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }

        else if(Boolean.parseBoolean(winnerInfo[10])){
            g.setColor(Color.RED);
            drawCenterString(properties.meteorString,properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }else if(!Boolean.parseBoolean(winnerInfo[8])){
            g.setColor(Color.RED);
            drawCenterString(properties.mountainCollisionString,properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }
        else if(win){
            g.setColor(Color.green);
            drawCenterString(properties.nextLevelString +winnerInfo[1],properties.endString3Y+properties.endStringGap,g,properties.textFont);
        }
        else
            return;

    }

    /***
     * Creates the buttons according to the result of the ended game and
     * sets the frame visible.
     */
    public void showWindow(){
        if(win) {
            options = new Button[2];
            options[0] = new Button(properties.Backbutton, properties.endGameButtonY, properties.smallButtonFont, properties.fontColor, properties.selectedColor, properties.smallButtonWidth, properties.smallButtonHeight);
            options[1] = new Button(properties.tryAgainButton, properties.endGameButtonY, properties.smallButtonFont, properties.fontColor, properties.selectedColor, properties.smallButtonWidth, properties.smallButtonHeight);
        }
        else{
            options = new Button[2];
            options[0] = new Button(properties.Backbutton, properties.endGameButtonY, properties.smallButtonFont, properties.fontColor, properties.selectedColor, properties.smallButtonWidth, properties.smallButtonHeight);
            options[1] = new Button(properties.tryAgainButton, properties.endGameButtonY, properties.smallButtonFont, properties.fontColor, properties.selectedColor, properties.smallButtonWidth, properties.smallButtonHeight);
        }
        frame.setVisible(true);
    }

    /***
     * Initializes the window frame and sets WindowListener, location.
     */
    public void initEndWindow(){
        frame = new JFrame(properties.endGameTitle);

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ((Component) e.getSource()).setVisible(false);
                ((Window) e.getSource()).dispose();
                gameController.changeState(STATE.MENU);
            }
        };


        frame.add(this);
        frame.setResizable(false);
        frame.addWindowListener(l);
        frame.pack();
        frame.requestFocus();
        frame.setLocationRelativeTo(null);

    }

    /***
     * Loads the information about the game gme result form the player.
     *
     * @param player the currant player to load the information
     *               about ended game
     */
    public void loadWinnerInfo(Player player){
        winnerInfo = new String[11];
        winnerInfo[0] = player.getLogin();
        winnerInfo[1] = String.valueOf(player.getLevel());
        winnerInfo[2] = String.valueOf(player.getScoreLevel());
        winnerInfo[3] = String.valueOf(player.getLunars());
        winnerInfo[4] = String.valueOf(player.getFuel());
        winnerInfo[5] = String.valueOf(player.getVelX());
        winnerInfo[6] = String.valueOf(player.getVelY());
        winnerInfo[7] = String.valueOf(player.getFlyTime());
        winnerInfo[8] = String.valueOf(player.getPlainLanding());
        winnerInfo[9] = String.valueOf(player.getInSpace());
        winnerInfo[10] = String.valueOf(player.getMeteorCollision());

    }

    /***
     * Sets the variable win to true (the player won) or to false
     * (the player lost).
     *
     * @param win if true, player won. if false, player lost the game
     */
    public void setWin(boolean win){ this.win = win; }

    /**
     * EndGame loop
     */
    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
