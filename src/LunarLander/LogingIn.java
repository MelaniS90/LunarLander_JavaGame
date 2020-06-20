package LunarLander;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 *  This is the login window shown along with the main window.
 */
public class LogingIn extends abstractMenu implements Runnable{

    private Button[] options;
    private JTextField jf = new JTextField(50);
    private JFrame frame;

    public static String input;

    private int currentSelection;

    public Thread thread;

    /***
     * Constructor of the class.
     * <p>
     *     Sets preferred size of the component, creates buttons and textfield,
     *     adds and implements MouseListener and MouseMotionListener.
     * </p>
     *
     * @param gameController object of the class GameController for the constructor of
     *                       a inherited class
     */
    public LogingIn(GameController gameController) {
        super(gameController);

        setPreferredSize(new Dimension(properties.loginWidth,properties.loginHeight));

        options = new Button[properties.buttonNumLog];
        options[0] = new Button(properties.Backbutton,properties.buttonLogY,properties.smallButtonFont,properties.fontColor,properties.selectedColor,properties.smallButtonWidth,properties.smallButtonHeight);
        options[1] = new Button(properties.OKbutton,properties.buttonLogY,properties.smallButtonFont,properties.fontColor,properties.selectedColor,properties.smallButtonWidth,properties.smallButtonHeight);


        setLayout(null);
        TextField();


        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                    boolean clicked = false;
                    for(int i = 0;i<properties.buttonNumLog;i++){
                        if(options[i].intersects(new Rectangle(e.getX(),e.getY(),1,1))){
                            clicked = true;
                            input = jf.getText();
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




    }

    /***
     * Changes the game state according to the clicked button and
     * closes the window.
     */
    @Override
    protected void select() {

        switch(currentSelection){
            case 1:
                gameController.changeState(STATE.MENU);
                frame.dispose();
                break;
            case 2:
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
        drawCenterString(properties.LoginTitle,properties.TitleY,g,properties.buttonFont);

        drawButtons(g,size);
    }

    /***
     * Paints background.
     *
     * @param g Graphics
     */
    protected void paintBackground(Graphics g){
        Dimension size = getSize();

        g.setColor(properties.loginBgColor);
        g.fillRect(0,0,size.width,size.height);
    }


    private void drawButtons(Graphics g, Dimension size){
        options[0].drawGoldButton(g,size,1);
        options[1].drawGoldButton(g,size,3);
    }



    private void TextField(){
        Dimension size = getSize();

        jf.setSize(properties.textWidth,properties.textHeight);
        jf.setLocation((properties.loginWidth-properties.textWidth)/2,(properties.loginHeight-properties.textHeight)/2);
        jf.setFont(properties.textFont);
        add(jf);
    }

    /***
     * Creates frame of the window, adds WindowListener and sets location and visibility of the frame.
     */
    public void initLoginWindow(){
        frame = new JFrame("Login");

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
        frame.setVisible(true);
    }

    /***
     * Login loop
     */
    @Override
    public void run() {
        while(properties.GameState == STATE.LOGING){
            repaint();
            sleeep();
        }
    }


    private void sleeep(){
        try{
            thread.sleep(15);
        }catch(InterruptedException e){
            System.out.println("przerwanie sleep");
        }
    }
}
