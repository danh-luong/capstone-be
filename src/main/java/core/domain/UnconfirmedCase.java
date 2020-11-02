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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Date;

@Entity
@Table(name = "unconfirmed_cases")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UnconfirmedCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int caseId;
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

    public UnconfirmedCase(UnconfirmedCase unconfirmedCase) {
        this.caseId = unconfirmedCase.getCaseId();
    }
}
