package core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Case implements Serializable {

    @Column(name = "location", length = 500)
    private String location;
    @Column(name = "license_plate", length = 500)
    private String licensePlate;
    @Column(name = "created_date")
    private Date createdDate;
    @OneToOne
    @JoinColumn(name = "image_id")
    private int imgaeId;
    @ManyToOne
    @JoinColumn(name = "violation_id")
    private ViolationType violationType;

}
