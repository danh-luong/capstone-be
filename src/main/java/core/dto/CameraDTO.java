package core.dto;

import core.domain.GroupCamera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CameraDTO implements Serializable {

    private List<LineDTO> lines;
    private int cameraId;
    private String location;
    private String connectionUrl;
    private String position;
    private GroupCamera groupCamera;
    private String status;
}
