package LunarLander;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesManager {

    private static PropertiesManager instance;

    static{
        try{
            instance = new PropertiesManager();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static synchronized PropertiesManager getInstance(){return instance;}

    public static Properties p = new Properties();




    public Map<String,String> loadToMap(String fileName){
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

    public Font loadFont(String fontName){
        Font newFont = null;
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT,new File("resources/fonts/"+fontName+".ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("resources/fonts/"+fontName+".ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFont;
    }


}
