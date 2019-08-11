package my.sheshenya.springbootfeatures.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Builder
@Data
@ApiModel(description = "Brief project description. ")
public class ProjectDescription {

    private Long projectId;
    private String title;
    private URL url;

}
