package com.superhero.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Heros Rafa's API")
                        .description("Api REST Heros")
                        .license(new License().name("Apache 2.0").url("https://www.marvel.com/"))
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("About api")
                        .url("https://www.marvel.com/"));
    }

}
