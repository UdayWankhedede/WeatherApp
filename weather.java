import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.transform.OutputKeys;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;







class GetWeatherInfo {
    public double getKelvinToCelcius(String temp_in_kel) {
        float f = Float.parseFloat(temp_in_kel);  
        double m = (f - 273.15);
        return m;
    }

    public String getWeatherData(String cityname) throws IOException, InterruptedException 
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.openweathermap.org/data/2.5/weather?q=" + cityname
                        + "&appid=706b0fafb83fcc2d2d73af22f390bd6c"))
                .POST(BodyPublishers.ofString(cityname))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());

        HashMap<String, Object> map = new Gson().fromJson(response.body(), HashMap.class);
        // System.out.println(map);
        String cityName = (String) map.get("name");
        // String temprature = (String) map.get("main");
        // System.out.println(temprature);
        LinkedTreeMap tempinfo = (LinkedTreeMap) map.get("main");

        // float tempreture = ((Float) tempinfo.get("temp"));
        String cel_temp_str = tempinfo.get("temp").toString();
        double cel_temp = getKelvinToCelcius(cel_temp_str);   //cel temp in double format
        // System.out.println(cel_temp);

        // float tempMax = ((Float)tempinfo.get("temp_max"));
        String cel_maxtemp_str = tempinfo.get("temp_max").toString();
        double cel_tempMax = getKelvinToCelcius(cel_maxtemp_str);
        // System.out.println(cel_tempMax);

        // float tempMin = ((Float)tempinfo.get("temp_min"));
        String cel_mintemp_str = tempinfo.get("temp_min").toString();
        double cel_tempMin = getKelvinToCelcius(cel_mintemp_str);
        // System.out.println(cel_tempMin);

        // float humidity = ((Float)tempinfo.get("humidity"));
        String humidity_str = tempinfo.get("humidity").toString();

        // float pressure = ((Float)tempinfo.get("pressure"));
        String pressure_str = tempinfo.get("pressure").toString();

        System.out.println(cel_temp_str + "°C");
        System.out.println(cel_maxtemp_str + "°C");
        System.out.println(cel_mintemp_str + "°C");
        System.out.println(humidity_str + "%");
        System.out.println(pressure_str + "hpa");

        // String outputstring = String.format("Weather information for %s" + "temprature is: %s °C" + "min and max temp %s °C and %s °C" +"humidity: %s %" + "and pressure %s hPa",cityName,cel_temp_str,cel_mintemp_str,cel_maxtemp_str,humidity_str,pressure_str);
        // String outputstring = String.format("Weather information for %s is humidity: %s % and pressure %s hPa",cityName,humidity_str,pressure_str);
        // System.out.println("humidity is "+humidity_str+" %");
        String s = String.format("weatherinfo for city%s\n tempreture is%.2f °C\n max tempreture is%.2f °C\n min tempreture is%.2f °C\n humidity is%s %\n and pressure is %s hPa\n"
        , cityName,cel_temp,cel_tempMax,cel_tempMin,humidity_str,pressure_str);

        // String finalname = "weatherinfo for " +cityName+ " city \n";
        // String finaltemp = "tempreture " +cel_temp+ " °C \n";
        // String finalmaxtemp = "max temp " +cel_tempMax+ " °C\n";
        // String finalmintemp = "min temp " +cel_tempMin+ " °C\n";
        // String finalhumidity = "humidity " +humidity_str+ " %\n";
        // String finalpressure = "pressure " +pressure_str+ " hPa\n";

        // String out = String.join(finalname,finaltemp,finalmaxtemp,finalmintemp,finalhumidity,finalpressure);

        // System.out.println(out);
        return response.body();
        
    }

}

class weather {

    public static void main(String args[]) throws IOException, InterruptedException {

        // get input city name for weather data
        System.out.println("enter city name");
        String data = "";
        try (Scanner sobj = new Scanner(System.in)) {
            data = sobj.nextLine();
        } catch (Exception ee) {
            System.out.println("exception occured:" + ee);
        }

        GetWeatherInfo obj = new GetWeatherInfo();
        obj.getWeatherData(data);

    }

}