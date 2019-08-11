package my.sheshenya.springbootfeatures.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "All details about the users project. ")
public class UserProjectDescriptions {
    private String userName;
    private Set<ProjectDescription> projectDescriptions;
}
