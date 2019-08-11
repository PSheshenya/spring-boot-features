package my.sheshenya.springbootfeatures.repository;

import my.sheshenya.springbootfeatures.entity.Project;
import my.sheshenya.springbootfeatures.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ProjectCrudRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProjectCrudRepository repository;

    Project anotherProject;

    @Before
    public void before() throws MalformedURLException {
        repository.deleteAll();

        anotherProject = new Project( null, "Project anotherProject", new URL("http://go.com/projectanother"), "Describe project anotherProject", 100);
        entityManager.persist(anotherProject);
    }

    @Test
    public void existsProject_thenExpectedReturned() {
        Boolean projectExist = repository.existsById(anotherProject.getProjectId());
        assertTrue(projectExist);
    }

    @Test
    public void findProject_thenExpectedReturned() {
        Optional<Project> sProject = repository.findById(anotherProject.getProjectId());
        assertEquals(sProject.get(), anotherProject);
    }
}