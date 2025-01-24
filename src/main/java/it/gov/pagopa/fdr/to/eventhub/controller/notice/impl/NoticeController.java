package it.gov.pagopa.fdr.to.eventhub.controller.notice.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import it.gov.pagopa.fdr.to.eventhub.controller.notice.INoticeController;
import it.gov.pagopa.fdr.to.eventhub.model.notice.NoticeModel;
import it.gov.pagopa.fdr.to.eventhub.service.BlobStorageService;
import it.gov.pagopa.fdr.to.eventhub.service.EventHubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NoticeController implements INoticeController {
	
	private final EventHubService eventHubService;
	private final BlobStorageService blobStorageService;

	@Override
	public ResponseEntity<Void> notify(@Valid NoticeModel noticeModel) {
		log.info("Received request to notify with noticeModel: {}", noticeModel);

        try {
            log.debug("Processing noticeModel: {}", noticeModel);
            
            // search in blob storage
            blobStorageService.searchBlobs("nome", null, 0L);

            // sends a message to event hubs
            eventHubService.sendMessageToEventHubFlowTx("");
            eventHubService.sendMessageToEventHubReportedIUV("");

            log.info("Successfully processed noticeModel: {}", noticeModel);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error occurred while processing noticeModel: {}", noticeModel, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
