package it.gov.pagopa.fdr.to.eventhub.service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubProducerClient;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventHubService {

    private final EventHubProducerClient eventHubClientFlowTx;
    private final EventHubProducerClient eventHubClientReportedIUV;

    public EventHubService(
            @Qualifier("eventHubClientFlowTx") EventHubProducerClient eventHubClientFlowTx,
            @Qualifier("eventHubClientReportedIUV") EventHubProducerClient eventHubClientReportedIUV) {
        this.eventHubClientFlowTx = eventHubClientFlowTx;
        this.eventHubClientReportedIUV = eventHubClientReportedIUV;
    }

    /**
     * Sends a message to Event Hub FlowTx.
     *
     * @param message The message to send.
     */
    public void sendMessageToEventHubFlowTx(String message) {
        try {
        	log.debug("Preparing to send message to Event Hub FlowTx: {}", message);
        	EventDataBatch batch = eventHubClientFlowTx.createBatch();
        	
        	if (!batch.tryAdd(new EventData(message))) {
                throw new IllegalArgumentException("Message too large to fit in a batch.");
            }
    
            eventHubClientFlowTx.send(batch);
            
            log.debug("Message sent successfully to Event Hub FlowTx.");       
        } catch (Exception e) {
        	log.error("Failed to send message to Event Hub FlowTx.", e);
        }
    }

    /**
     * Sends a message to Event Hub ReportedIUV.
     *
     * @param message The message to send.
     */
    public void sendMessageToEventHubReportedIUV(String message) {
        try {
        	log.debug("Preparing to send message to Event Hub ReportedIUV: {}", message);
        	EventDataBatch batch = eventHubClientReportedIUV.createBatch();
        	
        	if (!batch.tryAdd(new EventData(message))) {
                throw new IllegalArgumentException("Message too large to fit in a batch.");
            }
            
            eventHubClientReportedIUV.send(batch);
            
            log.debug("Message sent successfully to Event Hub ReportedIUV.");
        } catch (Exception e) {
        	log.error("Failed to send message to Event Hub ReportedIUV.", e);
        }
    }
}
