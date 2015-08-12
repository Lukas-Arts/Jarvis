package GUI;

import API.*;
import API.GUI.AbstractSettingsPanel;

import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by lukas on 10.04.15.
 */
public class SettingsFrame extends JFrame
{
    private JTree settingsTree;
    private JPanel settingsPanel;
    private JPanel ok;
    private JFrame jf;
    public SettingsFrame()
    {
        super("Settings");
        this.setSize(500, 400);
        this.setMinimumSize(new Dimension(300,300));
        this.setLocationRelativeTo(null);
        this.setContentPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT));
        //this.setLayout(new BorderLayout());
        jf=this;
        DefaultMutableTreeNode root=new DefaultMutableTreeNode();
        root.add(new DefaultMutableTreeNode("Main"));
        root.add(new DefaultMutableTreeNode("Appearance"));
        root.add(new DefaultMutableTreeNode("Questions"));
        DefaultMutableTreeNode actions=new DefaultMutableTreeNode("Actions");
        for(String s:new File("./addons/actions").list())
        {
            System.out.println(s);
            if(new File("./addons/actions/"+s+"/"+s+"Settings.class").exists())
                actions.add(new DefaultMutableTreeNode(s));
        }
        root.add(actions);

        root.add(new DefaultMutableTreeNode("Addons"));
        root.add(new DefaultMutableTreeNode("Voice Recognition"));
        settingsTree=new JTree(root);
        settingsTree.setMinimumSize(new Dimension(100, 100));
        settingsTree.setRootVisible(false);
        settingsTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String selected = e.getNewLeadSelectionPath().getLastPathComponent().toString();
                System.out.println(selected);
                switch (selected) {
                    case "Main": {
                        settingsPanel.removeAll();
                        settingsPanel.add(new MainSettings(), BorderLayout.CENTER);
                        settingsPanel.add(ok, BorderLayout.SOUTH);
                        settingsPanel.revalidate();
                        //settingsPanel.repaint();
                        break;
                    }
                    case "Appearance": {
                        break;
                    }
                    case "Questions": {
                        settingsPanel.removeAll();
                        settingsPanel.add(new Questions(), BorderLayout.CENTER);
                        settingsPanel.add(ok, BorderLayout.SOUTH);
                        settingsPanel.revalidate();
                        break;
                    }
                    case "Actions": {
                        break;
                    }
                    default: {
                        String action = e.getPath().getLastPathComponent().toString();
                        try {
                            System.out.println("./addons/actions/" + action);
                            System.out.println(new File("addons/actions/" + action).toURL());
                            Class c = new URLClassLoader(new URL[]{new File("addons/actions/" + action).toURL()}).loadClass(action + "Settings");
                            System.out.println(c);
                            AbstractSettingsPanel asp = (AbstractSettingsPanel) c.newInstance();
                            settingsPanel.removeAll();
                            settingsPanel.add(asp, BorderLayout.CENTER);
                            settingsPanel.add(ok, BorderLayout.SOUTH);
                            settingsPanel.revalidate();
                            revalidate();
                        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | MalformedURLException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    }
                }
            }
        });
        JPanel jp=new JPanel(new BorderLayout());
        jp.add(new JTextField("search.."), BorderLayout.NORTH);     //TODO: search settings
        jp.add(settingsTree, BorderLayout.CENTER);
        this.add(jp);
        settingsPanel=new JPanel();
        settingsPanel.setPreferredSize(new Dimension(400,300));
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.add(new GoogleSpeechRecognitionSettings(), BorderLayout.CENTER);
        ok=new JPanel();
        ok.setLayout(new GridLayout(1, 4));
        JButton cancel=new JButton("Cancel");
        ok.add(new JLabel(""));
        ok.add(cancel);
        cancel.addActionListener(e -> {
            SettingsManager.loadProperties();
            jf.dispose();
        });
        ok.add(new JLabel(""));
        JButton apply=new JButton("Apply");
        ok.add(apply);
        apply.addActionListener(e -> {
            ((AbstractSettingsPanel)settingsPanel.getComponent(0)).save();
            SettingsManager.loadProperties();
            jf.dispose();
        });
        settingsPanel.add(ok,BorderLayout.SOUTH);
        this.add(settingsPanel);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
    }
}
