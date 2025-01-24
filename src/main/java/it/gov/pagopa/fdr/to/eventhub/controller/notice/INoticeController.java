package it.gov.pagopa.fdr.to.eventhub.controller.notice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gov.pagopa.fdr.to.eventhub.model.ProblemJson;
import it.gov.pagopa.fdr.to.eventhub.model.notice.NoticeModel;
import jakarta.validation.Valid;

@Tag(name = "Notice API", description = "APIs to manage notice operations")
@RequestMapping("/api/notice")
public interface INoticeController {

	@Operation(
			summary = "Accepts as input the references to the Blobs to be forwarded to the Event Hub",
			security = {
					@SecurityRequirement(name = "ApiKey"),
					@SecurityRequirement(name = "Authorization")
			},
			operationId = "notify")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Request successfully processed."),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid request payload.",
							content =
							@Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = ProblemJson.class))),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized access. Check API key or token.",
							content = @Content(schema = @Schema())),
					@ApiResponse(
							responseCode = "404",
							description = "No blob references found.",
							content = @Content(schema = @Schema(implementation = ProblemJson.class))),
					@ApiResponse(
							responseCode = "500",
							description = "Service unavailable.",
							content =
							@Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = ProblemJson.class)))
			})
	@PostMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> notify(
			@Valid @RequestBody NoticeModel noticeModel );
}
