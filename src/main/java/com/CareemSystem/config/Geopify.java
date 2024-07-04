package com.CareemSystem.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Geopify {

    URL url = new URL("https://api.geoapify.com/v1/geocode/reverse?lat=51.21709661403662&lon=6.7782883744862374&apiKey=459f3572038c4ca0a76a42e37d966e88");
    HttpURLConnection http = (HttpURLConnection)url.openConnection();

    public Geopify() throws IOException {
        http.setRequestProperty("Accept", "application/json");
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }

}
