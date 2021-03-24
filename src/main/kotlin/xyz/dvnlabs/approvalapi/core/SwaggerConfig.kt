/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core

import io.swagger.annotations.ApiOperation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(
                ApiInfoBuilder()
                    .title("Skripshit")
                    .description("API For Skripshit")
                    .contact(
                        Contact(
                            "Davin Alfarizky Putra Basudewa",
                            "https://dvnlabs.xyz",
                            "dbasudewa@gmail.com"
                        )
                    )
                    .version("Mager gue!!!")
                    .build()
            )
            .select()
            .apis(RequestHandlerSelectors.basePackage("xyz.dvnlabs.approvalapi.controller"))
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(arrayListOf(securityContext()))
            .securitySchemes(arrayListOf(apiKey()) as List<SecurityScheme>?);
    }

    private fun apiKey(): ApiKey {
        return ApiKey("BearerToken", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/.*"))
            .build()
    }

    fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return arrayListOf(SecurityReference("BearerToken", authorizationScopes))
    }

}