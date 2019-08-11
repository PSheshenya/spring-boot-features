package my.sheshenya.springbootfeatures.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProjectDescriptions {
    private String userName;
    private Set<ProjectDescription> projectDescriptions;
}
