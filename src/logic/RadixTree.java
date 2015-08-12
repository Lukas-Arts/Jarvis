package logic;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lukas on 13.04.15.
 */
public class RadixTree
{
    private int size=0;
    private HashMap<String,RadixTreeNode> mainnodes=new HashMap<>();
    public void put(String question,String value)
    {
        put(question,value,0);
    }
    public void put(String question,String value,int times_requested)
    {
        String s2[]=question.toLowerCase().split(" ");
        RadixTreeNode n=mainnodes.get(s2[0]);
        if (n == null) {
            n=new RadixTreeNode(s2[0],(s2.length==1?value:null),new ArrayList<RadixTreeNode>(),times_requested);
            n.put(s2, 1, value,times_requested);
            mainnodes.put(s2[0],n);
        } else n.put(s2,1,value,times_requested);
        size++;
    }

    /**
     *
     * @param s The Question that should be searched for
     * @return  Empty String + (empty) ArrayList => question not in tree
     *          else string=action name arraylist=parameter list
     */
    public Pair<String,ArrayList<String>> search(String s)
    {
        RadixTreeNode n=mainnodes.get(s.toLowerCase().split(" ")[0]);
        if(n==null)return null;
            return n.search(s);
    }

    /**
     * @return an ArrayList<String> containing all questions in
     *      the format: question=answer | times_requested
     * @throws IOException
     */
    public ArrayList<String> getElements() throws IOException {
        ArrayList<String> strings=new ArrayList<>();
        for(Map.Entry n:mainnodes.entrySet())
        {
            strings.addAll(((RadixTreeNode)n.getValue()).save(""));
        }
        return strings;
    }
    public void save(File file) throws IOException {
        FileWriter out=new FileWriter(file);
        for(String s:getElements())out.write(s+"\n");
        out.close();
    }
    public void buildFromFile(String file) throws IOException {
        BufferedReader fr=new BufferedReader(new FileReader(file));
        String s;
        while((s=fr.readLine())!=null){
            if(!s.startsWith("#"))
            {
                //  '=' and '|' can't be used as parameters for String.split()
                //  therefore we have to do it manually..
                // note to self: maybe a while loop over the char array would be faster?
                int i=s.indexOf("=");
                String s2[]={s.substring(0,i),s.substring(i+1,s.length())};
                i=s2[1].indexOf("|");
                String s3[]={s2[1].substring(0,i-1),s2[1].substring(i+2, s2[1].length())};
                //for(String s4:s3)System.out.println(s4);
                if(s3.length>1)this.put(s2[0],s3[0],Integer.parseInt(s3[1]));
                    else this.put(s2[0],s3[0]);
                //System.out.println(s2[0]+" = "+s3[0]);
            }
        }

    }

    /**
     * just for testing propose
     * @param file
     * @throws IOException
     */
    @Deprecated
    public void save2(File file) throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        bw.write(this.toString());
        bw.flush();
        bw.close();
    }
    public void clear()
    {
        mainnodes.clear();
    }
    public String toString()
    {
        String s="";
        for(Map.Entry n:mainnodes.entrySet())s+=n.getValue().toString();
        return s;
    }
    public int getSize()
    {
        return size;
    }
    public static void main(String args[])
    {
        Properties p=new Properties();
        try {
            p.load(new FileReader("./questions.english"));
            RadixTree r=new RadixTree();
            for(Map.Entry e:p.entrySet())
            {
                System.out.println("in: "+e.getKey()+" | "+e.getValue());
                r.put(e.getValue().toString(),e.getKey().toString());
            }
            System.out.println(r.search("open asdf"));
            r.save(new File("./questions.tree"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
