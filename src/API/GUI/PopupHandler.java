package API.GUI;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lukas on 16.04.15.
 *
 * Use this class to show your answer to the User ;)
 *
 * TODO: improve scrolling - maybe use an overlying frame..
 */
public class PopupHandler
{
    private static ArrayList<PopupFrame> popups=new ArrayList<>();
    private static boolean growsUp=false;
    private static boolean blocked=false;
    public static synchronized PopupFrame addPopup(PopupFrame pf)
    {
        //Move the other popups up/ down..
        popups.forEach(a -> a.move(pf.getHeight() + 10));
        //Popups need pf.getHeight()*10ms to move, so let's wait before we add the new one..
        if(popups.size()>0)try {
            Thread.sleep((pf.getHeight()+15)*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        popups.add(pf);
        pf.setVisible(true);
        //TODO: Main.getMainFrame().revalidate();
        return pf;
    }
    public static synchronized PopupFrame createNewPopup(Component content,int personalCloseTime)
    {
        System.out.println("starting " + popups.size());
        //Move the other popups up/ down..
        PopupFrame pf=new PopupFrame(content,personalCloseTime);
        moveAndWait(pf.getHeight()+10);
        pf.addMouseWheelListener(e -> {
            //System.out.println(e);
            new Thread(() -> moveAndWait(e.getWheelRotation() * 10)).start();
        });
        popups.add(pf);
        pf.setVisible(true);
        System.out.println("finished " + popups.size());
        //TODO: Main.getMainFrame().revalidate();
        return pf;
    }
    /**
     * Creates a new Popup with the specified content
     * Be aware that the Popup might be transparent. Therefore you,
     * as an developer, should make sure that the content may be
     * transparent aswell
     * @param content
     */
    public static synchronized PopupFrame createNewPopup(Component content)
    {
        return createNewPopup(content,-1);
    }
    public static void remove(PopupFrame p){

        while (blocked) try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        blocked=true;
        popups.remove(p);
        blocked=false;
    }
    public static void moveAndWait(int dy)
    {
        ArrayList<Thread> ths=new ArrayList<>();
        while (blocked) try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        blocked=true;

        popups.forEach(a ->{
                ths.add(a.move(dy));
            });
        System.out.println("waiting..");
        //popups.forEach(a -> a.setLocation((int) a.getLocation().getX(), resy));
        //Popups need pf.getHeight()*10ms to move, so let's wait before we add the new one..
        for(Thread th:ths)
            while(th.getState()!= Thread.State.TERMINATED)
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        System.out.println("fin waiting.. " + popups.size());
        blocked=false;
    }
    public static void moveAndWaitFromPopup(PopupFrame pf,int dy)
    {

        ArrayList<Thread> ths=new ArrayList<>();
        while (blocked) try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        blocked=true;

        popups.subList(0,popups.indexOf(pf)).forEach(a ->{
            ths.add(a.move(dy));
        });
        System.out.println("waiting..");
        //popups.forEach(a -> a.setLocation((int) a.getLocation().getX(), resy));
        //Popups need pf.getHeight()*10ms to move, so let's wait before we add the new one..
        for(Thread th:ths)
            while(th.getState()!= Thread.State.TERMINATED)
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        System.out.println("fin waiting.. " + popups.size());
        blocked=false;
    }
}
