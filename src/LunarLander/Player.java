package LunarLander;
import java.awt.*;
import java.awt.geom.Line2D;

/***
 * This is the player (a rocket) for the Lunar Lander game.
 */
public class Player{

    private PropertiesManager properties = PropertiesManager.getInstance();
    private FileManager fileManager = FileManager.getInstance();
    private GameController gameController;

    private String login;
    private int level;
    private int score;
    private int scoreLevel;
    private boolean plainLanding;
    private boolean inSpace;
    private boolean meteorCollision;

    private int fuel = properties.initFuel;
    public static int lunars;
    private float flyTime = properties.flyTime;

    private Polygon rocket;
    private double promien;
    private double alfa[];
    private double x[] = new double[4];
    private double y[] = new double[4];

    private float playerX,playerY;
    private float velX, velY;
    private float powerY = properties.initPowerY;
    private float powerX = properties.initPowerX;
    private float rotate = properties.initRotate;
    private float gravity;
    private float velocity;
    private float fallTime;
    private float distance;
    private float engineTimer = properties.initEngineTime;

    private boolean usingEngine = false;

    /*Bonusy*/
    public static int addFuel;
    public static int fullFuel;
    public static int suddenStop;


    /***
     * Constructor of the class.
     * <p>
     *     Sets an inner object as a parameter and sets init values.
     * </p>
     *
     * @param gameController object of the GameController class
     */
    public Player(GameController gameController){
        this.gameController = gameController;
        setInitRocket();
    }


    /***
     * Draw player.
     *
     * @param g Graphics
     */
    public void drawPlayer(Graphics g){

        g.setColor(properties.ComponentColor);
        g.drawPolygon(rocket);

        if(usingEngine)
            g.setColor(Color.red);

        else
            g.setColor(properties.ComponentColor);
        g.drawLine((int)x[1],(int)y[1],(int)x[0],(int)y[0]);
        g.drawLine((int)x[1],(int)y[1]+1,(int)x[0],(int)y[0]+1);
    }


    /***
     * Update values of the variables of the player. Sets the speed of the rocket.
     * Checks collisions, fuel and if the rocket is in space.
     */
    public void updatePlayer(){
        collision();
        meteor();
        outOfFuel();
        outOfTime();
        inSpace();

        if (!usingEngine){
            fallTime += properties.addFallTime;
        }

        playerY = playerY + velY * fallTime;
        velY  =  velY + gravity * fallTime - powerY;

        playerX = playerX + velX * fallTime;
        if(usingEngine)
            velX = velX + powerX;
        else if((velX < 0 && velX > (-0.001)) || (velX > 0 && velX < 0.001))
            velX = properties.initVelX;
        else{
            velX /= properties.dragX ;
        }

        flyTime -= properties.addFlyTime;

        updateRocket();
    }


    /**
     * Updates size of the rocket according to window.
     *
     * @param prevWidth width of the window before resizing
     * @param prevHeight height of the window before resizing
     * @param width width of the window after resizing
     * @param height height of the window after resizing
     */
    public void updateSize(int prevWidth, int prevHeight,double width, double height){
        promien = Math.sqrt(Math.pow(properties.playerWIDTH * (width/properties.WIDTH)/2,2)
                + Math.pow(properties.playerHEIGHT*(height/properties.HEIGHT)/2,2));

        playerX = (float) (playerX * (width/prevWidth));
        playerY = (float) (playerY * (height/prevHeight));

    }

    /***
     * Sets the power added to the rocket according to the parameter
     *
     * @param acceleration acceleration added to the rocket
     */
    public void move(float acceleration){
        engineTimer+=properties.addEngineTime;
        velocity = acceleration * engineTimer;

        powerY = (float)(velocity * Math.cos(Math.toRadians(rotate)));
        powerX = (float)(velocity * Math.sin(Math.toRadians(rotate)));
        fuel -= properties.addFuel;

    }

    /***
     * Sets fall time according to the location of the rocket
     */
    public void calculatefallTime(){
        fallTime = (float) Math.sqrt(2*distance/gravity);
    }

    /***
     * Calculates the distance between the rocket and the terrain
     */
    public void calculateDistance(){
        float y = 0;

        for(int i=0; i<properties.terrain.size();i++){
            if(properties.terrain.get(i).x1 <= playerX && properties.terrain.get(i).x2 >= playerX){
                y = (properties.terrain.get(i).y2 + properties.terrain.get(i).y1)/2; //uproszczenie
            }
        }

        distance = (properties.HEIGHT - (y - playerY))/properties.scaleDistance; //przeskalowanie
    }

    /***
     * Sets the angle of the rocket
     *
     * @param angle angle added to the rocket in degrees
     */
    public void addRotation(float angle){
        rotate += angle;
        updateRotation(angle);
    }


