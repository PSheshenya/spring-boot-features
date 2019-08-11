package my.sheshenya.springbootfeatures.service;

import lombok.extern.slf4j.Slf4j;
import my.sheshenya.springbootfeatures.entity.Project;
import my.sheshenya.springbootfeatures.entity.User;
import my.sheshenya.springbootfeatures.entity.UserProjectId;
import my.sheshenya.springbootfeatures.entity.UserProjectRelation;
import my.sheshenya.springbootfeatures.model.ProjectDescription;
import my.sheshenya.springbootfeatures.model.UserProject;
import my.sheshenya.springbootfeatures.model.UserProjectDescriptions;
import my.sheshenya.springbootfeatures.repository.ProjectCrudRepository;
import my.sheshenya.springbootfeatures.repository.UserCrudRepository;
import my.sheshenya.springbootfeatures.repository.UserProjectRelationCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DemoService {

    @Autowired
    UserCrudRepository userRepository;

    @Autowired
    ProjectCrudRepository projectRepository;

    @Autowired
    UserProjectRelationCrudRepository userProjectRelationRepository;

    public UserProjectDescriptions getUserProjectDescriptions(String userName) {
        Assert.isTrue(userRepository.existsByUserNameIgnoreCase(userName), String.format("userName '%s' does not exist", userName));
        UserProjectDescriptions userProjectDescriptions = new UserProjectDescriptions();
        userProjectDescriptions.setUserName(userName);
        userProjectDescriptions.setProjectDescriptions(new HashSet());

        List<UserProjectRelation> userProjectRelations = userProjectRelationRepository.findUserProjectRelationsByUserNameIgnoreCase(userName);
        userProjectRelations
                .forEach(upRel ->
                {
                    Optional<Project> project = projectRepository.findById(upRel.getProjectId());
                    if (project.isPresent()) {
                        Project p = project.get();
                        ProjectDescription projectDescription = ProjectDescription.builder()
                                .projectId(p.getProjectId())
                                .title(p.getTitle())
                                .url(p.getUrl())
                                .build();
                        userProjectDescriptions.getProjectDescriptions().add(projectDescription);
                    }
                    else
                        log.warn("finded unconsistent UserProjectRelation row" + userProjectRelations);
                });

        return userProjectDescriptions;
    }

    public UserProject getUserProject(String userName, Long projectId) {
        Assert.isTrue(userRepository.existsByUserNameIgnoreCase(userName), String.format("userName '%s' does not exist", userName));
        Assert.isTrue(projectRepository.existsById(projectId), String.format("projectId '%s' does not exist", projectId));

        UserProject userProject = new UserProject();

        Optional<UserProjectRelation> userProjectRelation = userProjectRelationRepository.findById(new UserProjectId(userName, projectId));
        Assert.isTrue(userProjectRelation.isPresent(), String.format("Project '%s' doesn't exist for user '%s'", projectId, userName));

        UserProjectRelation upRel = userProjectRelation.get();
        Optional<Project> project = projectRepository.findById(upRel.getProjectId());
        if (project.isPresent()) {
            Project p = project.get();
            //
            userProject.setReadme(p.getReadme());
            userProject.setCommits(p.getCommits());
            //
            List<UserProjectRelation> userProjectRelations = userProjectRelationRepository.findUserProjectRelationsByProjectId(projectId);
            userProject.setContributors(new HashSet<>());
            userProjectRelations
                    .forEach(upr ->
                    {
                        Optional<User> user = userRepository.findByUserNameIgnoreCase(upr.getUserName());
                        if (user.isPresent()) {
                            User u = user.get();
                            userProject.getContributors().add(u);
                        }
                        else
                            log.warn("finded unconsistent UserProjectRelation row" + userProjectRelations);
                    });

        }
        else
            log.warn("finded unconsistent UserProjectRelation row" + upRel);


        return userProject;
    }
}
