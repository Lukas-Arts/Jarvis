package API;

import com.sun.istack.internal.Nullable;
import logic.Main;
import logic.Verifyer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by lukas on 27.03.15.
 */
public class ActionExecuter {
    public static String executeAction(String action, String[] params)
    {
        String response=null;
        try {
            //load class capable of handling the action
            Class c=new URLClassLoader(new URL[]{new File("./addons/actions/"+action).toURL()}).loadClass(action);
            //verify signature before creating the new instance,
            //that's important for the whole signature-thing..
            //System.out.println("signature: "+c.getField("signature").get(null));
            if(SettingsManager.getProperty("devmode").equalsIgnoreCase("1") || Verifyer.verify(c.getField("signature").toString()))
            {
                AbstractAction ai=(AbstractAction)c.newInstance();
                //execute action with parameters
                response=ai.executeAction(params);
            } else System.err.println("Error: permission denied! Action '"+action+"' isn't signed and therefore unoffical or corrupted. \n" +
                    "Change to devmode if you still want to use this action ;)");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException | MalformedURLException e1) {
            System.err.println("Error: action '" + action + "' not found");
            Main.setState(Main.ERROR);
        }
        return response;
    }
}