    private void collision(){
        if(properties.GameState == STATE.GAME) {
            for (int i = 0; i < properties.terrain.size(); i++) {
                Line2D.Float line = properties.terrain.get(i);

                if (line.intersectsLine(x[0],y[0],x[1],y[1])
                        || line.intersectsLine(x[1],y[1],x[2],y[2])
                        || line.intersectsLine(x[2],y[2],x[3],y[3])
                        || line.intersectsLine(x[3],y[3],x[0],y[0])
                ) {
                    gravity = 0;


                    if(Math.abs(velY) < properties.velCond && Math.abs(velX) < properties.velCond){
                        calculateScore();
                        if(level != 7)
                            upgardeLevel();
                        gameController.endGame.setWin(true);
                    }
                    else
                        gameController.endGame.setWin(false);

                    if(i == properties.landing1 || i == properties.landing2)
                        setPlainLanding(true);

                    gameOver();
                }
            }
        }
    }


    private void outOfFuel(){
        if(properties.GameState == STATE.GAME){
            if(fuel <= 0){
                gameController.endGame.setWin(false);
                gameOver();
            }
        }
    }

    private void outOfTime(){
        if(properties.GameState == STATE.GAME){
            if(flyTime <= 0 ){
                gameController.endGame.setWin(false);
                gameOver();
            }
        }
    }

    private void inSpace(){
        if(properties.GameState == STATE.GAME){
            if(Math.abs(playerX) > properties.spaceX || playerY < properties.spaceY1 || playerY > properties.spaceY2){
                inSpace = true;
                gameController.endGame.setWin(false);
                gameOver();
            }
        }
    }

    private  void meteor(){
        if(properties.GameState == STATE.GAME){
            if(meteorCollision){
                gameController.endGame.setWin(false);
                gameOver();
            }
        }
    }

    private void gameOver(){
        gameController.changeState(STATE.END);
        gameController.endGame.loadWinnerInfo(this);
        fileManager.writePlayer(this);
    }

    private void calculateScore(){
        scoreLevel = (int) (flyTime + (properties.velCond - velX) + (properties.velCond - velY) + fuel * (properties.initFuel/properties.scaleFuel) + lunars) * level;
        score +=scoreLevel;
    }

    private void upgardeLevel(){
        level += 1;
    }

    /***
     * Sets the player by the login (name)
     *
     * @param login name of the player
     */
    public void setPlayer(String login){
        this.login = login;

        if(properties.isPlayerIn(login)){
            int[] data = properties.loadPlayer(login);
            level = data[0];
            score = data[1];
            lunars = data[2];
            addFuel = data[3];
            fullFuel = data[4];
            suddenStop = data[5];
        }else{
            level = properties.initLevel;
            score = properties.initScore;
            lunars = properties.initLunars;
            addFuel = properties.initBonus;
            fullFuel = properties.initBonus;
            suddenStop = properties.initBonus;
        }

    }

    /***
     * Sets initial values before the game starts
     */
    public void initValues(){
        setPlayerX((float)properties.WIDTH/2);
        setPlayerY(properties.playerStartY);
        setVelY(properties.initVelY);
        setVelX(properties.initVelX);
        setGravity(properties.gravity);
        setFuel(properties.initFuel);
        velocity = 0;
        setUsingEngine(false);
        setRotation(properties.initRotate);
        setEngineTimerto0();
        setPowerY(properties.initPowerY);
        setPowerX(properties.initPowerX);
        setFlyTime(properties.flyTime);
        setFallTime(0);
        setPlainLanding(false);
        setMeteorCollision(false);
        inSpace = false;
        setInitRocket();
        gameController.endGame.setWin(false);
    }

    private void setInitRocket(){
        promien = Math.sqrt(Math.pow(properties.playerHEIGHT/2,2) + Math.pow(properties.playerWIDTH/2,2));

        double alfa1 = Math.atan((double) properties.playerHEIGHT/ (double)properties.playerWIDTH);
        alfa = new double[]{alfa1, Math.PI - alfa1, Math.PI + alfa1, 2 * Math.PI - alfa1};

        updateRocket();
    }


    private void updatePoints(){
        for(int i=0; i<4; i++) {
            x[i] =  playerX + promien * Math.cos(alfa[i]);
            y[i] =  playerY + promien * Math.sin(alfa[i]);
        }

    }

    /**
     * Updates rocket after change of the posiotion of the rocket.
     */
    public void updateRocket(){
        updatePoints();
        int x_tab[] = new int[4];
        int y_tab[] = new int[4];
        for(int i=0; i<4; i++){
            x_tab[i] = (int) x[i];
            y_tab[i] = (int) y[i];
        }

        rocket = new Polygon(x_tab,y_tab,4);
    }

    private void updateRotation(float angle){
        for (int i=0; i<alfa.length; i++){
            alfa[i] += Math.toRadians(angle);
        }
    }

    /**
     * Adds 10% bonus fuel
     */
    public void add10Fuel(){
        fuel += properties.initFuel * properties.bonusFuel;

        if(fuel > properties.initFuel){
            fuel = properties.initFuel;
        }
    }

    /***
     * Adds a value to lunars.
     *
     * @param add value added
     */
    public void addLunars(int add){ lunars += add; }

    /***
     * Returns the sum of lunars of the player
     *
     * @return lunars
     */
    public int getLunars(){ return lunars; }

