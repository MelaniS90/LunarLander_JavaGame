package LunarLander;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;


/***
 * This is the board of the Lunar Lander Game
 */
public class Board extends JPanel {

    private PropertiesManager properties = PropertiesManager.getInstance();
    private FontManager fonts = FontManager.getInstance();
    private FileManager fileManager = FileManager.getInstance();
    private GameController gameController;

    private GamePanel gamePanel;
    private Player player;
    private Meteor meteor;
    private Lunar lunar;

    private int prevHEIGHT = properties.HEIGHT;
    private int prevWIDTH = properties.WIDTH;

    /***
     * Constructor of the class.
     * <p>
     *     Sets preferred size of the component and creates Player and GamePanel objects.
     * </p>
     *
     * @param gameController object of the GameController class passed as an parameter to
     *                       set a inner object
     */
    public Board(GameController gameController) {
        this.gameController = gameController;
        setPreferredSize(new Dimension(properties.WIDTH, properties.HEIGHT));

        player = new Player(gameController);
        gamePanel = new GamePanel(gameController);

    }

    /***
     * Paints the component.
     * <p>
     *     Paints background, player, game panel and terrain.
     * </p>
     *
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Dimension size = getSize();

        paintBackground(g2d, size);

        if(lunar != null)
            lunar.draw(g);

        if(properties.numOfMeteors != 0)
            meteor.drawMeteor(g);

        player.drawPlayer(g);
        gamePanel.drawGamePanel(g2d, player,size.width,size.height);
        drawLines(g2d);

        if(properties.GameState == STATE.PAUSE){
            drawPauseInfo(g,size.width,size.height);
        }

    }

    /***
     * Updated player
     */
    public void update(Dimension size) {
        updateBoardSize(size);

        player.updatePlayer();

        if(meteor!=null)
            meteor.update(size.getWidth(),size.getHeight());


        meteorCollision();
        meteorMeetsGround();
        lunarCollision();
    }

    public void updateBoardSize(Dimension size){
        if(prevHEIGHT != size.getHeight() || prevWIDTH != size.getWidth()) {
            updateLines(size);
            player.updateSize(prevWIDTH, prevHEIGHT, size.getWidth(), size.getHeight());

            if(meteor != null)
                meteor.resize(size.getWidth(),size.getHeight(),prevWIDTH,prevHEIGHT);
            if(lunar != null)
                lunar.updateSize(size.getWidth(),size.getHeight(),prevWIDTH,prevHEIGHT);


            setPrevSize(size);
        }
        player.updateRocket();

    }

    /***
     * Draw resized player according to the dimension of the window
     *
     * @param g Graphics
     */
    void updateDraw(Graphics g) {
        player.drawPlayer(g);
    }

    /***
     * Paints background.
     *
     * @param g Graphics
     * @param size Dimension of the component
     */
    private void paintBackground(Graphics g, Dimension size) {

        g.setColor(properties.backgroundColor);
        g.fillRect(0, 0, size.width, size.height);
    }

