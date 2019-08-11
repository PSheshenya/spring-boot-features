package my.sheshenya.springbootfeatures.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity @IdClass(UserProjectId.class)
public class UserProjectRelation {

    @Id
    private String userName;

    @Id
    private Long projectId;

    private Timestamp joinDate;
}
