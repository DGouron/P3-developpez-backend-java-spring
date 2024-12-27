package com.oc.chatop.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Châtop API",
                version = "1.0.0",
                description = "This is the API for the project named Châtop",
                contact = @Contact(
                        name = "Gouron Damien",
                        email = "damien.gouron@gmail.com"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = @Server(
                description = "localhost",
                url = "http://localhost:3002/api/"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
