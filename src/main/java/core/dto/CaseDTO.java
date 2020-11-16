package core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import core.domain.Image;
import core.domain.ViolationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseDTO implements Serializable {

    private int caseId;
    private String caseType;
    private String reportUrl;
    private String trainedStatus;
    private String location;
    private String licensePlate;
    private Date createdDate;
    private Image image;
    private ViolationType violationType;
}
