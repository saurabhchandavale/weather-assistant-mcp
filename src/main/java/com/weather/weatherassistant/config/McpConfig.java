package com.weather.weatherassistant.config;


import com.weather.weatherassistant.tools.*;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {
	@Bean
    public ToolCallbackProvider weatherToolProvider(
            WeatherTools weatherTools) {

        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherTools)
                .build();
    }

}
