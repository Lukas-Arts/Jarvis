package GUI;

import API.GUI.AbstractSettingsPanel;
import API.LanguageManager;
import logic.Main;
import logic.RadixTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

/**
 * Created by lukas on 11.04.15.
 */
public class Questions extends AbstractSettingsPanel{
    private JTable table1;
    private JPanel panel1;
    private JScrollPane scrollpane;
    int i=0;
    public Questions()
    {
        DefaultTableModel tm= (DefaultTableModel) table1.getModel();
        try {
            i=0;
            RadixTree r= Main.getQuestionTree();
            tm.setRowCount(r.getSize() + 20);
            tm.setColumnCount(2);
            //get questions as 'question=answer | times_used' and iterate through..
            r.getElements().forEach((a) -> {
                int j=a.indexOf("=");
                String s2[]={a.substring(0,j),a.substring(j+1,a.length())};
                j=s2[1].indexOf("|");
                String s3[]={s2[1].substring(0,j-1),s2[1].substring(j+2, s2[1].length())};
                tm.setValueAt(s2[0], i, 0);
                tm.setValueAt(s3[0], i, 1);
                i++;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] names={"Question","Action"};
        tm.setColumnIdentifiers(names);
        table1.setModel(tm);
        //Combobox for actions-Column
        File f=new File("./addons/actions/");
        f.list();
        table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox<String>(f.list())));
        scrollpane.createHorizontalScrollBar();
        scrollpane.createVerticalScrollBar();
        this.setLayout(new BorderLayout());
        this.add(panel1,BorderLayout.CENTER);
    }
    @Override
    public boolean save() {
        try {
            RadixTree r=Main.getQuestionTree();
            r.clear();
            for(int i=0;i<table1.getModel().getRowCount();i++)
            {
                Object key=table1.getModel().getValueAt(i,1);
                Object value=table1.getModel().getValueAt(i,0);
                if (key!=null&&value!=null)
                {
                    r.put(value.toString(),key.toString());
                }
            }
            r.save(new File("./questions." + LanguageManager.lang));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
