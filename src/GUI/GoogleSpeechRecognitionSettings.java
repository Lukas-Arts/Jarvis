package GUI;

import API.GUI.AbstractSettingsPanel;
import API.SettingsManager;

import javax.swing.*;

/**
 * Created by lukas on 10.04.15.
 */
public class GoogleSpeechRecognitionSettings extends AbstractSettingsPanel
{
    JTextField apiKey= new JTextField(SettingsManager.getProperty("google_api_key"));
    public GoogleSpeechRecognitionSettings()
    {
        SpringLayout springLayout=new SpringLayout();
        this.setLayout(springLayout);
        JLabel keytext=new JLabel("(*)Google-API-Key: ");
        this.add(keytext);
        this.add(apiKey);
        JLabel note=new JLabel("*)how to get it: http://...");
        this.add(note);

        springLayout.putConstraint(SpringLayout.WEST, keytext,
                5,
                SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, keytext,
                5,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, apiKey,
                5,
                SpringLayout.EAST, keytext);
        springLayout.putConstraint(SpringLayout.NORTH, apiKey,
                5,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, apiKey,
                -5,
                SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.WEST, note,
                5,
                SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, note,
                5,
                SpringLayout.SOUTH, apiKey);
    }
    @Override
    public boolean save() {
        return false;
    }
}
