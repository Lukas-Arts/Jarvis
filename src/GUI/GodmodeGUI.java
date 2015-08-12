package GUI;

import GUI.Listeners.ActivateButtonListener;
import GUI.Listeners.QuerryListener;
import GUI.Listeners.GlobalHotkeyListener;
import GUI.Listeners.WindowClosedListener;
import logic.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by lukas on 25.03.15.
 */
public class GodmodeGUI extends JFrame {
    private static JButton jb=new JButton(new ImageIcon("./img/Jarvis_Godmode.png"));
    private JButton settings=new JButton(new ImageIcon("./img/Settings.png"));
    private JButton power=new JButton(new ImageIcon("./img/power-button-20.png"));
    private JTextField jtf=new JTextField();
    private int width=400,height=130;
    private WindowClosedListener wcl;
    public GodmodeGUI()
    {
        super("JARVIS");
        this.setSize(width, height);
        this.setUndecorated(true);
        this.wcl=new WindowClosedListener(this);
        this.addWindowListener(wcl);
        Toolkit t=Toolkit.getDefaultToolkit();
        this.setLocation(t.getScreenSize().width / 2 - width / 2, t.getScreenSize().height / 10);
        this.setBackground(new Color(0, 255, 0, 0));

        this.setLayout(null);

        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setMaximumSize(new Dimension(80, 80));
        jb.setBounds(width / 2 - 40, 0, 80, 80);
        jb.setLayout(null);

        settings.setContentAreaFilled(false);
        settings.setBorderPainted(false);
        settings.setBounds(0, 62, 20, 20);

        power.setContentAreaFilled(false);
        power.setBorderPainted(false);
        power.setBounds(60, 62, 20, 20);

        jtf.setBounds(0, 80, width, 50);
        ActionListener al= e -> {
            if(e.getSource()==settings){
                new SettingsFrame();
                System.out.println("settings");
            }
            else if(e.getSource()==power){
                openExitDialog();
                System.out.println("power");
            }
        };
        QuerryListener ql=new QuerryListener();
        ActionListener abl=new ActivateButtonListener(jtf);
        jb.addActionListener(abl);
        settings.addActionListener(al);
        power.addActionListener(al);
        jtf.addActionListener(ql);
        jb.add(settings);
        jb.add(power);
        this.add(jb);
        this.add(jtf);
        this.setVisible(true);

        String s[]={"Alt_L","q"};
        new GlobalHotkeyListener(s).addActionListener(abl);
        /*
        JFrame jf=new JFrame();
        jf.setSize(500, 500);
        JEditorPane jep=new JEditorPane("text/html","<html><body><p>hallo welt!</p></body></html>");
        jf.add(jep);
        jf.setVisible(true);
        */
    }

    /**
     * show state via icon
     * @param state
     */
    public void changeState(int state)
    {
        switch (state){
            case Main.WAITING:{
                if(Main.getState()!=Main.ERROR)jb.setIcon(new ImageIcon("./img/Jarvis_Godmode.png"));
                break;
            }
            case Main.PROCESSING:{
                jb.setIcon(new ImageIcon("./img/Jarvis_Godmode_blue.png"));
                break;
            }
            case Main.ERROR:{
                jb.setIcon(new ImageIcon("./img/Jarvis_Godmode_red.png"));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jb.setIcon(new ImageIcon("./img/Jarvis_Godmode.png"));
                break;
            }
        }
        System.out.println("state changed");
        jb.revalidate();
        this.revalidate();
    }
    private void openExitDialog()
    {
        JDialog jd=new JDialog(this,"Quit?");
        JPanel jp=new JPanel(new BorderLayout());
        JLabel jl=new JLabel("    Do you really want to exit?");
        jp.add(jl, BorderLayout.CENTER);
        JPanel jp2=new JPanel(new BorderLayout());
        JButton yes=new JButton("Yes");
        yes.addActionListener(wcl);
        jp2.add(yes,BorderLayout.WEST);
        jp2.add(new JLabel("    "),BorderLayout.CENTER);
        JButton no=new JButton("No");
        no.addActionListener(e -> jd.dispose());
        jp2.add(no,BorderLayout.EAST);
        jp2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jp.add(jp2, BorderLayout.SOUTH);
        jd.setContentPane(jp);

        jd.setSize(225,110);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
    }
}
