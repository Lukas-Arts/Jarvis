package GUI.Listeners;

import API.SettingsManager;
import API.SpeechRecognitionInterface;
import logic.GoogleSpeechRecognition;
import logic.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by lukas on 25.03.15.
 */
public class ActivateButtonListener implements ActionListener{
    private JTextField jtf;
    private static int recTime=Integer.parseInt(SettingsManager.getProperty("recTime"));
    public ActivateButtonListener(JTextField jtf)
    {
        this.jtf=jtf;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ((JFrame)jtf.getRootPane().getParent()).toFront();
        System.out.println("starting recording (" + recTime + "ms)");
        Main.setState(Main.PROCESSING);
        Thread th=new Thread(()->{
            try {
                //recording recTime ms
                final String audioFile="temp.wav";
                SpeechRecognitionInterface sr=new GoogleSpeechRecognition();
                Process p=Runtime.getRuntime().exec("rec --encoding signed-integer --bits 16 --channels 1 --rate 16000 "+audioFile);
                int i=0;
                while(i<recTime){
                    Thread.sleep(10);
                    i++;
                }
                p.destroy();
                System.out.println("recording finished");
                jtf.setText(sr.processAudio(audioFile));
            } catch (IOException | InterruptedException e1) {
                System.err.println("Error while recording or recognition!");
                Main.setState(Main.ERROR);
            }
            jtf.getActionListeners()[0].actionPerformed(new ActionEvent(jtf, 0, ""));
            System.out.println("finished");
            Main.setState(Main.WAITING);
        });
        th.start();
    }
}