    /**
     * Sets horizonatal power of the rocket
     *
     * @param powerX horizontal power
     */
    public void setPowerX(float powerX){ this.powerX = powerX; }

    /**
     * Sets fuel as initial value
     */
    public void setFullFuel(){
        fuel = properties.initFuel;
    }

    /**
     * Returns true if the rocket is in collision with a meteor and false if otherwise.
     *
     * @return meteorCollision
     */
    public boolean getMeteorCollision(){ return meteorCollision; }

    /**
     *Sets true or false wheather the rocket is in collision with a meteor or not.
     *
     * @param meteorCollision
     */
    public void setMeteorCollision(boolean meteorCollision){ this.meteorCollision = meteorCollision; }

    /***
     * Returns true if the rocket is in space and false if otherwise.
     *
     * @return inSpace
     */
    public boolean getInSpace(){ return this.inSpace; }

    /***
     * Returns true if the rocket landed on a plain ground and false if otherwise.
     *
     * @return plainLanding
     */
    public boolean getPlainLanding(){ return plainLanding; }

    /***
     * Sets value of the game timer.
     *
     * @param fallTime
     */
    public void setFallTime(float fallTime){ this.fallTime = fallTime; }

    /***
     * Sets true or false wheather the rocket landed on plain ground or not.
     *
     * @param plainLanding
     */
    public void setPlainLanding(boolean plainLanding){ this.plainLanding = plainLanding; }


    /***
     * Returns the score reached in the current level
     *
     * @return score reached in this level
     */
    public int getScoreLevel(){ return scoreLevel; }

    /***
     * Returns the bounds of the rocket
     *
     * @return rectangle with the dimenion and location of the rocket
     */
    public Polygon getBounds(){return this.rocket; }

    /***
     * Returns the level of the player
     *
     * @return level
     */
    public int getLevel(){
        return this.level;
    }

    /***
     * Returns the score of the player
     *
     * @return score
     */
    public int getScore(){
        return this.score;
    }

    /***
     * Returns the location on X axis of the player.
     *
     * @return location of the player (on X axis)
     */
    public float getPlayerX() { return playerX; }

    /***
     * Sets the location on X axis of the player
     *
     * @param playerX locatin of the player on X axis
     */
    public void setPlayerX(float playerX) { this.playerX = playerX; }

    /***
     * Returns the location on Y axis of the player.
     *
     * @return location of the player on Y axis
     */
    public float getPlayerY() { return playerY; }

    /***
     * Sets the location on Y axis of the player.
     *
     * @param playerY location of the player on Y axis.
     */
    public void setPlayerY(float playerY) { this.playerY = playerY; }

    /***
     * Returns velocity of the rocket on X axis
     *
     * @return velocity on X axis
     */
    public float getVelX() { return velX; }

    /***
     * Sets velocity of the rocket on X axis
     *
     * @param velX velocity on X axis
     */
    public void setVelX(float velX) { this.velX = velX; }

    /****
     * Returns velocity of the rocket on Y axis.
     *
     * @return velocity on Y axis
     */
    public float getVelY() { return velY; }

    /***
     * Sets velocity of the rocket on Y axis.
     *
     * @param velY velocity on Y axis
     */
    public void setVelY(float velY) { this.velY = velY; }

    /***
     * Sets gravity constant
     *
     * @param gravity gravity constant
     */
    public void setGravity(float gravity){
        this.gravity = gravity;
    }

    /***
     * Returns gravity constant
     *
     * @return gravity constant
     */
    public float getGravity(){ return gravity; }

    /***
     * Sets fuel of the rocket
     *
     * @param fuel value of fuel
     */
    public void setFuel(int fuel){ this.fuel = fuel; }

    /***
     * Returns login name of the player
     *
     * @return name of the player
     */
    public String getLogin(){ return login; }


    /***
     * Returns the angle of the rocket if rotated
     *
     * @return angle of the rocket
     */
    public float getRotation(){ return rotate; }

    /***
     * Sets rotation of the rocket
     *
     * @param rotate angle of the rocket
     */
    public void setRotation(float rotate){ this.rotate = rotate; }

    /***
     * Returns the fuel of the rocket
     *
     * @return fuel of the rocket
     */
    public int getFuel(){ return fuel; }

    /***
     * Sets engineTimer to 0
     */
    public void setEngineTimerto0(){ this.engineTimer = 0;}

    /***
     * Sets power added to the rocket
     *
     * @param powerY power
     */
    public void setPowerY(float powerY){ this.powerY = powerY; }

    /***
     * Returns the timer of the current game
     *
     * @return current timer
     */
    public float getFlyTime(){ return flyTime; }

    /***
     * Sets fly timer of the current game
     *
     * @param flyTime current timer
     */
    public void setFlyTime(float flyTime){ this.flyTime = flyTime; }

    /***
     * Sets the usage of the rocket to true or false
     *
     * @param usingEngine if true, engine is being used. if false
     *                    engine is not being used
     */
    public void setUsingEngine(boolean usingEngine){ this.usingEngine = usingEngine; }


}
