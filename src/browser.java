import API.AbstractAction;
import API.GUI.PopupHandler;
import API.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by lukas on 25.03.15.
 */
public class browser extends AbstractAction {

    /**
     * TODO: maybe better use                     Desktop.getDesktop().browse(new URI("http://"));
     * @param params    parameters for the action
     * @return
     */
    @Override
    public String executeAction(String[] params) {
        String response="";
        switch (params.length)
        {
            case 0: {
                try {
                    BufferedReader is=new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("xdg-settings get default-web-browser").getInputStream()));
                    String browser=is.readLine().replace(".desktop", "");
                    Process p=new ProcessBuilder(browser).inheritIO().start();
                    p.waitFor();
                    p.destroy();
                    response=LanguageManager.getAnswer("./addons/actions/browser/", "startbrowser").replace("%s", browser);
                } catch (IOException e) {
                    response=LanguageManager.getAnswer("./addons/actions/browser/", "sorry");
                } catch (InterruptedException e) {
                    System.err.println("problem waiting for process");
                }
                break;
            }
            case 1: {
                params[0]=params[0].replace(" dot ",".").replace(" slash ","/").replace(" minus ","-");
                if(!params[0].startsWith("http://"))params[0]="http://"+params[0];
                try {
                    String[] s={"xdg-open", params[0]};
                    Process p=new ProcessBuilder(s).inheritIO().start();
                    p.waitFor();
                    p.destroy();
                    response=LanguageManager.getAnswer("./addons/actions/browser/", "opensite");
                } catch (IOException e) {
                    response=LanguageManager.getAnswer("./addons/actions/browser/", "sorry");
                } catch (InterruptedException e) {
                    System.err.println("problem waiting for process");
                }
                break;
            }
            default:{
                response=LanguageManager.getAnswer("./addons/actions/browser/", "unknownparams");
                break;
            }
        }
        JPanel jp=new JPanel();
        JLabel jl=new JLabel(response);
        jp.add(jl);
        jl.setForeground(new Color(255, 255, 255));
        jp.setBackground(new Color(0, 0, 0, 0));
        PopupHandler.createNewPopup(jp);
        return response;
    }

    @Override
    public String getInfo() {
        return  "Browser Action: \n" +
                "- 0 parameters: start new frame of standard browser\n" +
                "- 1 parameter: start website extracted from parameter in new tab of standard browser\n"+
                "uses xdg-settings and xdg-open on *nix-OS";

    }
}
