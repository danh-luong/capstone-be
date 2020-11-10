package core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LineDTO implements Serializable {

    private int lineId;
    private String lineType;
    private int top;
    private int left;
    private int right;
    private int bottom;
}
