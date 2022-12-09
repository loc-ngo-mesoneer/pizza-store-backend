package io.locngo.pizza.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class BeanInitializer {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE, false);
        return objectMapper;
    }

    @Bean
    public OpenAPI pizzaStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Pizza Store API")
                        .description("Pizza store backend application")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Pizza store requirement documentation")
                        .url("https://mesoneerag.atlassian.net/wiki/spaces/DEV/pages/1324024135/Axon.ivy+Kata+-+PizzaStore+Online"));
    }
}
