package it.gov.pagopa.fdr.to.eventhub.model.notice;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.xml.datatype.XMLGregorianCalendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeModel implements Serializable {

    private static final long serialVersionUID = -2923839439514213360L;
	
    private String sessionId;
    private LocalDateTime insertedTimestamp;
    // private FlussoRiversamento flussoRiversamento; TODO creare oggetto
    private String flowId;
    private XMLGregorianCalendar flowDate;
    private String creditorInstitution;
    private String psp;
    private String brokerPsp;
}
