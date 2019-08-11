package my.sheshenya.springbootfeatures.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Builder
@Data
public class ProjectDescription {

    private Long projectId;
    private String title;
    private URL url;

}
