package GUI.Listeners;

import API.LanguageManager;
import logic.Main;
import logic.RadixTree;
import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by lukas on 14.04.15.
 */
public class WindowClosedListener implements WindowListener, ActionListener {
    private static JFrame jf;
    public WindowClosedListener(JFrame jf)
    {
        this.jf=jf;
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("test asdf 1234");
        try {
            Main.getQuestionTree().save(new File("./questions." + LanguageManager.lang));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Main.getQuestionTree().save(new File("./questions." + LanguageManager.lang));
            Main.getQuestionTree().save2(new File("./test2.txt"));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        jf.dispose();
    }
}
