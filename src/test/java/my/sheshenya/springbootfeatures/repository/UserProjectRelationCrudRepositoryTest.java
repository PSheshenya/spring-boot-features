package my.sheshenya.springbootfeatures.repository;

import my.sheshenya.springbootfeatures.entity.Project;
import my.sheshenya.springbootfeatures.entity.UserProjectRelation;
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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserProjectRelationCrudRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserProjectRelationCrudRepository repository;

    UserProjectRelation userProjectRelation;

    @Before
    public void before() throws MalformedURLException {
        repository.deleteAll();

        userProjectRelation = new UserProjectRelation( "Alex", 1L, new Timestamp(System.currentTimeMillis()));
        entityManager.persist(userProjectRelation);
    }

    @Test
    public void findRelation_ByProjectId_thenExpectedReturned() {
        List<UserProjectRelation> userProjectRelationsByProjectId = repository.findUserProjectRelationsByProjectId(1L);
        assertEquals(userProjectRelation, userProjectRelationsByProjectId.get(0));
    }

    @Test
    public void findRelation_whenMatchingByUserNameIgnoreCase_thenExpectedReturned() {
        List<UserProjectRelation> userProjectRelationsByUserName = repository.findUserProjectRelationsByUserNameIgnoreCase("aLeX");
        assertEquals(userProjectRelation, userProjectRelationsByUserName.get(0));
    }

}