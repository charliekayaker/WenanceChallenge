package com.wenance.digitalcurrencies.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

	@Configuration
	@EnableSwagger2
	public class SwaggerConfig {

		private static final String STRING = "string";
		private static final String HEADER = "header";

		@Bean
		public Docket api() {

			ParameterBuilder parameterBuilder = new ParameterBuilder();
			List<Parameter> headerParameters = new ArrayList<>();

			parameterBuilder.name("Content-Type").modelRef(new ModelRef(STRING)).parameterType(HEADER).required(true)
					.defaultValue("application/json");
			headerParameters.add(parameterBuilder.build());

			return new Docket(DocumentationType.SWAGGER_2).select()
					.apis(RequestHandlerSelectors.basePackage("com.wenance.digitalcurrencies.controller")).paths(PathSelectors.regex("/.*")).build()
					.apiInfo(metaData()).globalOperationParameters(headerParameters);
		}

		private ApiInfo metaData() {
			return new ApiInfoBuilder().title("Digital Currencies - API -")
					.description("API-REST que informa la cotización histórica y estadística entre fechas de las dos principales "
							+ "cryptomonedas, Etherium y Bitcoin. Además permite la conversión de cryptomonedas a USD ")
					.version("1.0-SNAPSHOT").build();
		}

		@Bean
		public UiConfiguration uiConfig() {
			return UiConfigurationBuilder.builder().displayRequestDuration(true).validatorUrl(StringUtils.EMPTY).build();
		}
	}
