package LunarLander;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public abstract class abstractMenu extends JComponent {

    protected GameController gameController;
    protected PropertiesManager propertiesManager = PropertiesManager.getInstance();
    protected InputManager inputManager = InputManager.getInstance();

    /*Kolory*/
    protected Color backgroundColor;
    protected Color fontColor;

    /*Czcionki*/
    protected Font titleFont;
    protected Font buttonFont;
    protected Font textFont;

    protected int TitleY;
    /*Przyciski*/
    protected int buttonWidth;
    protected int buttonHeight;
    protected int button1Y;
    protected int button2Y;
    protected int button3Y;
    protected int button4Y;

    protected String StartButton;
    protected String RankingButton;
    protected String HelpButton;
    protected String ExitButton;



    public abstractMenu(GameController gameController) {
        this.gameController = gameController;

        setPreferredSize(new Dimension(640,480));
        initMenu();

    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);
    }



    protected void paintBackground(Graphics g){
        Dimension size = getSize();

        g.setColor(backgroundColor);
        g.fillRect(0,0,size.width,size.height);

    }

    protected void drawCenterString(String s,int y,Graphics g,Font font){
        Dimension size = getSize();
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (size.width - metrics.stringWidth(s))/2;
        g.setFont(font);
        g.drawString(s,x,y - (buttonHeight - metrics.getHeight())/2);

    }

    protected void drawCenterRect(int y,Graphics g){
        Dimension size = getSize();

        int x = (size.width - buttonWidth)/2;
        g.drawRect(x,y-buttonHeight,buttonWidth,buttonHeight);

    }




    private void initMenu(){
        Map<String,String> map = propertiesManager.loadToMap("Menu.properties");

        /*Kolory*/
        backgroundColor = propertiesManager.parseColor(map.get("BackgroundColor"));
        fontColor = propertiesManager.parseColor(map.get("FontColor"));

        /*Czcionki*/
        titleFont = propertiesManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("TitleSize")));
        buttonFont = propertiesManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("ButtonFontSize")));
        textFont = propertiesManager.loadFont(map.get("FontName")).deriveFont(Float.parseFloat(map.get("TextSize")));

        TitleY = Integer.parseInt(map.get("TitleY"));
        /*Przciski*/
        buttonWidth = Integer.parseInt(map.get("buttonWidth"));
        buttonHeight = Integer.parseInt(map.get("buttonHeight"));
        button1Y  = Integer.parseInt(map.get("button1Y"));
        button2Y  = Integer.parseInt(map.get("button2Y"));
        button3Y  = Integer.parseInt(map.get("button3Y"));
        button4Y  = Integer.parseInt(map.get("button4Y"));
        StartButton = map.get("StartButton");
        RankingButton = map.get("RankingButton");
        HelpButton = map.get("HelpButton");
        ExitButton = map.get("ExitButton");


    }
}
