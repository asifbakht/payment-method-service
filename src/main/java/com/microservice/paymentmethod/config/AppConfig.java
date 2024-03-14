package com.microservice.paymentmethod.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;

/**
 * custom application configuration resides here
 * 
 * @author Asif Bakht
 * @since 2024
 */
@Configuration
public class AppConfig {

   /**
    * jackson mapper bean with predefined configuration
    * 
    * @return {@link Jackson2ObjectMapperBuilderCustomizer} jackson customizer
    */
   @Bean
   public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {

      return builder -> builder
              .featuresToEnable(READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
              .serializationInclusion(JsonInclude.Include.NON_NULL);
   }
}
