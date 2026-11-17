package com.weather.weatherassistant.dto;

public record WeatherResponse(
        String city,
        String country,
        double temperature,
        double feelsLike,
        int humidity,
        double windSpeed,
        int weatherCode,
        String condition
) {
}