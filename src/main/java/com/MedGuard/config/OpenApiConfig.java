package com.MedGuard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info =@Info(
                contact = @Contact(
                        name = "Wassem Tenbakji" ,
                        email = "wasee.tenbakji@gmail.com",
                        url = "https://www.linkedin.com/in/wassem-tenbakji-a078a5206"
                ),
                description = "open api documentation for Ride Share System",
                title = "MedGuard",
                version = "1.0",
                license = @License(
                        name = "This Doc made for training purpose and can't be used for business or jobs," +
                                "If You need any help contact me with previous email",
                        url = "wasee.tenbakji@gmail.com"
                ),
                termsOfService = "Term of my Service"
        ),
        servers =
                {
                        @Server(
                                description = "Prod ENV",
                                url = "http://203.161.44.194:50080"
                        ),
                        @Server(
                                description = "Local ENV",
                                url = "http://localhost:3101"
                        ),
                },
        security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
