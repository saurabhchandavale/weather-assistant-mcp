package com.weather.weatherassistant.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URI;

import org.springframework.stereotype.Component;

@Component
public class OpenMeteoClient {

	private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";
	private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast";
	private final HttpClient httpClient = HttpClient.newHttpClient();
	
    // Search city and get its latitude and longitude
    public String searchLocation(String city)
            throws IOException, InterruptedException {

        String encodedCity =
                URLEncoder.encode(city, StandardCharsets.UTF_8);

        String url = GEOCODING_URL
                + "?name=" + encodedCity
                + "&count=5"
                + "&language=en"
                + "&format=json";

        return sendGetRequest(url);
    }
    
    // Common method to execute HTTP GET requests
    private String sendGetRequest(String url)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {

            throw new IOException(
                    "Open-Meteo API error. HTTP Status: "
                    + response.statusCode()
            );
        }

        return response.body();
    }
    
    // Get current weather using latitude and longitude
    public String getCurrentWeather(double latitude, double longitude)
            throws IOException, InterruptedException {

        String url = FORECAST_URL
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current=temperature_2m"
                + ",relative_humidity_2m"
                + ",apparent_temperature"
                + ",weather_code"
                + ",wind_speed_10m"
                + "&timezone=auto";

        return sendGetRequest(url);
    }


}
