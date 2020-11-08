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
@Table(name = "cameras")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Camera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cameraId;
    @Column(name = "location", length = 500)
    private String location;
    @Column(name = "connection_url", length = 500)
    private String connectionUrl;
    @Column(name = "position", length = 500)
    private String position;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupCamera groupCamera;
    @Column(name = "status", length = 500)
    private String status;

}
