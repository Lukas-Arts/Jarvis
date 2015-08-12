package logic;

/**
 * Created by lukas on 19.05.15.
 */
public class WeatherInfo {
    public String city,country,wind_name,wind_direction,clouds,last_update,sunrise,sunset;
    public int humidity;
    public double lon,lat,temperature,pressure,windspeed;
    public String toString(){
        return  "Weather in "+city+","+country+" ("+lon+","+lat+"):\n"+
                "temperature: "+temperature+", pressure: "+pressure+" humidity: "+humidity+"\n"+
                "wind: "+wind_name+" ("+windspeed+", "+wind_direction+")\n"+
                "clouds: "+clouds+", sunrise: "+sunrise+", sunset: "+sunset+"\n"+
                "last updated: "+last_update;
    }
}
