package LunarLander;

import javax.swing.plaf.basic.BasicListUI;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

    private static InputManager instance;

    private boolean[] keys = new boolean[100];

    private boolean up,down,left,right,ok,space;


    private InputManager (){}

    static{
        try{
            instance = new InputManager();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public synchronized static InputManager getInstance(){return instance;}


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;

        assignKey();

        System.out.println("test");

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }


    private void assignKey(){
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        ok = keys[KeyEvent.VK_ENTER];
        space = keys[KeyEvent.VK_SPACE];
    }

    public boolean isUpPressed(){return up;}

    public boolean isDownPressed(){return down;}

    public boolean isLewftPressed(){return left;}

    public boolean isOkPressed(){return ok;}

    public boolean isSpacePressed(){return space;}
}
