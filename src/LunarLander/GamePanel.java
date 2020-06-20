package LunarLander;
import java.awt.*;

/***
 * This is a game panel displayed on the board.
 */
public class GamePanel {

    private GameController gameController;
    private PropertiesManager properties = PropertiesManager.getInstance();

    /***
     * Constructor of the class.
     * <p>
     *     Sets the inner object as the parameter
     * </p>
     * @param gameController object of the GameController class
     */
    public GamePanel(GameController gameController){
        this.gameController = gameController;
    }

    /***
     * Draws fuel, celocity, title, help and timer.
     *
     * @param g Graphics
     * @param player current player playing
     */
    public void drawGamePanel(Graphics g, Player player,float width, float height) {

        fuel(g,player,width,height);
        velocity(g,player,width,height);
        title(g,player,width,height);
        help(g,width, height);
        flyTimer(g,player,width,height);
    }


    private void fuel(Graphics g, Player player, float width, float height){
        Font font = properties.littleGameFont.deriveFont((float) (properties.smallFontSize * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));

        g.setColor(properties.fuelBgColor);
        g.fillRect((int) (properties.fuelX * (width/properties.WIDTH)),
                (int) (properties.fuelY * (height/properties.HEIGHT)),
                (int) (properties.fuelWidth * (width/properties.WIDTH)),
                (int) (properties.fuelHeight * (height/properties.HEIGHT)));

        g.setColor(properties.fuelColor);
        g.fillRect((int) (properties.fuelX * (width/properties.WIDTH)),
                (int) (properties.fuelY * (height/properties.HEIGHT)),
                (int) (properties.scaleFuelPanel * player.getFuel() / (properties.initFuel/properties.scaleFuel)* (width/properties.WIDTH)),
                (int) (properties.fuelHeight * (height/properties.HEIGHT)));


        g.setColor(properties.ComponentColor);
        g.drawRect((int) (properties.fuelX * (width/properties.WIDTH)),
                (int) (properties.fuelY * (height/properties.HEIGHT)),
                (int) (properties.fuelWidth * (width/properties.WIDTH)),
                (int) (properties.fuelHeight * (height/properties.HEIGHT)));

        g.setFont(font);
        g.drawString(properties.fuelString, (int) (properties.fuelTitleX * (width/properties.WIDTH)), (int) (properties.fuelTitleY * (height/properties.HEIGHT)));
    }


    private void velocity(Graphics g,Player player,float width, float height){
        Font font = properties.littleGameFont.deriveFont((float) (properties.smallFontSize * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));

        float velY = (player.getVelY() + player.getGravity()) * (-1);
        float velX = (player.getVelX());

        g.setFont(font);
        if(velX < -(properties.velCond))
            g.setColor(Color.RED);
        else
            g.setColor(properties.ComponentColor);
        g.drawString(properties.velString1 + velX, (int) (properties.velStringX * (width/properties.WIDTH)), (int) (properties.velStringY * (height/properties.HEIGHT)));

        if(velY < -(properties.velCond))
            g.setColor(Color.RED);
        else
            g.setColor(properties.ComponentColor);
        g.drawString(properties.velString2 + velY, (int) (properties.velStringX * (width/properties.WIDTH)), (int) ((properties.velStringY+properties.velStringGap)*(height/properties.HEIGHT)));
    }


    private void title(Graphics g, Player player,float width, float height){
        Font font = properties.textFont.deriveFont((float) (properties.panelTitleSize * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));

        g.setColor(properties.ComponentColor);
        g.setFont(font);
        g.drawString(player.getLogin(), (int) (properties.panelTitleX * (width/properties.WIDTH)), (int) (properties.playerStringY * (height/properties.HEIGHT)));
        g.drawString(properties.levelString + player.getLevel(),(int)((properties.panelTitleX)*(width/properties.WIDTH)),(int)(properties.levelStringY * (height/properties.HEIGHT)));
    }


    private void help(Graphics g,float width, float height){
        Font font = properties.textFont.deriveFont((float) (properties.panelTitleSize * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));

        g.setColor(properties.ComponentColor);
        g.setFont(font);
        g.drawString(properties.helpPanelString,(int)(properties.helpPanelStringX * (width/properties.WIDTH)), (int) (properties.helpPanelStringY * (height/properties.HEIGHT)));


        int i = 0;
        if(Player.addFuel > 0){
            g.setColor(Color.GREEN);
            g.drawString(properties.addFuelString, (int)(properties.bonusStringX * (width/properties.WIDTH)),(int)(properties.bonusStringY * (height/properties.HEIGHT)));
            i++;
        }
        if(Player.fullFuel > 0){
            g.setColor(Color.GREEN);
            g.drawString(properties.fullFuelString,(int)((properties.bonusStringX + properties.bonusStringGap * i) * (width/properties.WIDTH)),(int)(properties.bonusStringY * (height/properties.HEIGHT)));
            i++;
        }
        if(Player.suddenStop > 0){
            g.setColor(Color.GREEN);
            g.drawString(properties.suddenStopString,(int)((properties.bonusStringX + properties.bonusStringGap * i) * (width/properties.WIDTH)), (int)(properties.bonusStringY * (height/properties.HEIGHT)));
            i++;
        }

    }


    private void flyTimer(Graphics g, Player player, float width, float height){
        Font font = properties.littleGameFont.deriveFont((float) (properties.timerStringSize * Math.sqrt(Math.pow(width/properties.WIDTH,2)+Math.pow(height/properties.HEIGHT,2))));

        g.setColor(properties.ComponentColor);
        g.setFont(font);
        g.drawString(properties.timeString + (int)player.getFlyTime() + properties.secString,
                (int) (properties.velStringX *(width/properties.WIDTH)),
                (int) ((properties.velStringY + properties.timerStringGap)*(height/properties.HEIGHT)));
    }


}
