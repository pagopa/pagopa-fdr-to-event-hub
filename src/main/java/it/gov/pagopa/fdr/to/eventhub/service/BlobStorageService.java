package it.gov.pagopa.fdr.to.eventhub.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlobStorageService {
	private final BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public List<BlobItem> searchBlobs(String nameFilter, OffsetDateTime createdAfter, Long minSize) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        return StreamSupport.stream(containerClient.listBlobs().spliterator(), false)
                .filter(blob -> nameFilter == null || blob.getName().contains(nameFilter))
                .filter(blob -> createdAfter == null || blob.getProperties().getCreationTime().isAfter(createdAfter))
                .filter(blob -> minSize == null || blob.getProperties().getContentLength() >= minSize)
                .toList();
    }
}
