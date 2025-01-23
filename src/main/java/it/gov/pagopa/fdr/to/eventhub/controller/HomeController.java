package it.gov.pagopa.fdr.to.eventhub.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gov.pagopa.fdr.to.eventhub.model.AppInfo;

@RestController
@Validated
@Tag(name = "Home", description = "Application info APIs")
public class HomeController {
	
	private static final String SWAGGER_UI_PATH = "swagger-ui.html";
	private static final String INFO_ENDPOINT = "/info";

    @Value("${server.servlet.context-path}")
    String basePath;

    @Value("${info.application.name}")
    private String name;

    @Value("${info.application.version}")
    private String version;

    @Value("${info.properties.environment}")
    private String environment;


    /**
     * @return redirect to Swagger page documentation
     */
    @Hidden
    @GetMapping("")
    public RedirectView home() {
    	String redirectUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(SWAGGER_UI_PATH)
                .toUriString();
    	return new RedirectView(redirectUrl);
    }

    /**
     * Health Check
     *
     * @return ok
     */
    @Operation(summary = "Return OK if application is started", security = {@SecurityRequirement(name = "ApiKey")}, tags = {"Home"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppInfo.class))),
    })
    @GetMapping(INFO_ENDPOINT)
    public AppInfo healthCheck() {
        return AppInfo.builder()
                .name(name)
                .version(version)
                .environment(environment)
                .build();
    }

}
