package my.sheshenya.springbootfeatures.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import my.sheshenya.springbootfeatures.entity.User;

import java.util.Set;

@Data
@ApiModel(description = "All details about the project. ")
public class UserProject {
    private String readme;
    private Integer commits;
    private Set<User> contributors;
}
