package it.gov.pagopa.fdr.to.eventhub.controller.notice.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import it.gov.pagopa.fdr.to.eventhub.controller.notice.INoticeController;
import it.gov.pagopa.fdr.to.eventhub.model.notice.NoticeModel;
import it.gov.pagopa.fdr.to.eventhub.service.EventHubService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NoticeController implements INoticeController {
	
	private final EventHubService eventHubService;

    public NoticeController(EventHubService eventHubService) {
        this.eventHubService = eventHubService;
    }

	@Override
	public ResponseEntity<Void> notify(@Valid NoticeModel noticeModel) {
		log.info("Received request to notify with noticeModel: {}", noticeModel);

        try {
            log.debug("Processing noticeModel: {}", noticeModel);

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
