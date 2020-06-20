package LunarLander;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/***
 * This is an implementation of a KeyListener for Lunar Lander Game
 */
public class KeyInput implements KeyListener {

    private Board board;

    /***
     * Constructor of the class
     *
     * @param board object of the Board class
     */
   public KeyInput(Board board){
       this.board = board;
   }

    /***
     * Implements action after a key is typed
     *
     * @param keyEvent
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        this.board.keyTyped(keyEvent);
    }

    /***
     * Implements action after a key is pressed according to the current state
     *
     * @param keyEvent
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        this.board.keyPressed(keyEvent);
    }

    /***
     * Implements action after a key is released according to the current state
     *
     * @param keyEvent
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        this.board.keyReleased(keyEvent);
    }
}
