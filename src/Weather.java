import API.AbstractAction;
import logic.WeatherInfo;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lukas on 19.05.15.
 */
public class Weather extends AbstractAction {
    @Override
    public String executeAction(String[] params) {
        if(params[0].equalsIgnoreCase("now")){
            try {
                HttpURLConnection connection= (HttpURLConnection)
                        new URL("http://api.openweathermap.org/data/2.5/weather?q=Groß-Umstadt,de&units=metric&mode=xml").openConnection();
                DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc= db.parse(connection.getInputStream());
                NodeList nl=doc.getDocumentElement().getChildNodes();
                WeatherInfo wi=new WeatherInfo();
                for(int i=0;i<nl.getLength();i++){
                    Node n=nl.item(i);
                    NamedNodeMap nnm=n.getAttributes();
                    System.out.println(n.getNodeName());
                    switch (n.getNodeName())
                    {
                        case "city":{
                            wi.city=nnm.getNamedItem("name").getNodeValue();
                            wi.lon=Double.parseDouble(n.getChildNodes().item(1).getAttributes().getNamedItem("lon").getNodeValue());
                            wi.lat = Double.parseDouble(n.getChildNodes().item(1).getAttributes().getNamedItem("lat").getNodeValue());
                            wi.sunrise=n.getChildNodes().item(5).getAttributes().getNamedItem("rise").getNodeValue();
                            wi.sunset=n.getChildNodes().item(5).getAttributes().getNamedItem("set").getNodeValue();
                            wi.country=n.getChildNodes().item(3).getChildNodes().item(0).getNodeValue();
                            System.out.println(wi.country);
                            break;
                        }
                        case "temperature":{
                            wi.temperature=Double.parseDouble(nnm.getNamedItem("value").getNodeValue());
                            break;
                        }
                        case "pressure":{
                            wi.pressure=Double.parseDouble(nnm.getNamedItem("value").getNodeValue());
                            break;
                        }
                        case "humidity":{
                            wi.humidity=Integer.parseInt(nnm.getNamedItem("value").getNodeValue());
                            break;
                        }
                        case "clouds":{
                            wi.clouds=nnm.getNamedItem("name").getNodeValue();
                            break;
                        }
                        case "lastupdate":{
                            wi.last_update=nnm.getNamedItem("value").getNodeValue();
                            break;
                        }
                        case "wind":{
                            System.out.println("asdf "+ n.getChildNodes().item(3));
                            wi.windspeed=Double.parseDouble(n.getChildNodes().item(1).getAttributes().getNamedItem("value").getNodeValue());
                            wi.wind_name=n.getChildNodes().item(1).getAttributes().getNamedItem("name").getNodeValue();
                            wi.wind_direction=n.getChildNodes().item(3).getAttributes().getNamedItem("code").getNodeValue();
                        }
                    }
                }
                System.out.println(wi);
            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
