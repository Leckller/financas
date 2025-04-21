package com.ruyCorp.dot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  @Value("${app.frontend.url}")
  private String frontendUrl;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/user")
        .allowedOrigins(this.frontendUrl)
        .allowedMethods("GET", "POST", "PATCH", "DELETE");
    registry.addMapping("/auth/**")
        .allowedOrigins(this.frontendUrl)
        .allowedMethods("GET", "POST", "PATCH", "DELETE");
    registry.addMapping("/transaction/**")
        .allowedOrigins(this.frontendUrl)
        .allowedMethods("GET", "POST", "PATCH", "DELETE");
  }
}