package LunarLander;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Map;
import java.util.Vector;

/***
 * This is the properties manager which mainly loads the properties from the
 * properties files.
 */
public class PropertiesManager {

    private static PropertiesManager instance;

    private FileManager fileManager = FileManager.getInstance();
    private FontManager fontManager = FontManager.getInstance();

    /*Start Game*/
    public int WIDTH;
    public int HEIGHT;
    public String NAME;

    public STATE GameState = null;
    public STATE prevState = null;

    /*Kolory*/
    public Color ComponentColor;
    public Color backgroundColor;
    public Color fontColor;
    public Color selectedColor;
    public Color shopNotSelecColor;

    /*Czcionki*/
    public Font titleFont;
    public Font buttonFont;
    public Font smallButtonFont;
    public Font textFont;
    public Font littleGameFont;
    public Font shopFontSize;

    public int TitleY;
    /*Przyciski*/
    public int buttonNumMenu;
    public int buttonWidth;
    public int buttonHeight;
    public int button1Y;
    public int button2Y;
    public int button3Y;
    public int button4Y;

    public String StartButton;
    public String RankingButton;
    public String HelpButton;
    public String ExitButton;
    public String Backbutton;
    public String OKbutton;

    /*Help*/
    public int buttonNumHelp;
    public int helpTextY;
    public int helpTextX;
    public int lineBreakY;
    public String helpText;

    /*Login Window*/
    public String LoginTitle;
    public Color loginBgColor;

    public int loginWidth;
    public int loginHeight;
    public int textWidth;
    public int textHeight;
    public int buttonLogY;

    public int smallButtonWidth;
    public int smallButtonHeight;
    public int buttonNumLog;

    /*Shop window*/
    public String shopTitle;
    public Color shopBgColor;
    public Color shopSelCondColor;
    public Color shopNotSelCondColor;

    public int shopWidth;
    public int shopHeight;
    public int shopTitleY;

    public String buyButtonString;

    public int okButtonY;
    public int buyButtonY0;
    public int buyButtonY1;
    public int buyButtonY2;

    public String shopTitleString;
    public String shopLoginString;
    public String shopLevelString;
    public String shopLunarString;

    public int shopInfoY;

    public int levelCond1;
    public int levelCond2;
    public int levelCond3;

    public int lunarCond1;
    public int lunarCond2;
    public int lunarCond3;

    public String bonusString1;
    public String bonusString2;
    public String bonusString3;

    public int shopBonusStringX;
    public int shopBonusStringY;

    public String bonusInfo1;
    public String bonusInfo2;
    public String bonusInfo3;

    public int bonusInfoY;
    public int okButtonX;
    public int buyButtonX;


    /*Level*/
    public float[] x;
    public float[] y;
    public Vector<Line2D.Float> terrain = new Vector<>();
    public float gravity;
    public int landing1;
    public int landing2;
    public int numOfMeteors;
    public int meteorX;
    public int meteorY;
    public float meteorVelX;
    public float meteorVelY;
    public int meteorWIDTH;
    public int meteorHEIGHT;
    public int flyTime;

    public float lightLife;
    public float subLightLife;
    public int alphaLight;

    /*Player*/
    public float playerStartY;
    public int playerWIDTH;
    public int playerHEIGHT;
    public int playerTHICNESS;
    public int engineHeight;
    public float initVelY;
    public float initVelX;
    public int initFuel;
    public int initPowerY;
    public int initPowerX;
    public int initRotate;
    public int initEngineTime;
    public float addFallTime;
    public float dragX;
    public float addFlyTime;
    public float addEngineTime;
    public float addFuel;
    public int scaleDistance;
    public int spaceX;
    public int spaceY1;
    public int spaceY2;
    public int velCond;
    public int scaleFuel;
    public int initLevel;
    public int initScore;
    public int initLunars;
    public int initBonus;
    public float bonusFuel;

    /*Game Panel*/
    public int panelTitleX;
    public int playerStringY;
    public int levelStringY;
    public int fuelX;
    public int fuelY;
    public int fuelWidth;
    public int fuelHeight;
    public int fuelTitleX;
    public int fuelTitleY;
    public int velStringX;
    public int velStringY;
    public int helpPanelStringX;
    public int helpPanelStringY;
    public int bonusStringX;
    public int bonusStringY;
    public int bonusStringGap;

