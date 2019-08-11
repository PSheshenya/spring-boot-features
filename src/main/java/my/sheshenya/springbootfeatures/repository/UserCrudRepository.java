package my.sheshenya.springbootfeatures.repository;

import my.sheshenya.springbootfeatures.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrudRepository extends CrudRepository<User, String> {
    boolean existsByUserNameIgnoreCase(String userName);

    Optional<User> findByUserNameIgnoreCase(String userName);
}
