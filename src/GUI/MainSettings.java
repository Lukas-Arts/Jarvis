package GUI;

import API.GUI.AbstractSettingsPanel;
import API.LanguageManager;
import API.SettingsManager;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Created by lukas on 10.04.15.
 */
public class MainSettings extends AbstractSettingsPanel{
    private JComboBox<String> lang;
    private JCheckBox devmode;
    private JFormattedTextField recTime;
    private JComboBox speechRecognition;
    private JPanel speechRecSettings;
    private JPanel rootPane;
    private JPanel speech;

    public MainSettings() {
        super();
        speech.setBorder(BorderFactory.createTitledBorder("Speech Recognition"));
        recTime.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        devmode.setSelected(SettingsManager.getProperty("devmode").equalsIgnoreCase("1"));
        for(String s:SettingsManager.getProperty("languages").split(", "))lang.addItem(s);
        lang.setSelectedItem(LanguageManager.lang);
        System.out.println(SettingsManager.getProperty("recTime"));
        recTime.setValue(Integer.parseInt(SettingsManager.getProperty("recTime")));
        recTime.setEditable(true);
        this.setLayout(new BorderLayout());
        this.add(rootPane,BorderLayout.CENTER);
    }

    @Override
    public boolean save() {
        SettingsManager.setProperty("lang",lang.getSelectedItem().toString());
        LanguageManager.lang=lang.getSelectedItem().toString();
        SettingsManager.setProperty("devmode",(devmode.isSelected()?"1":"0"));
        SettingsManager.setProperty("recTime",recTime.getText().replace(" ","").replace(".",""));
        return SettingsManager.storePropierties();
    }
}
