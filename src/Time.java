import API.AbstractAction;
import API.GUI.PopupHandler;
import API.SettingsManager;
import logic.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by lukas on 11.05.15.
 */
public class Time extends AbstractAction {
    @Override
    public String executeAction(String[] params) {
        switch (params[0])
        {
            case "time":{   //use format in pramas[1] if available
                String time=LocalTime.now().format(DateTimeFormatter.ofPattern((params.length > 1 ? params[1] : "H:m:s")));
                System.out.println(time);
                char[] outputmode=SettingsManager.getProperty("outputmode").toCharArray();
                if(outputmode[1]=='1')//popup
                {
                    JPanel jp=new JPanel(new BorderLayout());
                    JPanel jp2=new JPanel();
                    JLabel jl=new JLabel("Es ist "+time);
                    jl.setForeground(Color.WHITE);
                    jl.setHorizontalTextPosition(JLabel.CENTER);
                    jp2.setBackground(new Color(50, 50, 50));
                    jp2.add(jl);
                    jp.add(jp2, BorderLayout.CENTER);
                    jp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    jp.setBackground(new Color(0,0,0));
                    PopupHandler.createNewPopup(jp);
                }
                if(outputmode[2]=='1')//audio
                {
                    String t[]=time.split(":");
                    time=t[0]+" Uhr "+t[1]+" und "+t[2]+" Sekunden";
                    String cmd[]={"espeak","-v",SettingsManager.getLangCode(),"Es ist "+time};
                    ProcessBuilder pb=new ProcessBuilder(cmd);
                    pb.inheritIO();
                    try {
                        pb.start().waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "reminder":{
                if(params[1].equalsIgnoreCase("period"))
                {
                    try {
                        new Thread(new Timer(params[2],params[3],params[4])).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(params[1].equalsIgnoreCase("time")){
                    //TODO:
                }else if(params[1].equalsIgnoreCase("date")){
                    //TODO
                }
                break;
            }
            case "stop":{
                System.out.println("stop");
                break;
            }
            case "calendar":{
                break;
            }
        }
        return null;
    }

    @Override
    public String getInfo() {
        return  "time ([format])\n" +
                "reminder date/time/period ([period_format]) [data] [amount]\n" +
                "\t where period_format is either 'h', 'm', 's' or a combination of them separated by ':'\n" +
                "\t and amount is of an equal format"+
                "stop [time]\n" +
                "calendar [date] [start] [end] [name] ([opt_info])";

    }
    class Timer extends JDialog implements Runnable{
        private String data;
        private int h=0,m=0,s=0;
        private int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        private boolean showProgress=true;
        private int progress=0;
        private JDialog settings=null;
        public Timer(String period_format, String data, String amount) throws Exception {
            super();
            String s[]=period_format.split(":");
            String s2[]=amount.split(":");
            if(s.length!=s2.length)
                throw new Exception("amount must have the format given by period_format!");
            for(int i=0;i<s.length;i++){
                switch (s[i]){
                    case "h":{
                        this.h+=Integer.parseInt(s2[i]);
                        break;
                    }
                    case "m":{
                        this.m+=Integer.parseInt(s2[i]);
                        break;
                    }
                    case "s":{
                        this.s+=Integer.parseInt(s2[i]);
                        break;
                    }
                }
            }
            this.data=data;
        }
        public void run(){
            try {
                int time=(h * 60 * 60 + m * 60 + s) * 1000;
                double time2=time/width;
                if(showProgress){
                    this.add(new JLabel("asdf"));
                    this.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JDialog jd2 = new JDialog();
                            jd2.setTitle("Timer Settings");
                            JPanel jp = new JPanel();

                            jd2.add(jp);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

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
                    });
                    this.setAlwaysOnTop(true);
                    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    this.setUndecorated(true);
                    Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
                    this.setSize((int) d.getWidth(), 3);
                    this.setLocation(0, 0);
                    this.setVisible(true);
                }
                System.out.println("starting Timer: "+LocalTime.now().format(DateTimeFormatter.ofPattern("H:m:s")));
                for(progress=0;progress<width;progress++){
                    Thread.sleep((int)time2);
                    if(showProgress) this.repaint();
                }
                Thread.sleep(time - (int) time2 * width);
                if(showProgress) this.repaint();
                System.out.println("finished Timer: "+LocalTime.now().format(DateTimeFormatter.ofPattern("H:m:s")));
                char[] outputmode=SettingsManager.getProperty("outputmode").toCharArray();
                if(outputmode[1]=='1')//popup
                {
                    JPanel jp=new JPanel(new BorderLayout());
                    JPanel jp2=new JPanel();
                    JLabel jl=new JLabel(data+" ist Fertig!");
                    jp2.add(jl);
                    jp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                    jp.add(jp2,BorderLayout.CENTER);
                    jl.setForeground(new Color(255, 255, 255));
                    jp2.setBackground(new Color(50,50,50));
                    jp.setBackground(new Color(0, 0, 0, 0));
                    PopupHandler.createNewPopup(jp);
                }
                if(outputmode[2]=='1')//audio
                {
                    String cmd[]={"espeak","-v",SettingsManager.getLangCode(),data+" ist Fertig!"};
                    ProcessBuilder pb=new ProcessBuilder(cmd);
                    pb.inheritIO();
                    try {
                        pb.start().waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(showProgress) this.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void paint(Graphics g){
            g.setColor(new Color(0,0,0,0));
            g.fillRect(0, 0, width, 3);
            g.setColor(Color.BLUE);
            g.drawRect(0, 0, progress, 1);
            g.setColor(new Color(100, 150, 255));
            g.fillRect(progress-10, 0, 10, 2);
        }
    }
}
