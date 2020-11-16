package core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Date;

@Entity
@Table(name = "rejected_cases")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RejectedCase {

    @Id
    @Column(name = "case_id")
    private int caseId;
    @Column(name = "trained_status")
    private String trainedStatus;
    @Column(name = "location", length = 500)
    private String location;
    @Column(name = "license_plate", length = 500)
    private String licensePlate;
    @Column(name = "created_date")
    private Date createdDate;
    @OneToOne
//    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "violation_id")
    private ViolationType violationType;

    public RejectedCase(RejectedCase rejectedCase) {
        this.caseId = rejectedCase.getCaseId();
        this.trainedStatus = rejectedCase.getTrainedStatus();
    }
}