    public String fuelString;
    public String velString1;
    public String velString2;
    public String levelString;
    public String helpPanelString;
    public String addFuelString;
    public String fullFuelString;
    public String suddenStopString;

    public Color fuelBgColor;
    public Color fuelColor;

    public int smallFontSize;
    public float scaleFuelPanel;
    public int velStringGap;
    public int panelTitleSize;
    public int timerStringSize;
    public int timerStringGap;

    public String timeString;
    public String secString;

    /*End Game*/
    public int mediumWIDTH;
    public int mediumHEGIHT;

    public String endGameTitle;
    public String winString;
    public String endString0;
    public String endString1;
    public String endString2;
    public String endString3;
    public String tryAgainButton;

    public int winStringY;
    public int endString0Y;
    public int endString1Y;
    public int endString2Y;
    public int endString3Y;
    public int endGameButtonY;

    public String loseString;
    public String outOfFuelString;
    public String tooFastYString;
    public String tooFastXString;
    public String velXString;
    public String outOfTimeString;
    public String inSpaceString;
    public String meteorString;
    public String mountainCollisionString;
    public String nextLevelString;

    public int endStringGap;

    /*Ranking*/
    public String rankingl1String;
    public String rankingl2String;
    public String rankingl3String;
    public String rankingl4String;
    public String rankingl5String;
    public String rankingl6String;
    public String rankingScoreString;
    public String winnerString;
    public int rankingFirstY;
    public int rankingStringBreak;
    public int rankingNickX;
    public int rankingLevelX;
    public int rankingScoreX;
    public int rankingMaxPrint;



