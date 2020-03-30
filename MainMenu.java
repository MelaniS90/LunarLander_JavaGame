package LunarLander;

import java.awt.*;

public class MainMenu extends abstractMenu {
    public MainMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(fontColor);
        drawCenterString(gameController.NAME,90,g,titleFont);

        System.out.println("test");

    }
}
