package my.sheshenya.springbootfeatures.model;

import lombok.Data;
import my.sheshenya.springbootfeatures.entity.User;

import java.util.Set;

@Data
public class UserProject {
    private String readme;
    private Integer commits;
    private Set<User> contributors;
}
