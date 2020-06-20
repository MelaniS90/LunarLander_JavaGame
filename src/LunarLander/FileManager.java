package LunarLander;

import java.awt.*;
import java.io.*;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/***
 * This is a file manager which reads data from properties files
 * and manages the data.
 */
public class FileManager {

    private static FileManager instance;

    static{
        try{
            instance = new FileManager();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static synchronized FileManager getInstance(){return instance;}


    /***
     * Reads data from properties file and puts it into a map
     *
     * @param fileName name of the properties file
     * @return a map with data
     */
    public Map<String,String> loadToMap(String fileName){
        Properties p = new Properties();
        Map<String,String> map = new HashMap<>();

        try(InputStream is = new FileInputStream("resources/"+ fileName)){
            p.load(is);

            for(String key: p.stringPropertyNames()){
                String value = p.get(key).toString();
                if(key!=null){
                    map.put(key,value);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /***
     * Saves the player's variables into the properties file
     *
     * @param player object of the PLayer class
     */
    public void writePlayer(Player player){
        Properties p = new Properties();
        InputStream is;
        OutputStream os;
        try {
            is = new FileInputStream("resources/Players.properties");
            p.load(is);
            os = new FileOutputStream("resources/Players.properties");
            p.setProperty(player.getLogin(), player.getLevel() + "," + player.getScore() + "," + player.getLunars()+","+
                    Player.addFuel + ","+Player.fullFuel + ","+ Player.suddenStop);
            p.store(os,null);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Parses the String into the Color
     *
     * @param s string, name of the color
     * @return variable (type:Color)
     */
    public Color parseColor(String s){
        Color newColor;

        try{
            newColor = Color.decode(s);
        }catch(NumberFormatException nfe) {
            try {
                Field field = Class.forName("java.awt.Color").getField(s);
                newColor = (Color) field.get(null);
            } catch (Exception e) {
                newColor = null;
            }
        }
        return newColor;
    }

    /***
     * Splits lines of read data and puts it into the float array.
     *
     * @param s data read form file
     * @return array o data (type: float)
     */
    public float[] splitStringToFloat(String s){
        String[] table;
        table=s.split(",");

        float[] inty = new float[table.length];

        for(int i=0; i<table.length; i++){
            inty[i] = Float.parseFloat(table[i]);
        }
        return inty;
    }

    /***
     * Splits lines of read data and puts it into the integer array.
     *
     * @param s data read form file
     * @return array o data (type: integer)
     */
    public int[] splitStringToInt(String s){
        String[] table;
        table=s.split(",");

        int[] inty = new int[table.length];

        for(int i=0; i<table.length; i++){
            inty[i] = Integer.parseInt(table[i]);
        }

        return inty;
    }




}
