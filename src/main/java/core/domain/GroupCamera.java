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
import java.io.Serializable;

@Entity
@Table(name = "group_cameras")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupCamera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;
    @Column(name = "group_name", length = 500)
    private String groupName;

    public GroupCamera(String groupName) {
        this.groupName = groupName;
    }
}
