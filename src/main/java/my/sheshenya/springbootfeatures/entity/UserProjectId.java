package my.sheshenya.springbootfeatures.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProjectId implements Serializable {
    private String userName;
    private Long projectId;
}
