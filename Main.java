package LunarLander;

import javax.swing.*;
import java.awt.*;

public class Main {

    GameController gameController;

    public Main(){
        gameController = new GameController();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                gameController.setVisible(true);
            }
        });

    }

    public static void main(String[] avg){
        new Main();

    }


}
