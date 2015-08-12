package logic;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by lukas on 26.03.15.
 */
public class ActionInstaller
{
    public static boolean installAction(String zipfilepath)
    {
        try {
            //extract zipfile
            ZipFile zp=new ZipFile(zipfilepath);
            ZipEntry ze=null;
            while((ze=zp.entries().nextElement())!=null)
            {
                if(!ze.isDirectory())
                {
                    FileWriter fw=new FileWriter("./addons/action/"+zp.getName()+"/"+ze.getName());
                    BufferedReader is=new BufferedReader(new InputStreamReader(zp.getInputStream(ze)));
                    String s=null;
                    while((s=is.readLine())!=null)
                    {
                        fw.write(s);
                    }
                }
            }
            //TODO: execute install script if available. gives developers possibility to install any dependencies
            //TODO: merge questions with existing to ./question.%lang%
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
