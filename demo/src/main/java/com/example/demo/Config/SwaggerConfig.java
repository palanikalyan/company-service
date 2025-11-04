package com.example.demo.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI companyServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Freelancing Application - Company Service API")
                        .description("API documentation for Company management module")
                        .version("1.0.0"));
    }
}
