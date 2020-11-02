package core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name = "violation_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViolationType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int violationId;
    @Column(name = "name", length = 500)
    private String name;
    @Column(name = "description", length = 500)
    private String description;
}
