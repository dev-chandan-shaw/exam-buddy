package com.project.textbookres.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // ✅ Allow all endpoints
                        .allowedOrigins("http://localhost:4200", "http://192.168.29.100:4200")// ✅ Change this to your frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ Allow required methods
                        .allowedHeaders("*")
                        .allowCredentials(true); // ✅ Required if using cookies
            }
        };
    }
}
