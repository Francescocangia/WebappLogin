package com.webapplogin.webapplogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ClassConfigurationCORS {

    //semplice classe di ocnfigurazione del cors
    //nota anche con questa classe da errore con docker






        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {

                @Override
                public void addCorsMappings(CorsRegistry registry) {

                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:5173", "http://localhost:5137")
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                            .allowedHeaders("*","Content-Type","Authorization")
                            .allowCredentials(true);
                }
            };
        }
    }

