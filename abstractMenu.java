package LunarLander;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.util.Map;

public abstract class abstractMenu extends JComponent {

    protected GameController gameController;
    protected PropertiesManager propertiesManager = PropertiesManager.getInstance();

    /*Kolory*/
    protected Color backgroundColor;
    protected Color fontColor;

    /*Czcionki*/
    protected Font titleFont;
    protected Font buttonFont;
    protected Font textFont;



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
        g.drawString(s,x,y);

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


    }
}
