package com.apimars.apimarstemperature.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "apimars")
@PropertySource("classpath:application.properties")
@Getter
@NoArgsConstructor
@SuppressWarnings("squid:S00116")
public class ConfigProperties {

    @Value("${apimars.url_base}")
    private String URL_BASE;
    @Value("${apimars.api_key}")
    private String API_KEY;
    @Value("${apimars.feedtype}")
    private String FEEDTYPE;
    @Value("${apimars.version_api}")
    private String VERSION_API;

    @Value("${apimars.error_return_endpoint}")
    private String ERROR_RETURN_ENDPOINT;
    @Value("${apimars.error_invalid_parameter}")
    private String ERROR_INVALID_PARAMETER;

}