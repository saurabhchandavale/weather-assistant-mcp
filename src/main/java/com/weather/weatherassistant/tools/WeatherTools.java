package com.weather.weatherassistant.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.weather.weatherassistant.dto.WeatherResponse;
import com.weather.weatherassistant.service.WeatherService;

@Component
public class WeatherTools {
	
    private final WeatherService weatherService;

    public WeatherTools(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    @Tool(description = "Get current weather information for a city, including temperature, feels-like temperature, humidity, wind speed and weather condition.")
    public WeatherResponse getCurrentWeather(String city) throws Exception {

        return weatherService.getCurrentWeather(city);
    }

}
