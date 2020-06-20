package LunarLander;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.stream.Collectors;

/***
 * This is the ranging of the 10 best players so far.
 */
public class Ranking extends abstractMenu {

    private Button[] options;
    private Map<String,int[]> scoreMap = new HashMap<>();

    private Map<String, Integer> firstlevels = new HashMap<>();
    private Map<String, Integer> secondlevels = new HashMap<>();
    private Map<String, Integer> thirdlevels = new HashMap<>();
    private Map<String, Integer> fourthlevels = new HashMap<>();
    private Map<String, Integer> fifthlevels = new HashMap<>();
    private Map<String, Integer> sixthlevels = new HashMap<>();
    private Map<String, Integer> winners = new HashMap<>();


    /***
     * Constructor of the class.
     * <p>
     *     Sets the buttons, adds and implements MouseListener and MouseMotionListener.
     * </p>
     *
     * @param gameController object of a GameController class for the constructor
     *                       of the inherited class
     */
    public Ranking(GameController gameController) {
        super(gameController);

        options = new Button[properties.buttonNumHelp];
        options[0] = new Button(properties.Backbutton,properties.button4Y,properties.buttonFont,properties.fontColor,properties.selectedColor,properties.buttonWidth,properties.buttonHeight);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                boolean clicked = false;
                for(int i = 0;i<properties.buttonNumHelp;i++){
                    if(options[i].intersects(new Rectangle(e.getX(),e.getY(),1,1))){
                        clicked = true;
                    }
                }
                if(clicked) select();
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                for (int i = 0; i < properties.buttonNumHelp; i++) {
                    if (options[i].intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
                        options[i].setSelected(true);

                    } else
                        options[i].setSelected(false);

                }
                repaint();
            }
        });

    }

    /***
     * Paints the component.
     * <p>
     *     Draws title, buttons and best players.
     * </p>
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(properties.fontColor);
        drawCenterString(properties.NAME,properties.TitleY,g,properties.titleFont);

        paintButtons(g);
        printSorted(g);
    }

    /***
     * Sets the state (to MENU) when the button is clicked.
     */
    @Override
    protected void select() {
        gameController.changeState(STATE.MENU);
    }


    private void paintButtons(Graphics g){
        Dimension size = getSize();

        options[0].drawCenterButton(g,size);

    }

    /***
     * Loads the scores of the players
     */
    public void loadScores(){
        Map<String,String> map = fileManager.loadToMap("Players.properties");

        for(String key: map.keySet()){
            if(key!=null){
                String s = map.get(key);
                int[] scores = fileManager.splitStringToInt(s);
                scoreMap.put(key,scores);
            }
        }

    }

    /***
     * Sorts players according to the scores and prints best 10 players
     *
     * @param g
     */
    public void printSorted(Graphics g){
        loadScores();
        sortByLevel();

        Map<String, Integer> firstSorted = sortByValue(firstlevels);
        Map<String, Integer> secondSorted = sortByValue(secondlevels);
        Map<String, Integer> thirdSorted = sortByValue(thirdlevels);
        Map<String, Integer> fourthSorted = sortByValue(fourthlevels);
        Map<String, Integer> fifthSorted = sortByValue(fifthlevels);
        Map<String, Integer> sixthSorted = sortByValue(sixthlevels);
        Map<String, Integer> winnersSorted = sortByValue(winners);

        g.setFont(properties.textFont);
        g.setColor(properties.fontColor);

        int printed =0;

        for (int i = 0; i < winnersSorted.size(); i++) {
            if(printed<10) {
                g.drawString(winnersSorted.keySet().toArray()[i].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.winnerString,properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingScoreString+ winnersSorted.get(winnersSorted.keySet().toArray()[i]).toString(),properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                printed++;
            }
        }
        for (int i = 0; i < sixthSorted.size(); i++) {
            if(printed<10) {
                g.drawString(sixthSorted.keySet().toArray()[i].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingl6String,properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingScoreString+ sixthSorted.get(sixthSorted.keySet().toArray()[i]).toString(),properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                printed++;
            }
        }
        for (int i = 0; i < fifthSorted.size(); i++) {
            if(printed<10) {
                g.drawString(fifthSorted.keySet().toArray()[i].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingl5String,properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingScoreString+ fifthSorted.get(fifthSorted.keySet().toArray()[i]).toString(),properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                printed++;
            }
        }
        for (int i = 0; i < fourthSorted.size(); i++) {
            if(printed<10) {
                g.drawString(fourthSorted.keySet().toArray()[i].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingl4String,properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingScoreString+ fourthSorted.get(fourthSorted.keySet().toArray()[i]).toString(),properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                printed++;
            }
        }
        for (int i = 0; i < thirdSorted.size(); i++) {
            if(printed<10) {
                g.drawString(thirdSorted.keySet().toArray()[i].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingl3String,properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                g.drawString(properties.rankingScoreString+ thirdSorted.get(thirdSorted.keySet().toArray()[i]).toString(),properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                printed++;
            }
        }
            for (int i = 0; i < secondSorted.size(); i++) {
                if(printed<10) {
                    g.drawString(secondSorted.keySet().toArray()[i].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                    g.drawString(properties.rankingl2String,properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                    g.drawString(properties.rankingScoreString+ secondSorted.get(secondSorted.keySet().toArray()[i]).toString(),properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                    printed++;
                }
            }
            for(int j=0;j<firstSorted.size();j++){
                if(printed<10) {
                    g.drawString(firstSorted.keySet().toArray()[j].toString(), properties.rankingNickX, properties.rankingFirstY + printed * properties.rankingStringBreak);
                    g.drawString(properties.rankingl1String, properties.rankingLevelX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                    g.drawString(properties.rankingScoreString+ firstSorted.get(firstSorted.keySet().toArray()[j]).toString(), properties.rankingScoreX,properties.rankingFirstY + printed * properties.rankingStringBreak);
                    printed++;
                }
            }

    }


    private void sortByLevel(){

        for(String key: scoreMap.keySet()){
            if(scoreMap.get(key)[0]==1)
                firstlevels.put(key,scoreMap.get(key)[1]);
            else if(scoreMap.get(key)[0]==2)
                secondlevels.put(key,scoreMap.get(key)[1]);
            else if(scoreMap.get(key)[0]==3)
                thirdlevels.put(key,scoreMap.get(key)[1]);
            else if(scoreMap.get(key)[0]==4)
                fourthlevels.put(key,scoreMap.get(key)[1]);
            else if(scoreMap.get(key)[0]==5)
                fifthlevels.put(key,scoreMap.get(key)[1]);
            else if(scoreMap.get(key)[0]==6)
                sixthlevels.put(key,scoreMap.get(key)[1]);
            else if(scoreMap.get(key)[0]==7)
                winners.put(key,scoreMap.get(key)[1]);
        }

    }


    private static Map<String, Integer> sortByValue(final Map<String, Integer> wordCounts) {

            return wordCounts.entrySet()
                    .stream()
                    .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }



}
