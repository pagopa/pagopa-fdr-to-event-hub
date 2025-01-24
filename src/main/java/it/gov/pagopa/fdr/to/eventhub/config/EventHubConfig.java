package it.gov.pagopa.fdr.to.eventhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;

@Configuration
public class EventHubConfig {
	
	@Value("${azure.eventhub.flowtx.connection-string}")
    private String flowTxConnectionString;

    @Value("${azure.eventhub.flowtx.name}")
    private String flowTxName;

    @Value("${azure.eventhub.reportediuv.connection-string}")
    private String reportedIUVConnectionString;

    @Value("${azure.eventhub.reportediuv.name}")
    private String reportedIUVName;
	
	@Bean(name = "eventHubClientFlowTx")
    public EventHubProducerClient eventHubClientFlowTx() {
        return new EventHubClientBuilder()
                .connectionString(flowTxConnectionString, flowTxName)
                .buildProducerClient();
    }

    @Bean(name = "eventHubClientReportedIUV")
    public EventHubProducerClient eventHubClientReportedIUV() {
        return new EventHubClientBuilder()
                .connectionString(reportedIUVConnectionString, reportedIUVName)
                .buildProducerClient();
    }
}
