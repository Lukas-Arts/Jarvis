package API;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * Created by lukas on 26.03.15.
 */
public class LanguageManager
{
    public static String lang=SettingsManager.getProperty("lang");
    public static String getAnswer(String path, String question)
    {
        return getAnswer(path,question,lang);
    }
    private static String getAnswer(String path, String question,String lang)
    {
        String value;
        try {
            Properties p=new Properties();
            FileReader fr=new FileReader(path+"answers."+lang);
            p.load(fr);
            fr.close();
            //Amount of answers for this case?
            value=p.getProperty(question);
            //Choose on of the answers randomly
            Random r=new Random();
            value=p.getProperty(question+r.nextInt(Integer.parseInt(value)));
        } catch (IOException e) {
            value=null;
        }
        if(value==null)
        {
            //if not available in default language, test for english version.
            if(lang.equalsIgnoreCase("english"))System.err.println("Error: unable to load answers for "+question+" in "+path+"answers."+lang);
                else {
                System.err.println("Error: unable to load answers for "+question+" in "+path+"answers."+lang+". Trying to get english answer...");
                return getAnswer(path,question,"english");
            }
        }
        return value;
    }
}
