package LunarLander;
import java.awt.*;

/***
 * Main class with function main for application LunarLander
 */

public class Main {

    GameController gameController;

    /***
     * Class constructor creates GameController object and sets the main
     * window of application LunarLander visible
     */
    public Main(){
        gameController = new GameController();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                gameController.setVisible(true);
            }
        });

        gameController.thread.start();
    }

    /***
     * main function; creates object Main
     * @param avg
     */
    public static void main(String[] avg){
        new Main();

    }


}
