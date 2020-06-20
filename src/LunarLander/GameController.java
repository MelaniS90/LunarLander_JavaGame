package LunarLander;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 * This the controller of the game and a main frame. Creates almost every object
 * of the game and controls which one to display.
 */


public class GameController extends JFrame implements Runnable{

    private PropertiesManager properties = PropertiesManager.getInstance();
    private KeyInput keyInput;

    private MainMenu mainMenu;
    private HelpMenu helpMenu;
    private Board board;
    public Shop shop;
    public LogingIn logingIn;
    public Ranking ranking;
    public EndGame endGame;

    public Thread thread;

    /***
     * Class constructor which creates objects such as: MainMenu, HelpMenu,
     * Shop, LogingIn, Ranking, EndGame, Board, KeyInput, but also initialize
     * the game and the frame.
     */
    public GameController(){

        mainMenu = new MainMenu(this);
        helpMenu = new HelpMenu(this);

        shop = new Shop(this);
        logingIn = new LogingIn(this);
        ranking = new Ranking(this);
        endGame = new EndGame(this);


        board =    new Board(this);
        keyInput = new KeyInput(board);

        thread = new Thread(this);

        initGame();

        initFrame();
    }


    /***
     * Adds first component to the frame
     */
    public void initGame(){
        addComponent();
    }

    /***
     * Changes state of the game. Previous state becomes the current state
     * and current state becomes the new state which is a parameter in the method.
     * Next, the old component is being removed and the new component is being added
     * to the frame
     *
     * @param state new state of the game
     */
    public void changeState(STATE state){
        if(properties.GameState != state) {
            properties.prevState = properties.GameState;
            properties.GameState = state;

            removeComponent();
            addComponent();
        }
    }


    private void addComponent(){
        switch(properties.GameState){
            case MENU:
                this.add(mainMenu);
                revalidate();
                setResizable(false);
                break;
            case HELP:
                this.add(helpMenu);
                repaint();
                revalidate();
                setResizable(false);
                break;
            case GAME:
                if(properties.prevState != STATE.PAUSE && properties.prevState != STATE.SHOP)
                    board.initBoard();
                this.add(board);
                addKeyListener(keyInput);
                repaint();
                revalidate();
                setResizable(true);
                break;
            case RANKING:
                this.add(ranking);
                repaint();
                revalidate();
                setResizable(false);
                break;
            case END:
                endGame.showWindow();
                (endGame.thread = new Thread(endGame)).start();
                break;
            case LOGING:
                (logingIn.thread = new Thread(logingIn)).start();
                break;
            case PAUSE:
                break;
            default:
                break;
        }
    }

    private void removeComponent(){
        switch(properties.prevState){
            case MENU:
                if(properties.GameState != STATE.LOGING) {
                    remove(mainMenu);
                }
                break;
            case HELP:
                remove(helpMenu);
                break;
            case GAME:
                if(properties.GameState != STATE.END && properties.GameState != STATE.SHOP && properties.GameState != STATE.PAUSE) {
                    remove(board);
                    removeKeyListener(keyInput);
                    setSize(new Dimension(properties.WIDTH,properties.HEIGHT));
                }

                break;
            case RANKING:
                remove(ranking);
                break;
            case END:
                endGame.thread = null;
                remove(board);
                setSize(new Dimension(properties.WIDTH,properties.HEIGHT));
                setLocationRelativeTo(null);
                break;
            case LOGING:
                remove(mainMenu);
                logingIn.thread = null;
                break;
            case PAUSE:
                break;
            default:
                break;
        }
    }


    private void initFrame(){
        setTitle(properties.NAME);
        setPreferredSize(new Dimension(properties.WIDTH,properties.HEIGHT));

        WindowListener l = new WindowAdapter() {
                public void windowIconified (WindowEvent e){
                    if(properties.GameState == STATE.MENU) {
                        thread = null;
                    }
                }
                public void windowDeiconified (WindowEvent e){
                    if(properties.GameState == STATE.MENU) {
                        (thread = new Thread()).start();
                    }

                }
                public void windowClosing (WindowEvent e){
                    dispose();
                    if(properties.GameState == STATE.GAME) {
                        thread = null;
                    }
                }
                public void windowClosed (WindowEvent e){
                    System.exit(1);
                }
        };
        addWindowListener(l);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent ce) {
            }
        });
        pack();
        setFocusable(true);
        setLocationRelativeTo(null);
    }


    @Override
    public void run() {
        while(true) {
            if(properties.GameState == STATE.GAME)
                board.update(getSize());
            else if(properties.GameState == STATE.PAUSE) {
                board.updateBoardSize(getSize());
            }
            repaint();
            sleeep();
        }
    }

    private void sleeep(){
        try {
            thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
