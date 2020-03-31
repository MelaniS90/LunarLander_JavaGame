package LunarLander;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainMenu extends abstractMenu {
    public MainMenu(GameController gameController) {
        super(gameController);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                System.out.println("test");
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(fontColor);
        drawCenterString(gameController.NAME,TitleY,g,titleFont);

        drawOptions(g);

    }


    private void drawOptions(Graphics g){

        g.setColor(fontColor);

        drawCenterString(StartButton,button1Y,g,buttonFont);
        drawCenterRect(button1Y,g);

        drawCenterString(RankingButton,button2Y,g,buttonFont);
        drawCenterRect(button2Y,g);

        drawCenterString(HelpButton,button3Y,g,buttonFont);
        drawCenterRect(button3Y,g);

        drawCenterString(ExitButton, button4Y, g, buttonFont);
        drawCenterRect(button4Y, g);


    }
}
