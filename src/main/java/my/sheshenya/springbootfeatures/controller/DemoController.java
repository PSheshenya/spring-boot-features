package my.sheshenya.springbootfeatures.controller;

import io.swagger.annotations.*;
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
@Api(value="Demo Service",
        produces="application/json, application/xml",
        consumes="application/json, application/xml")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping({"/projects", "/mock"})
    @ApiOperation(value="Hi all", response=String.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Hi",response=String.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Service not found")
    })
    public String hi(){
        return  "Hi from demo Service";
    }


    @GetMapping ("/projects/{userName}")
    @ApiOperation(value="Get document which containing\n" +
            "the user’s name and a list of projects. Each project should contain the URL and title of the\n" +
            "project and some kind of id."
            ,response= UserProjectDescriptions.class)
    @ApiResponses(value={
            @ApiResponse(code=200,
                    message="UserProjectDescriptions Response",
                    responseHeaders = {
                            @ResponseHeader(name = "X-CorrelationId", description = "CorrelationId", response = String.class)
                    },
                    response= UserProjectDescriptions.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Not Found")
    })
    public ResponseEntity<UserProjectDescriptions> getUserProjectDescriptions (
            @ApiParam(value = "requested userName", required = true)
            @PathVariable @NotNull String userName)
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
    @ApiOperation(value="Get document which respond with document\n" +
            "a 50 word excerpt from the project’s readme.md, list of contributors and number of total\n" +
            "commits."
            ,response= UserProject.class)
    @ApiResponses(value={
            @ApiResponse(code=200,
                    message="UserProject Response",
                    responseHeaders = {
                            @ResponseHeader(name = "X-CorrelationId", description = "CorrelationId", response = String.class)
                    },
                    response= UserProject.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Not Found")
    })
    public ResponseEntity<UserProject> getUserProject (
            @ApiParam(value = "requested userName", required = true)
            @PathVariable @NotNull String userName,
            @ApiParam(value = "requested projectId", required = true)
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
