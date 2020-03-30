package LunarLander;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Properties;

public class GameController extends JFrame {

    public int WIDTH;
    public int HEIGHT;
    public String NAME;

    public static STATE GameState = null;

    private PropertiesManager propertiesManager = PropertiesManager.getInstance();

    private MainMenu mainMenu;


    public GameController(){


        mainMenu = new MainMenu(this);


        initGame();



        initFrame();
    }




    public void initGame(){
        Map<String,String> map = propertiesManager.loadToMap("Start.properties");

        NAME = map.get("Name");
        WIDTH = Integer.parseInt(map.get("WIDTH"));
        HEIGHT = Integer.parseInt(map.get("HEIGHT"));
        changeState(STATE.valueOf(map.get("GameState")));


    }

    public void changeState(STATE state){
        GameState = state;

        switch(GameState){
            case MENU:
                this.add(mainMenu);
                setResizable(false);
                break;
            default:
                break;
        }
    }



    private void initFrame(){
        setTitle(NAME);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        requestFocus();
        setLocationRelativeTo(null);
    }




}
