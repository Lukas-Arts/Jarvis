package logic;

import API.GUI.PopupHandler;
import API.LanguageManager;
import GUI.GodmodeGUI;
import GUI.Listeners.GlobalHotkeyListener;
import logic.RadixTree;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by lukas on 25.03.15.
 */
public class Main {
    public static final int WAITING=0, PROCESSING =1,ERROR=2;
    private static RadixTree r;
    private static int state=WAITING;
    private static JFrame mainframe;
    public static void load()
    {
        r=new RadixTree();
        try {
            r.buildFromFile("./questions."+ LanguageManager.lang);
        } catch (IOException e) {
            System.err.println("Unable to build Question-Tree");
        }
    }
    public static RadixTree getQuestionTree()
    {
        return r;
    }
    public static JFrame getMainFrame()
    {
        return mainframe;
    }
    public static int  getState()
    {
        return state;
    }
    public static void setState(int state)
    {
        if(mainframe instanceof GodmodeGUI)
            ((GodmodeGUI)mainframe).changeState(state);
    }
    public static void main(String args[])
    {
        load();
        mainframe=new GodmodeGUI();


        JPanel jp=new JPanel();
        JLabel jl=new JLabel("Hallo");
        jl.setForeground(new Color(255, 255, 255));
        jp.setBackground(new Color(0, 0, 0, 0));
        jp.add(jl);
        PopupHandler.createNewPopup(jp);
        JEditorPane jep=new JEditorPane();
        jep.setText(" asdfadsf \n asdfölml \n öaldsöf\n test124");
        PopupHandler.createNewPopup(jep);
        PopupHandler.createNewPopup(jp);
    }
}
