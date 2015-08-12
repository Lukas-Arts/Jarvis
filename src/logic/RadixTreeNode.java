package logic;

import javafx.util.Pair;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lukas on 13.04.15.
 */
public class RadixTreeNode {

    protected String key, value;
    private ArrayList<RadixTreeNode> nodes;
    protected int times_requested=0;
    public RadixTreeNode(String question, String value, ArrayList<RadixTreeNode> nodes){
        this.key=question;
        this.value=value;
        this.nodes=nodes;
    }
    public RadixTreeNode(String question, String value, ArrayList<RadixTreeNode> nodes,int times_requested){
        this.key=question;
        this.value=value;
        this.nodes=nodes;
        this.times_requested=times_requested;
    }
    public Pair<String,ArrayList<String>> search(String s)
    {
        return search(s.split(" "), 0, new ArrayList<String>());
    }
    public Pair<String,ArrayList<String>>
        search(String[] s, int index, ArrayList<String> params)
    {
        Pair<String,ArrayList<String>> result=null;
        if(s.length>index)switch(this.getKey())
        {
            case "%s":{
                params.add(s[index]);
                if(index==s.length-1 && this.getValue()!=null)   //finish - found some result
                {
                    this.times_requested++;
                    return new Pair<String,ArrayList<String>>(getValue(),params);
                }
                else {
                    index++;
                    for(RadixTreeNode n:nodes)
                    {
                        result=n.search(s,index,params);
                        if(result!=null)
                            return result;
                    }
                }
                break;
            }
            case "%ws":{
                if(index==s.length-1 && this.getValue()!=null)   //finish - found some result
                {
                    //System.out.println(s[index]+"  |  "+this.getValue());
                    params.add(s[index]);
                    this.times_requested++;
                    return new Pair<String,ArrayList<String>>(getValue(),params);
                }
                else {
                    for(RadixTreeNode n:nodes)
                    {
                        String param="";
                        for(int i=index;i<s.length;i++)
                        {
                            if(n.getKey().equalsIgnoreCase(s[i]))
                            {
                                params.add(param.substring(0,param.length()-1));
                                index++;
                                result=n.search(s,index,params);
                                if(result!=null)return result;
                            }
                            param+=s[i]+" ";
                        }
                    }
                }
                break;
            }
            default:{
                if(this.getKey().equalsIgnoreCase(s[index]))
                {
                    if(index==s.length-1 && this.getValue()!=null)   //finish - found some result
                    {
                        this.times_requested++;
                        return new Pair<String, ArrayList<String>>(getValue(), params);
                    }
                    else {
                        index++;
                        for(RadixTreeNode n:nodes)
                        {
                            result=n.search(s,index,params);
                            if(result!=null)return result;
                        }
                    }
                }
                break;
            }
        }
        return result;
    }
    public void put(String[] s,int index,String value)
    {
        put(s,index,value,0);
    }
    public void put(String[] s,int index,String value,int times_requested)
    {
        if(index<s.length)
        {
            int i=-1;
            //check if node is already in list
            for(int j=0;j<nodes.size();j++)
                if(nodes.get(j).getKey().equalsIgnoreCase(s[index]))i=j;
            if(i==-1){
                RadixTreeNode node=new RadixTreeNode(s[index],(index==s.length-1?value:null),new ArrayList<RadixTreeNode>(),(index==s.length-1?times_requested:0));
                node.put(s,index+1,value,times_requested);
                nodes.add(node);
            }else {
                if(index==s.length-1){
                    nodes.get(i).value=value;
                    nodes.get(i).times_requested=times_requested;
                }
                nodes.get(i).put(s,index+1,value,times_requested);
            }
        }
    }
    public ArrayList<String> save(String s) throws IOException {
        //System.out.println(this.getKey()+" | "+times_requested+" | "+this.nodes.size());
        if(this.getValue()!=null)
        {
            ArrayList<String> strings=new ArrayList<>();
            strings.add(s + this.getKey() + "=" + this.getValue() + " | " + times_requested);
            if(this.nodes.size()==0)
                return strings;
        }
        s+=this.getKey()+" ";
        ArrayList<String> strings=new ArrayList<String>();
        for(RadixTreeNode n:nodes)
            strings.addAll(n.save(s));
        return strings;
    }
    public String toString()
    {
        String s=this.getKey()+"[";
        for(RadixTreeNode n:nodes)s+=n.toString();
        s+="]";
        return s;
    }
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public ArrayList<RadixTreeNode> getNodes() {
        return nodes;
    }
}
