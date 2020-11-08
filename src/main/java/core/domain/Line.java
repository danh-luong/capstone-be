package core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@Table(name = "line")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Line implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lineId;
    @Column(name = "line_type", length = 500)
    private String lineType;
    @Column(name = "top")
    private int top;
    @Column(name = "lefts")
    private int left;
    @Column(name = "rights")
    private int right;
    @Column(name = "bottom")
    private int bottom;
    @ManyToOne
    @JoinColumn(name = "camera_id")
    private Camera camera;
}
