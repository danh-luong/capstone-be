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
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "punishment_reports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PunishmentReport implements Serializable {

    @Id
    @Column(name = "case_id")
    private int caseId;
    @Column(name = "report_url", length = 1000)
    private String reportUrl;
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

}