    static{
        try{
            instance = new PropertiesManager();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static synchronized PropertiesManager getInstance(){return instance;}


    /***
     * Constructor of the class.
     * <p>
     *     Loads properties of game, menu, help menu, login window, shop, board
     *     and end game window.
     * </p>
     */
    public PropertiesManager(){
        loadGame();
        loadMenu();
        loadHelp();
        loadLoginWindow();
        loadShop();
        loadBoard();
        loadEndGame();

    }

    /***
     * Loads properties of the main menu from the property file.
     */
    private void loadMenu(){
        Map<String,String> map = fileManager.loadToMap("Menu.properties");

        /*Kolory*/
        backgroundColor =   fileManager.parseColor(map.get("BackgroundColor"));
        fontColor =         fileManager.parseColor(map.get("FontColor"));
        selectedColor =     fileManager.parseColor(map.get("SelectedColor"));

        /*Czcionki*/
        titleFont =     fontManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("TitleSize")));
        buttonFont =    fontManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("ButtonFontSize")));
        textFont =      fontManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("TextSize")));
        shopFontSize = fontManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("ShopFontSize")));

        TitleY = Integer.parseInt(map.get("TitleY"));
        /*Przciski*/
        buttonNumMenu = Integer.parseInt(map.get("buttonNum"));
        buttonWidth =   Integer.parseInt(map.get("buttonWidth"));
        buttonHeight =  Integer.parseInt(map.get("buttonHeight"));
        button1Y  =     Integer.parseInt(map.get("button1Y"));
        button2Y  =     Integer.parseInt(map.get("button2Y"));
        button3Y  =     Integer.parseInt(map.get("button3Y"));
        button4Y  =     Integer.parseInt(map.get("button4Y"));
        StartButton =   map.get("StartButton");
        RankingButton = map.get("RankingButton");
        HelpButton =    map.get("HelpButton");
        ExitButton =    map.get("ExitButton");
        Backbutton =    map.get("BackButton");
        OKbutton =      map.get("OKbutton");

        /*Ranking*/
        rankingl1String = map.get("level1String");
        rankingl2String = map.get("level2String");
        rankingl3String = map.get("level3String");
        rankingl4String = map.get("level4String");
        rankingl5String = map.get("level5String");
        rankingl6String = map.get("level6String");
        rankingScoreString = map.get("scoreString");
        rankingFirstY =Integer.parseInt(map.get("firstStringY"));
        rankingStringBreak = Integer.parseInt(map.get("stringBreak"));
        rankingNickX = Integer.parseInt(map.get("nickX"));
        rankingLevelX = Integer.parseInt(map.get("levelX"));
        rankingScoreX = Integer.parseInt(map.get("scoreX"));
        rankingMaxPrint = Integer.parseInt(map.get("maxPrinted"));
        winnerString = map.get("winnerString");


    }

    /***
     * Loads properties of the help menu from the property file.
     */
    public void loadHelp(){
        Map<String,String> map = fileManager.loadToMap("Help.properties");

        buttonNumHelp = Integer.parseInt(map.get("buttonNum"));
        helpTextY = Integer.parseInt(map.get("helpTextY"));
        helpTextX = Integer.parseInt(map.get("helpTextX"));
        lineBreakY = Integer.parseInt(map.get("lineBreakY"));
        helpText = map.get("HelpText");

    }

    /***
     * Loads properties of the game from the property file.
     */
    public void loadGame(){
        Map<String,String> map = fileManager.loadToMap("Start.properties");

        NAME =      map.get("Name");
        WIDTH =     Integer.parseInt(map.get("WIDTH"));
        HEIGHT =    Integer.parseInt(map.get("HEIGHT"));
        prevState = STATE.valueOf(map.get("prevState"));
        GameState = STATE.valueOf(map.get("GameState"));
        ComponentColor = fileManager.parseColor(map.get("ComponentColor"));


    }

    /***
     * Loads properties of the login window from the property file.
     */
    public void loadLoginWindow(){
        Map<String,String> map = fileManager.loadToMap("LoginWindow.properties");

        LoginTitle =    map.get("LoginTitle");
        loginWidth =    Integer.parseInt(map.get("loginWidth"));
        loginHeight =   Integer.parseInt(map.get("loginHeight"));
        textHeight =    Integer.parseInt(map.get("textHeight"));
        textWidth =     Integer.parseInt(map.get("textWidth"));
        buttonLogY =    Integer.parseInt(map.get("buttonY"));

        loginBgColor =  fileManager.parseColor(map.get("Color"));

        smallButtonWidth =   Integer.parseInt(map.get("buttonWidth"));
        smallButtonHeight =  Integer.parseInt(map.get("buttonHeight"));
        buttonNumLog =       Integer.parseInt(map.get("buttonNum"));

        smallButtonFont = fontManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("buttonFontSize")));

    }

    /***
     * Loads properties of the shop from the property file.
     */
    public void loadShop(){
        Map<String,String> map = fileManager.loadToMap("Shop.properties");

        shopTitle = map.get("shopTitle");
        shopBgColor =  fileManager.parseColor(map.get("shopBgColor"));
        shopWidth =  Integer.parseInt(map.get("shopWidth"));
        shopHeight = Integer.parseInt(map.get("shopHeight"));
        shopTitleY = Integer.parseInt(map.get("titleY"));
        shopNotSelecColor = fileManager.parseColor(map.get("notSelectedColor"));
        shopNotSelCondColor = fileManager.parseColor(map.get("notSelectedCondColor"));
        shopSelCondColor = fileManager.parseColor(map.get("selectedCondColor"));

        buyButtonString = map.get("buyButtonString");
        okButtonY = Integer.parseInt(map.get("okButtonY"));
        buyButtonY0 = Integer.parseInt(map.get("buyButtonY0"));
        buyButtonY1 = Integer.parseInt(map.get("buyButtonY1"));
        buyButtonY2 = Integer.parseInt(map.get("buyButtonY2"));
        shopTitleString = map.get("shopTitleString");
        shopLoginString = map.get("shopLoginString");
        shopLevelString = map.get("shopLevelString");
        shopLunarString = map.get("shopLunarString");
        shopInfoY = Integer.parseInt(map.get("shopInfoY"));
        levelCond1 = Integer.parseInt(map.get("levelCond1"));
        levelCond2 = Integer.parseInt(map.get("levelCond2"));
        levelCond3 = Integer.parseInt(map.get("levelCond3"));
        lunarCond1 = Integer.parseInt(map.get("lunarCond1"));
        lunarCond2 = Integer.parseInt(map.get("lunarCond2"));
        lunarCond3 = Integer.parseInt(map.get("lunarCond3"));
        bonusString1=map.get("bonusString1");
        bonusString2 = map.get("bonusString2");
        bonusString3 = map.get("bonusString3");
        shopBonusStringX = Integer.parseInt(map.get("shopBonusStringX"));
        shopBonusStringY = Integer.parseInt(map.get("shopBonusStringY"));
        bonusInfo1 = map.get("bonusInfo1");
        bonusInfo2 = map.get("bonusInfo2");
        bonusInfo3 = map.get("bonusInfo3");
        bonusInfoY = Integer.parseInt(map.get("bonusInfoY"));
        okButtonX = Integer.parseInt(map.get("okButtonX"));
        buyButtonX = Integer.parseInt(map.get("buyButtonX"));

    }

    /***
     * Loads properties of the board from the property file.
     */
    public void loadBoard(){
        Map<String,String> map = fileManager.loadToMap("Board.properties");

        playerStartY = Float.parseFloat(map.get("playerStartY"));
        playerHEIGHT = Integer.parseInt(map.get("playerHEIGHT"));
        playerWIDTH = Integer.parseInt(map.get("playerWIDTH"));
        playerTHICNESS = Integer.parseInt(map.get("playerTHICKNESS"));
        engineHeight = Integer.parseInt(map.get("engineHeight"));
        initVelX = Float.parseFloat(map.get("initVelX"));
        initVelY = Float.parseFloat(map.get("initVelY"));

        littleGameFont = fontManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("fontSize")));

        panelTitleX = Integer.parseInt(map.get("titleX"));
        playerStringY = Integer.parseInt(map.get("playerStringY"));
        levelStringY = Integer.parseInt(map.get("levelStringY"));
        fuelX = Integer.parseInt(map.get("fuelX"));
        fuelY = Integer.parseInt(map.get("fuelY"));
        fuelWidth = Integer.parseInt(map.get("fuelWidth"));
        fuelHeight = Integer.parseInt(map.get("fuelHeight"));
        fuelTitleX = Integer.parseInt(map.get("fuelTitleX"));
        fuelTitleY = Integer.parseInt(map.get("fuelTitleY"));
        velStringX = Integer.parseInt(map.get("velStringX"));
        velStringY = Integer.parseInt(map.get("velStringY"));
        helpPanelStringX = Integer.parseInt(map.get("helpStringX"));
        helpPanelStringY = Integer.parseInt(map.get("helpStringY"));
        bonusStringX = Integer.parseInt(map.get("bonusStringX"));
        bonusStringY = Integer.parseInt(map.get("bonusStringY"));
        bonusStringGap = Integer.parseInt(map.get("bonusStringGap"));

        fuelString = map.get("fuelString");
        velString1 = map.get("velString1");
        velString2 = map.get("velString2");
        levelString = map.get("levelString");
        helpPanelString = map.get("helpString");
        addFuelString = map.get("addFuelString");
        fullFuelString = map.get("fullFuelString");
        suddenStopString = map.get("suddenStopString");

        fuelBgColor = fileManager.parseColor(map.get("fuelBgColor"));
        fuelColor = fileManager.parseColor(map.get("fuelColor"));

        initPowerY = Integer.parseInt(map.get("initPowerY"));
        initPowerX = Integer.parseInt(map.get("initPowerX"));
        initRotate = Integer.parseInt(map.get("initRotate"));
        initEngineTime = Integer.parseInt(map.get("initEngineTime"));
        addFallTime = Float.parseFloat(map.get("addFallTime"));
        dragX = Float.parseFloat(map.get("dragX"));
        addFlyTime = Float.parseFloat(map.get("addFlyTime"));
        addEngineTime = Float.parseFloat(map.get("addEngineTime"));
        addFuel = Float.parseFloat(map.get("addFuel"));
        scaleDistance = Integer.parseInt(map.get("scaleDistance"));
        spaceX = Integer.parseInt(map.get("spaceX"));
        spaceY1 = Integer.parseInt(map.get("spaceY1"));
        spaceY2 = Integer.parseInt(map.get("spaceY2"));
        velCond = Integer.parseInt(map.get("velCond"));
        scaleFuel = Integer.parseInt(map.get("scaleFuel"));
        initLevel = Integer.parseInt(map.get("initLevel"));
        initScore = Integer.parseInt(map.get("initScore"));
        initLunars = Integer.parseInt(map.get("initLunars"));
        initBonus = Integer.parseInt(map.get("initBonus"));
        bonusFuel = Float.parseFloat(map.get("bonusFuel"));
        initFuel = Integer.parseInt(map.get("initFuel"));

        lightLife = Float.parseFloat(map.get("lightLife"));
        subLightLife = Float.parseFloat(map.get("subLightLife"));
        alphaLight = Integer.parseInt(map.get("alphaLight"));

        smallFontSize = Integer.parseInt(map.get("smallFontSize"));
        scaleFuelPanel = Float.parseFloat(map.get("scaleFuelPanel"));
        velStringGap = Integer.parseInt(map.get("velStringGap"));
        panelTitleSize = Integer.parseInt(map.get("panelTitleSize"));
        timerStringSize = Integer.parseInt(map.get("timerStringSize"));
        timerStringGap = Integer.parseInt(map.get("timerStringGap"));
        timeString = map.get("timeString");
        secString = map.get("secString");
    }

    /***
     * Loads properties of the end game window from the property file.
     */
    private void loadEndGame(){
        Map<String,String> map = fileManager.loadToMap("EndGame.properties");

        endGameTitle = map.get("WindowString");
        winString = map.get("WinString");
        endString0 = map.get("String0");
        endString1 = map.get("String1");
        endString2 = map.get("String2");
        endString3 = map.get("String3");
        tryAgainButton = map.get("tryAgainButton");

        mediumHEGIHT = Integer.parseInt(map.get("HEIGHT"));
        mediumWIDTH = Integer.parseInt(map.get("WIDTH"));
        winStringY = Integer.parseInt(map.get("winStringY"));
        endString0Y = Integer.parseInt(map.get("string0Y"));
        endString1Y = Integer.parseInt(map.get("string1Y"));
        endString2Y = Integer.parseInt(map.get("string2Y"));
        endString3Y = Integer.parseInt(map.get("string3Y"));;
        endGameButtonY = Integer.parseInt(map.get("buttonY"));

        loseString = map.get("loseString");
        outOfFuelString = map.get("outOfFuelString");
        tooFastYString = map.get("tooFastYString");
        tooFastXString = map.get("tooFastXString");
        velXString = map.get("velXString");
        outOfTimeString = map.get("outOfTimeString");
        inSpaceString = map.get("inSpaceString");
        meteorString = map.get("meteorString");
        mountainCollisionString = map.get("mountainCollisionString");
        nextLevelString = map.get("nextLevelString");

        endStringGap = Integer.parseInt(map.get("endStringGap"));
    }


    /***
     * Loads properties of a level from the property file.
     */
    public void loadLevel(int level){
        Map<String,String> map = fileManager.loadToMap("Level.properties");

        meteorHEIGHT = Integer.parseInt(map.get("meteorHEIGHT"));
        meteorWIDTH = Integer.parseInt(map.get("meteorWIDTH"));

        terrain.clear();
        switch(level){
            case 1:
                x = fileManager.splitStringToFloat(map.get("level1x"));
                y = fileManager.splitStringToFloat(map.get("level1y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing1_0"));
                landing2 = Integer.parseInt(map.get("landing1_1"));
                numOfMeteors = Integer.parseInt(map.get("meteor1"));
                gravity = Float.parseFloat(map.get("gravity1"));
                initFuel = Integer.parseInt(map.get("initFuel1"));

                meteorX = Integer.parseInt(map.get("m1x_0"));
                meteorY = Integer.parseInt(map.get("m1y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_1"));
                meteorVelY = Float.parseFloat(map.get("mVely_1"));

                flyTime = Integer.parseInt(map.get("flyTime1"));
                break;
            case 2:
                x = fileManager.splitStringToFloat(map.get("level2x"));
                y = fileManager.splitStringToFloat(map.get("level2y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing2_0"));
                landing2 = Integer.parseInt(map.get("landing2_1"));
                numOfMeteors = Integer.parseInt(map.get("meteor2"));
                gravity = Float.parseFloat(map.get("gravity1"));
                initFuel = Integer.parseInt(map.get("initFuel2"));

                meteorX = Integer.parseInt(map.get("m2x_0"));
                meteorY = Integer.parseInt(map.get("m2y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_2"));
                meteorVelY = Float.parseFloat(map.get("mVely_2"));

                flyTime = Integer.parseInt(map.get("flyTime2"));
                break;
            case 3:
                x = fileManager.splitStringToFloat(map.get("level3x"));
                y = fileManager.splitStringToFloat(map.get("level3y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing3_0"));
                landing2 = Integer.parseInt(map.get("landing3_1"));

                numOfMeteors = Integer.parseInt(map.get("meteor3"));
                meteorX = Integer.parseInt(map.get("m3x_0"));
                meteorY = Integer.parseInt(map.get("m3y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_3"));
                meteorVelY = Float.parseFloat(map.get("mVely_3"));

                gravity = Float.parseFloat(map.get("gravity3"));
                initFuel = Integer.parseInt(map.get("initFuel3"));

                flyTime = Integer.parseInt(map.get("flyTime3"));
                break;
            case 4:
                x = fileManager.splitStringToFloat(map.get("level4x"));
                y = fileManager.splitStringToFloat(map.get("level4y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing4_0"));
                landing2 = Integer.parseInt(map.get("landing4_1"));
                numOfMeteors = Integer.parseInt(map.get("meteor4"));
                gravity = Float.parseFloat(map.get("gravity4"));
                initFuel = Integer.parseInt(map.get("initFuel4"));

                meteorX = Integer.parseInt(map.get("m4x_0"));
                meteorY = Integer.parseInt(map.get("m4y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_4"));
                meteorVelY = Float.parseFloat(map.get("mVely_4"));

                flyTime = Integer.parseInt(map.get("flyTime4"));
                break;
            case 5:
                x = fileManager.splitStringToFloat(map.get("level5x"));
                y = fileManager.splitStringToFloat(map.get("level5y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing5_0"));
                landing2 = Integer.parseInt(map.get("landing5_1"));

                numOfMeteors = Integer.parseInt(map.get("meteor5"));
                meteorX = Integer.parseInt(map.get("m5x_0"));
                meteorY = Integer.parseInt(map.get("m5y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_5"));
                meteorVelY = Float.parseFloat(map.get("mVely_5"));

                gravity = Float.parseFloat(map.get("gravity5"));
                initFuel = Integer.parseInt(map.get("initFuel5"));

                flyTime = Integer.parseInt(map.get("flyTime5"));
                break;
            case 6:
                x = fileManager.splitStringToFloat(map.get("level6x"));
                y = fileManager.splitStringToFloat(map.get("level6y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing6_0"));
                landing2 = Integer.parseInt(map.get("landing6_1"));
                numOfMeteors = Integer.parseInt(map.get("meteor6"));
                gravity = Float.parseFloat(map.get("gravity6"));
                initFuel = Integer.parseInt(map.get("initFuel6"));

                meteorX = Integer.parseInt(map.get("m6x_0"));
                meteorY = Integer.parseInt(map.get("m6y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_6"));
                meteorVelY = Float.parseFloat(map.get("mVely_6"));

                flyTime = Integer.parseInt(map.get("flyTime6"));
                break;
            case 7:
                x = fileManager.splitStringToFloat(map.get("level7x"));
                y = fileManager.splitStringToFloat(map.get("level7y"));
                for (int i=0;i<x.length-1;i++) {
                    terrain.addElement(new Line2D.Float((float) x[i],(float)y[i],(float)x[i+1],(float)y[i+1]));
                }

                landing1 = Integer.parseInt(map.get("landing7_0"));
                landing2 = Integer.parseInt(map.get("landing7_1"));
                numOfMeteors = Integer.parseInt(map.get("meteor7"));
                gravity = Float.parseFloat(map.get("gravity7"));
                initFuel = Integer.parseInt(map.get("initFuel7"));

                meteorX = Integer.parseInt(map.get("m7x_0"));
                meteorY = Integer.parseInt(map.get("m7y_0"));

                meteorVelX = Float.parseFloat(map.get("mVelx_7"));
                meteorVelY = Float.parseFloat(map.get("mVely_7"));

                flyTime = Integer.parseInt(map.get("flyTime7"));
            default:
                break;
        }

    }

    /***
     * Loads properties of a player from the property file.
     */
    public int[] loadPlayer(String login){
        Map<String,String> map = fileManager.loadToMap("Players.properties");

        return fileManager.splitStringToInt(map.get(login));
    }

    /***
     * Checks if the player is in the player's file.
     *
     * @param login name of the player
     * @return if true, the player is in the the file.
     *          if false, the player is not in the file.
     */
    public boolean isPlayerIn(String login){
        Map<String,String> map = fileManager.loadToMap("Players.properties");

        return map.containsKey(login);
    }

}
