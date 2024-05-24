package com.sparta.personalassignment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    /**
     * OpenAPI bean 구성
     * @return
     */
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("일정관리 api")
                .version("1.0")
                .description("일정관리에 관한 api가이드 페이지");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}