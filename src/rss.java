import API.AbstractAction;
import API.GUI.PopupHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * Created by lukas on 18.04.15.
 */
public class rss extends AbstractAction {
    private static boolean shortnews=true;
    private JPanel panel1;

    /**
     * @param params    parameters for the action
     * @return
     */
    @Override
    public String executeAction(String[] params) {
        String feedname=params[0];
        System.out.println(feedname);
        //get feed url
        Feed f=null;
        try {
            BufferedReader br=new BufferedReader(new FileReader("./addons/actions/rss/feeds.txt"));
            String s2;
            while((s2=br.readLine())!=null)
                if(s2.toLowerCase().contains(feedname.toLowerCase())){
                    //url found -> read feed
                    f=new RSSFeedParser(s2).readFeed();
                    break;
                }
            br.close();
        } catch (IOException e) {
            System.err.println("Couldn't find ./addons/actions/rss/feeds.txt!");
        }
        //show feed messages
        if (f != null) {
            List<FeedMessage> fms=f.getMessages();
            if(!shortnews)
            {
                //reverse so that the first message will be on top, since popups grow from top to bottom
                Collections.reverse(fms);
                for(int i=fms.size()-5;i<fms.size();i++)
                {
                    FeedMessage fm=fms.get(i);
                    JPanel jp3=new JPanel();
                    MouseListener ml=new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            if(e.getButton()==1){
                                try {
                                    Desktop.getDesktop().browse(new URI(fm.getGuid()));
                                } catch (IOException | URISyntaxException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    };
                    jp3.setLayout(new BorderLayout());
                    JTextPane jl=new JTextPane();
                    jl.setEnabled(false);
                    jl.setText(fm.getTitle());
                    jl.setFont(new Font("", Font.BOLD, 16));
                    jl.setForeground(Color.WHITE);
                    jl.setBackground(new Color(50, 50, 50));
                    jl.addMouseListener(ml);
                    jp3.add(jl, BorderLayout.NORTH);
                    JTextPane jl2=new JTextPane();
                    jl2.addMouseListener(ml);
                    jl2.setText(fm.getDescription().substring(0, fm.getDescription().length() - 1));
                    jl2.setEnabled(false);
                    jl2.setFont(new Font("", Font.PLAIN, 12));
                    jl2.setForeground(Color.WHITE);
                    jl2.setBackground(new Color(50, 50, 50));
                    jp3.add(jl2, BorderLayout.CENTER);
                    jp3.setBackground(new Color(0, 0, 0, 0));
                    jp3.setPreferredSize(new Dimension(400, 140));
                    jp3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    //jp2.add(jp3);
                    PopupHandler.createNewPopup(jp3, 10000);
                    //pf.setSize(400, 75);
                    //PopupHandler.addPopup(pf);
                }
            }else{
                JPanel jp2=new JPanel(new BorderLayout());
                jp2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                jp2.setBackground(new Color(0, 0, 0, 0));
                JPanel jp=new JPanel();
                jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
                jp.setBackground(new Color(50, 50, 50));
                for(int i=0;i<15;i++){
                    FeedMessage fm=fms.get(i);
                    System.out.println(fm);
                    JButton jb=new JButton(fm.getTitle());
                    jb.setFocusPainted(false);
                    jb.setBorderPainted(false);
                    jb.setContentAreaFilled(false);
                    jb.setForeground(Color.WHITE);
                    jb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    jb.addActionListener(e -> {
                        try {
                            Desktop.getDesktop().browse(new URI(fm.getGuid()));
                        } catch (IOException | URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    });
                    jp.add(jb);
                }
                jp2.add(jp,BorderLayout.CENTER);
                PopupHandler.createNewPopup(jp2,10000);
            }
        } else {
            System.err.println("No "+feedname+" in Index");
        }

        //JComponents seem to have problems with transparent background & interaction
            //-> non-transparent background


        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
