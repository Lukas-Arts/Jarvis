package GUI;

import sun.misc.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lukas on 25.03.15.
 */
public class SidekickGUI extends JFrame {

    private JButton jb=new JButton(new ImageIcon("./img/Jarvis_Sidekick.png"));
    private JButton settings=new JButton(new ImageIcon("./img/Settings.png"));
    private JButton power=new JButton(new ImageIcon("./img/power-button-20.png"));
    public SidekickGUI()
    {
        this.setSize(80, 80);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit t=Toolkit.getDefaultToolkit();
        this.setLocation(t.getScreenSize().width - 80, t.getScreenSize().height / 2 - 40);
        this.setBackground(new Color(0, 255, 0, 0));

        this.setLayout(null);

        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setBounds(0, 0, 80, 80);

        settings.setContentAreaFilled(false);
        settings.setBorderPainted(false);
        settings.setBounds(62, 0, 20, 20);

        power.setContentAreaFilled(false);
        power.setBorderPainted(false);
        power.setBounds(62, 60, 20, 20);

        ActionListener al=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==jb)System.out.println("jb");
                    else if(e.getSource()==settings)System.out.println("settings");
                        else if(e.getSource()==power)System.out.println("power");
            }
        };
        jb.addActionListener(al);
        settings.addActionListener(al);
        power.addActionListener(al);
        this.add(settings);
        this.add(power);
        this.add(jb);
        this.setVisible(true);
    }
}
