package my.sheshenya.springbootfeatures.controller;

import lombok.extern.slf4j.Slf4j;
import my.sheshenya.springbootfeatures.model.UserProject;
import my.sheshenya.springbootfeatures.model.UserProjectDescriptions;
import my.sheshenya.springbootfeatures.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping ("/projects/{userName}")
    public ResponseEntity<UserProjectDescriptions> getUserProjectDescriptions (@PathVariable @NotNull String userName)
    {
        String cid = UUID.randomUUID().toString();
        log.info("GET breif projects descriptions for user:{} [cid={}]" , userName, cid);

        UserProjectDescriptions userProjects = demoService.getUserProjectDescriptions(userName);

        HttpStatus httpStatusResponse = userProjects != null
                ? HttpStatus.OK
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(httpStatusResponse)
                .header("X-CorrelationId", cid)
                .body(userProjects);
    }


    @GetMapping ("/projects/{userName}/{projectId}")
    public ResponseEntity<UserProject> getUserProject (
            @PathVariable @NotNull String userName,
            @PathVariable @NotNull Long projectId   )
    {
        String cid = UUID.randomUUID().toString();
        log.info("GET projectId:{} for user:{} [cid={}]" , projectId, userName, cid);

        UserProject userProject = demoService.getUserProject(userName, projectId);

        HttpStatus httpStatusResponse = userProject != null
                ? HttpStatus.OK
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(httpStatusResponse)
                .header("X-CorrelationId", cid)
                .body(userProject);
    }

}
