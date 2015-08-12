package API;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lukas on 26.03.15.
 */
public class SettingsManager {
    private static Properties p;
    public SettingsManager()
    {
        loadProperties();
    }
    public static void loadProperties()
    {
        try {
            p=new Properties();
            FileReader  fr=new FileReader("./settings.properties");
            p.load(fr);
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: unable to laod settings");
            p=null;
        }
    }
    public static String getProperty(String key)
    {
        String value;
        if(p==null)loadProperties();
        value=p.getProperty(key);
        if(value==null)System.err.println("Error: unable to laod key: "+key);
        return value;
    }
    public static void setProperty(String key,String value)
    {
        if(p==null)loadProperties();
        p.setProperty(key, value);
    }
    public static boolean storePropierties() {
        if(p!=null)
            try {
                p.store(new FileWriter("./settings.properties"),"");
                return true;
            } catch (IOException e) {
                return false;
            }
        else return false;
    }
    public static String getLangCode(){
        String lang=SettingsManager.getProperty("lang");
        return getLangCode(lang);
    }
    public static String getLangCode(String lang){
        switch (lang){
            case "afrikaans":return "af";
            case "aragonese":return "an";
            case "bulgarian":return "bg";
            case "bosnian":return "bs";
            case "catalan":return "ca";
            case "czech":return "cs";
            case "danish":return "da";
            case "german":return "de";
            case "greek":return "el";
            case "english":return "en";
            case "english-us":return "en-us";
            case "esperanto":return "eo";
            case "spanish":return "es";
            case "finnish":return "fi";
            case "french-Belgium":return "fr-be";
            case "french":return "fr-fr";
            case "hindi":return "hi";
            case "croatian":return "hr";
            case "hungarian":return "hu";
            case "armenian":return "hy";
            case "indonesian":return "id";
            case "icelandic":return "is";
            case "italian":return "it";
            case "kannada":return "kn";
            case "kurdish":return "ku";
            case "latin":return "la";
            case "dutch":return "nl";
            case "norwegian":return "no";
            case "punjabi":return "pa";
            case "polish":return "pl";
            case "brazil":return "pt-br";
            case "portugal":return "pt-pt";
            case "russian":return "ru";
            case "swedish":return "sv";
            case "turkish":return "tr";
            case "vietnam":return "vi";
            case "Mandarin":return "zh";
            case "cantonese":return "zh-yue";
            default:return "en";
        }
    }
}
