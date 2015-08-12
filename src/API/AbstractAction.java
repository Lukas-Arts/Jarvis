package API;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This should be used by developers to create custom action (addons, plugins, or however you want to call it).
 *
 * There are a few rules to follow:
 * - Use SettingsManager to retrieve Settings
 * - Use LanguageManager so that questions and answers can be easily translated
 * - When using Popups to give user response:
 *      - Popups may be transparent, which doesn't work well with multiline-swing/text components.
 *        So if you have multiline-elements, use an extra Panel
 *      - Popup-Size is conent.getPreferredSize
 * - if you use external Libraries you have to edit the manifest-file in Jarvis.jar, so that the
 *   Library can be found on startup
 * - you can Execute other action with the ActionExecuter-Class
 * - you can redirect the users input to your own ActionInputHandler, so you can do some Bot-like
 *   Q/A-stuff, telling jokes, etc..
 * - if there are Settings to set for the Action, it should provide an SettingsPanel
 * - there may be some problems with anonym sub-classes
 *
 * Created by lukas on 25.03.15.
 */
public abstract class AbstractAction
{
    /**
     * Note that you have to read the Output of a process, when you start it!
     * Otherwise it might not execute properly!
     * e.g.: new ProcessBuilder(cmd).redirectOutput(File.createTempFile("out","")).start();
     * @param params    parameters for the action
     * @return          Jarvis' answer
     */
    public abstract String executeAction(String[] params);

    /**
     *  Output some instructions for the Usage of this action (parameters etc)
     */
    public String getUsage(){
        int i=0;
        String s=null;
        String usage="";
        try {
            BufferedReader br=new BufferedReader(new FileReader("./questions."+LanguageManager.lang));
            usage+="To use this action you have the following possible questions:";
            while((s=br.readLine())!=null)
            {
                if(s.contains("="+getClass().getName()))usage+="\n"+s;
            }
        } catch (IOException e) {
            usage+="\n no options for your language availiable, sorry.";
        }
        return usage;
    }

    /**
     * Information about how to use this action and what it does.
     * - amount of parameter(s)
     * - parameter description
     * - what does this action do?
     * - does this action what it does?
     * - what else is needed to know when building a new question for this action?
     * @return info
     */
    public abstract String getInfo();

    /**
     *  Signature for this Action to verify only approved Actions will be executed
     *  Leaf empty. Approver will fill it before publishing the code.
     */
    public static String getSignature()
    {
        return "asdf";
    }
}
