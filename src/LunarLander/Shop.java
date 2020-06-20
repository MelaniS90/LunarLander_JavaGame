package LunarLander;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 * This is a shop window in which the player can buy some bonus options. It is
 * shown along with the main window.
 */
public class Shop extends abstractMenu implements Runnable{

    private Button[] buyButtons;
    private Button okButton;

    private String[] playerInfo;

    private JFrame shopFrame;

    private int currentSelection;

    Thread thread;

    /***
     * Constructor of the class.
     * <p>
     *     Sets preferred size of the component. Initializes buttons nad implements MouseListener.
     * </p>
     *
     * @param gameController object of the GameController class for the constructor of
     *                       the inherited class
     */
    public Shop(GameController gameController) {
        super(gameController);

        setPreferredSize(new Dimension(properties.shopWidth,properties.shopHeight));

        okButton = new Button(properties.OKbutton,properties.okButtonY,properties.smallButtonFont,properties.ComponentColor,
                properties.selectedColor,properties.smallButtonWidth,properties.smallButtonHeight);

        buyButtons = new Button[3];
        buyButtons[0] = new Button(properties.buyButtonString,properties.buyButtonY0,properties.buttonFont,properties.selectedColor,
                properties.shopNotSelecColor,properties.buttonWidth,properties.buttonHeight);
        buyButtons[1] = new Button(properties.buyButtonString,properties.buyButtonY1,properties.buttonFont,properties.selectedColor,
                properties.shopNotSelecColor,properties.buttonWidth,properties.buttonHeight);
        buyButtons[2] = new Button(properties.buyButtonString,properties.buyButtonY2,properties.buttonFont,properties.selectedColor,
                properties.shopNotSelecColor,properties.buttonWidth,properties.buttonHeight);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                boolean clicked = false;
                if(okButton.intersects(new Rectangle(e.getX(),e.getY(),1,1))){
                    clicked = true;
                }

                for(int i=0; i<buyButtons.length; i++){
                    if(buyButtons[i].intersects(new Rectangle(e.getX(),e.getY(),1,1))){
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

                if (okButton.intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
                    okButton.setSelected(true);
                    currentSelection = 4;
                } else {
                    okButton.setSelected(false);
                }

                for(int i=0; i<buyButtons.length;i++){
                    if(buyButtons[i].intersects(new Rectangle(e.getX(),e.getY(),1,1))){
                        buyButtons[i].setSelected(true);
                        currentSelection = i + 1;
                    }
                    else{
                        buyButtons[i].setSelected(false);
                    }
                }

                repaint();
            }
        });
    }

    /***
     * Paints the component (informations, options, conditions and buttons).
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawInfo(g);
        drawOptions(g);
        drawConditions(g);
        drawButtons(g,new Dimension());
    }

    /***
     * Paints the background of the component.
     *
     * @param g Graphics
     */
    protected void paintBackground(Graphics g){
        Dimension size = getSize();

        g.setColor(properties.shopBgColor);
        g.fillRect(0,0,size.width,size.height);

    }


    private void drawInfo(Graphics g){
        g.setColor(properties.ComponentColor);
        fonts.drawCenterString(properties.shopTitleString,properties.shopTitleY,g,properties.buttonFont,properties.shopWidth,0);

        g.setColor(properties.ComponentColor);
        fonts.drawCenterString(properties.shopLoginString + playerInfo[0]+"   " +properties.shopLevelString+playerInfo[1]+ "   " +properties.shopLunarString+ playerInfo[2],properties.shopInfoY,g,properties.textFont,properties.shopWidth,0);
    }


    private void drawOptions(Graphics g){
        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond1 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond1){
            g.setColor(properties.selectedColor);
        }
        else
            g.setColor(properties.shopNotSelecColor);
        g.setFont(properties.shopFontSize);
        g.drawString(properties.bonusString1,properties.shopBonusStringX,properties.shopBonusStringY);

        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond2 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond2){
            g.setColor(properties.selectedColor);
        }
        else
            g.setColor(properties.shopNotSelecColor);
        g.drawString(properties.bonusString2,properties.shopBonusStringX,properties.shopBonusStringY * 2);

        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond3 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond3){
            g.setColor(properties.selectedColor);
        }
        else
            g.setColor(properties.shopNotSelecColor);
        g.drawString(properties.bonusString3,properties.shopBonusStringX,properties.shopBonusStringY * 3);
    }


    private void drawConditions(Graphics g){
        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond1 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond1){
            g.setColor(properties.shopSelCondColor);
        }
        else
            g.setColor(properties.shopNotSelCondColor);
        g.setFont(properties.textFont);
        g.drawString(properties.bonusInfo1,properties.shopBonusStringX,properties.shopBonusStringY + properties.bonusInfoY);

        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond2 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond2){
            g.setColor(properties.shopSelCondColor);
        }
        else
            g.setColor(properties.shopNotSelCondColor);
        g.setFont(properties.textFont);
        g.drawString(properties.bonusInfo2,properties.shopBonusStringX,properties.shopBonusStringY * 2 + properties.bonusInfoY);

        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond3 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond3){
            g.setColor(properties.shopSelCondColor);
        }
        else
            g.setColor(properties.shopNotSelCondColor);
        g.setFont(properties.textFont);
        g.drawString(properties.bonusInfo3,properties.shopBonusStringX,properties.shopBonusStringY * 3 + properties.bonusInfoY);
    }


    private void drawButtons(Graphics g, Dimension size){
        okButton.drawButton(g,properties.okButtonX);

        buyButtons[0].drawButton(g,properties.buyButtonX);
        buyButtons[1].drawButton(g,properties.buyButtonX);
        buyButtons[2].drawButton(g,properties.buyButtonX);
    }


    private void update(){
        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond1 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond1) {
            buyButtons[0].setColor(properties.ComponentColor);
            buyButtons[0].setSelectedColor(properties.selectedColor);
        }
        else {
            buyButtons[0].setColor(properties.shopNotSelecColor);
            buyButtons[0].setSelectedColor(properties.shopNotSelecColor);
        }

        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond2 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond2){
            buyButtons[1].setColor(properties.ComponentColor);
            buyButtons[1].setSelectedColor(properties.selectedColor);
        }
        else {
            buyButtons[1].setColor(properties.shopNotSelecColor);
            buyButtons[1].setSelectedColor(properties.shopNotSelecColor);
        }

        if(Integer.parseInt(playerInfo[1]) >= properties.levelCond3 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond3){
            buyButtons[2].setColor(properties.ComponentColor);
            buyButtons[2].setSelectedColor(properties.selectedColor);
        }
        else {
            buyButtons[2].setColor(properties.shopNotSelecColor);
            buyButtons[2].setSelectedColor(properties.shopNotSelecColor);
        }
    }

    /***
     * Changes the state of the game according to the clicked button.
     */
    @Override
    protected void select() {

        switch(currentSelection) {
            case 1:
                if(Integer.parseInt(playerInfo[1]) >= properties.levelCond1 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond1){
                    Player.addFuel += 1;
                    Player.lunars -=10;
                    playerInfo[2] = String.valueOf(Player.lunars);
                }
                break;
            case 2:
                if(Integer.parseInt(playerInfo[1]) >= properties.levelCond2 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond2){
                    Player.fullFuel += 1;
                    Player.lunars -=40;
                    playerInfo[2] = String.valueOf(Player.lunars);
                }
                break;
            case 3:
                if(Integer.parseInt(playerInfo[1]) >= properties.levelCond3 && Integer.parseInt(playerInfo[2]) >= properties.lunarCond3){
                    Player.suddenStop += 1;
                    Player.lunars -=60;
                    playerInfo[2] = String.valueOf(Player.lunars);
                }
                break;
            case 4:
                gameController.changeState(STATE.GAME);
                shopFrame.dispose();
                break;
            default:
                break;
        }

    }

    /***
     * Creates the frame of the window, adds WindowListener and sets the window
     * location and visibility. Starts shop thread.
     */
    public void initWindow(){
        shopFrame = new JFrame(properties.shopTitle);

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ((Component) e.getSource()).setVisible(false);
                ((Window) e.getSource()).dispose();
                gameController.changeState(STATE.GAME);
            }
        };

        shopFrame.add(this);

        shopFrame.setResizable(false);
        shopFrame.addWindowListener(l);
        shopFrame.pack();
        shopFrame.requestFocus();
        shopFrame.setLocationRelativeTo(null);
        shopFrame.setVisible(true);

        (thread = new Thread(this)).start();
    }

    public void loadPlayerInfo(Player player){
        playerInfo = new String[]{player.getLogin(), String.valueOf(player.getLevel()),String.valueOf(player.getLunars())};
    }


    /***
     * Shop loop
     */
    @Override
    public void run() {
        update();
        repaint();
        sleeep();
    }

    /***
     * Delay of a shop loop
     */
    private void sleeep(){
        try{
            thread.sleep(20);
        }catch(InterruptedException e){
            System.out.println("przerwanie sleep");
        }
    }

}
