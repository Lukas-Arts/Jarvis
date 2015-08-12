package API.GUI;

import API.SettingsManager;
import GUI.GodmodeGUI;
import logic.Main;
import sun.awt.dnd.SunDropTargetEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by lukas on 15.04.15.
 */
public class PopupFrame extends JDialog{
    private static int maxwidth=600,//maxheight=500,
            minx= Toolkit.getDefaultToolkit().getScreenSize().width/2-maxwidth/2,
            miny= (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/10)+135;
    private static Color background;
    protected static int closeTime=Integer.parseInt(SettingsManager.getProperty("PopupCloseTime"));
    private int personalCloseTime=closeTime;
    private Thread disposeThread;
    public PopupFrame(Component content, int closeTime)
    {
        this(content);
        this.personalCloseTime=(closeTime == -1?PopupFrame.closeTime:closeTime);
    }
    public PopupFrame(Component content)
    {
        super(Main.getMainFrame());
        String s[]=SettingsManager.getProperty("PopupBackgroundColor").split(",");
        PopupFrame.background=new Color(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]));
        this.setUndecorated(true);
        //this.setType(Type.UTILITY);
        this.setBackground(background);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.add(content);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==3)dispose();
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
        this.pack();
        this.setSize(new Dimension(maxwidth, content.getPreferredSize().height + 10));
        this.setLocation(minx, miny);
    }
    public static void setBackgroundColor(Color c)
    {PopupFrame.background=c;}

    /**
     * overwrite dispose so that the popup will be removed from the PopupHandler
     */
    @Override
    public void dispose()
    {
        PopupHandler.remove(this);
        try{
            super.dispose();
        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * overwrite setVisible so that the Popup will close after closeTime ms
     * @param b
     */
    @Override
    public void setVisible(boolean b)
    {
        super.setVisible(b);
        if(b&&disposeThread==null){
            disposeThread=new Thread(() -> {
                try {
                    Thread.sleep(personalCloseTime > closeTime ? personalCloseTime : closeTime);
                    //wait longer if mouseover
                    if(getMousePosition()!=null){
                        try {
                            while(this.getMousePosition()!=null)
                                Thread.sleep(100);
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    dispose();
                    setVisible(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            disposeThread.start();
        }
    }
    /**
     * Move the popup to y+dy, 1px per 10ms
     * @param dy
     */
    public synchronized Thread move(int dy)
    {
        //System.out.println(dy + " " + getY());
        //int resy=getY()+dy;
        int y=getY();
        int ysize=Toolkit.getDefaultToolkit().getScreenSize().height;

        Thread th=new Thread(() -> {
            if(dy>0)
            {
                for(int i=0;i<dy;i++)
                {
                    if(y+i>ysize-getHeight()-20 || y+i<miny){
                        i=dy;
                        this.setVisible(false);
                    }else this.setVisible(true);
                    this.setLocation(minx, y+i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                for(int i=0;i>dy;i--)
                {
                    if(y+i>ysize-getHeight()-20 || y+i<miny){
                        i=dy;
                        if(isVisible())this.setVisible(false);
                    }else if(!isVisible())this.setVisible(true);
                    this.setLocation(minx, y + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("finished moving..");
        });
        th.start();
        return th;
    }

    public static int getMaxwidth(){
        return maxwidth;
    }
}
