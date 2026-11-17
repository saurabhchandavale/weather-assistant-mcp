package com.weather.weatherassistant.dto;

public record LocationResponse(
        String name,
        double latitude,
        double longitude,
        String country
) {
}