    /***
     * Implements action after pressing game keys
     *
     * @param keyEvent
     */
    public void keyPressed(KeyEvent keyEvent) {

        if (properties.GameState != STATE.SHOP && properties.GameState != STATE.PAUSE) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                fileManager.writePlayer(player);
                gameController.changeState(STATE.MENU);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_X) {
                gameController.shop.loadPlayerInfo(player);
                gameController.changeState(STATE.SHOP);
                gameController.shop.initWindow();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP || keyEvent.getKeyCode() == KeyEvent.VK_W) {
                player.move((float) 6);
                player.setUsingEngine(true);
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT || keyEvent.getKeyCode() == KeyEvent.VK_D){
                player.addRotation(5);
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT || keyEvent.getKeyCode() == KeyEvent.VK_A){
                player.addRotation(-5);
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_P){
                gameController.changeState(STATE.PAUSE);
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_B){
                if(Player.addFuel > 0){
                    Player.addFuel -= 1;
                    player.add10Fuel();
                }
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_N){
                if(Player.fullFuel > 0){
                    Player.fullFuel -= 1;
                    player.setFullFuel();
                }
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_M){
                if(Player.suddenStop > 0){
                    Player.suddenStop -= 1;
                    player.setVelY(0);
                    player.setVelX(0);
                    
                }
            }
        }
        if(properties.GameState == STATE.PAUSE ){
            if(keyEvent.getKeyCode() == keyEvent.VK_G)
                gameController.changeState(STATE.GAME);
        }

    }

    public void keyTyped(KeyEvent keyEvent){
        if(properties.GameState == STATE.GAME) {

            if (keyEvent.getKeyCode() == KeyEvent.VK_B) {
                if (Player.addFuel > 0) {
                    Player.addFuel -= 1;
                    player.add10Fuel();
                }
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_N) {
                if (Player.fullFuel > 0) {
                    Player.fullFuel -= 1;
                    player.setFullFuel();
                }
            }

            if (keyEvent.getKeyCode() == KeyEvent.VK_M) {
                if (Player.suddenStop > 0) {
                    Player.suddenStop -= 1;
                    player.setVelY(0);
                    player.setVelX(0);

                }
            }
        }
    }

    /***
     * Implements action after releasing game keys
     *
     * @param keyEvent
     */
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != KeyEvent.VK_UP || keyEvent.getKeyCode() != KeyEvent.VK_W) {
            player.setPowerY(0);
            player.setPowerX(0);
            player.setEngineTimerto0();

            player.calculateDistance();
            player.calculatefallTime();

            player.setUsingEngine(false);
        }
    }

    /***
     * Draws terrain of the current level
     *
     * @param g Graphics
     */
    private void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < properties.terrain.size(); i++) {
            g2d.draw(properties.terrain.get(i));
        }
    }

    /***
     * Updates terrain after resizing the window
     *
     * @param size dimension of the window
     */
    public void updateLines(Dimension size) {

        for (int i = 0; i < properties.terrain.size(); i++) {
            float x1 = properties.terrain.elementAt(i).x1 * ((float)size.getWidth() / prevWIDTH );
            float y1 = properties.terrain.elementAt(i).y1 * ((float)size.getHeight() / prevHEIGHT);
            float x2 = properties.terrain.elementAt(i).x2 * ((float)size.getWidth() / prevWIDTH );
            float y2 = properties.terrain.elementAt(i).y2 * ((float)size.getHeight() / prevHEIGHT);

            properties.terrain.setElementAt(new Line2D.Float(x1,y1,x2,y2),i);
        }

    }

    private void drawPauseInfo(Graphics g,float width,float height){
        Font font = properties.titleFont.deriveFont((float) (40 * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));
        Font font1 = properties.titleFont.deriveFont((float) (10 * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));

        g.setFont(font);
        g.setColor(Color.BLUE);
        fonts.drawCenterString("PAUZA",getHeight()/2,g,font,(int)width,0);
        fonts.drawCenterString("[ G ] - powrot do gry",getHeight()/4 *3,g,font1,(int)width,0);
    }


    private void setPrevSize(Dimension size){
        prevWIDTH = (int) size.getWidth();
        prevHEIGHT = (int) size.getHeight();
    }


    /***
     * Sets values of the board to the initial
     */
    public synchronized void initBoard() {

        player.setPlayer(LogingIn.input);
        properties.loadLevel(player.getLevel());
        player.initValues();
        prevWIDTH = properties.WIDTH;
        prevHEIGHT = properties.HEIGHT;
        createMeteors();
        lunar = new Lunar();
    }

    private void createMeteors(){
        meteor = new Meteor(properties.meteorX,properties.meteorY,10,10,Color.YELLOW);
    }

    private void meteorCollision(){
        if(meteor != null) {
            if (player.getBounds().intersects(meteor.getBounds())) {
                player.setMeteorCollision(true);
            }
        }
    }

    private  void meteorMeetsGround(){
        if(meteor != null) {
            for (int i = 0; i < properties.terrain.size(); i++) {
                if (properties.terrain.get(i).intersects(meteor.getBounds())) {
                    meteor = new Meteor(0,0,0,0,Color.YELLOW);
                }
            }
        }
    }

    private void lunarCollision(){
        if(lunar != null){
            for(int i=0; i<lunar.lunars.length;i++) {
                if (player.getBounds().intersects(lunar.lunars[i])){
                    lunar.lunars[i].x = 0;
                    lunar.lunars[i].y = 0;
                    lunar.lunars[i].width = 0;
                    lunar.lunars[i].height =0;
                    player.addLunars(10);
                }
            }
        }
    }


}


