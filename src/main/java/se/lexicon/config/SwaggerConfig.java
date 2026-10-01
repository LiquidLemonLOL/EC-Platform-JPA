package se.lexicon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Platform API")
                        .version("0.0.1-SNAPSHOT")
                        .description("This is the API for ECommerce Platform API and provides endpoints for managing " +
                                " customers, categories, order and products through CRUD operations.")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicOpenAPI() {
        return GroupedOpenApi.builder()
                .group("e-commerce-platform-public")
                .pathsToMatch("/api/v1/**")
                .build();
    }

}
