package my.sheshenya.springbootfeatures.repository;


import my.sheshenya.springbootfeatures.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserCrudRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserCrudRepository repository;

    User fredUser;

    @Before
    public void before() {
        fredUser = new User("Fred", "Bloggs");

        entityManager.persist(fredUser);
        entityManager.persist(new User("Ricki", "Bobbie"));
        entityManager.persist(new User("Siya", "Kolisi"));
    }

    @Test
    public void iterUsers_whenMatchingIgnoreCase_thenExpectedReturned() {
        Iterable<User> iterable = repository.findAll();
        List<User> result = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        assertTrue(result.size() > 1);
    }

    @Test
    public void existsUsers_whenMatchingIgnoreCase_thenExpectedReturned() {

        Boolean fredExist = repository.existsByUserNameIgnoreCase("FrED");
        assertTrue(fredExist);
    }

    @Test
    public void findUsers_whenMatchingIgnoreCase_thenExpectedReturned() {

        Optional<User> fredUser = repository.findByUserNameIgnoreCase("FrED");
        assertEquals(fredUser.get().getUserName(), "Fred");
    }
}
