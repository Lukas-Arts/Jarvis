package GUI.Listeners;

import API.AbstractInputHandler;
import API.ActionExecuter;
import javafx.util.Pair;
import logic.Main;
import logic.RadixTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by lukas on 25.03.15.
 */
public class QuerryListener extends AbstractInputHandler implements ActionListener{
    private String action;
    private ArrayList<String> parameter;
    private static RadixTree r= Main.getQuestionTree();
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(()->{
            String cmd=((JTextField)e.getSource()).getText();
            newInput(cmd);
        }).start();
    }
    public static void redirectNextInput(AbstractInputHandler handler)
    {
        ih=handler;
    }

    @Override
    public void newInput(String cmd) {
        Main.setState(Main.PROCESSING);
        if(ih==null)
        {
            System.out.println("querrying for question.. ");
            action="";
            parameter=new ArrayList<>();
            System.out.println("> "+cmd);
            cmd=cmd.toLowerCase().replace("hey jarvis","").replace("hey computer","").replace("computer","").replace("jarvis","").replace("hey","");
            //more parameters -> give parameters numbers to indecate order.
            Pair<String,ArrayList<String>> result=r.search(cmd);
            if(result!=null) {
                action = result.getKey();
                parameter = result.getValue();
            }
            String s2="< "+action;
            System.out.println(s2);
            String s3[]=action.split("%");
            action=s3[0];
            if(s3.length>1)for(int i=s3.length-1;i>0;i--)parameter.add(0,s3[i]);
            //for(String s:parameter)s2+=" "+s;
            String s[]=new String[parameter.size()];
            parameter.toArray(s);
            System.out.println(parameter);
            System.out.println("< " + ActionExecuter.executeAction(action, s));
        }else ih.newInput(cmd);
        ih=null;
        Main.setState(Main.WAITING);
    }
}
