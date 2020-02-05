package com.apimars.apimarstemperature.config;

import com.apimars.apimarstemperature.constantes.Constantes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket votacaoApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(Constantes.PACKAGE))
				.paths(regex(Constantes.PATHS))
				.build()
				.apiInfo(metaInfo());
	}

	@SuppressWarnings("squid:S1488")
	private ApiInfo metaInfo() {
	    ApiInfo apiInfo = new ApiInfo(
                "API REST MARS TEMPERATURE",
                "API REST da NASA para temperature do planeta Marte.",
                "1.0.0",
                "Termos do serviço",
                new Contact(Constantes.DEVNAME, Constantes.DEVSITE, Constantes.DEVEMAIL),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
	}
	
}
