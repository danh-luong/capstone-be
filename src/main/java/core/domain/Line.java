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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@Table(name = "line")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Line implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
