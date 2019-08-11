package my.sheshenya.springbootfeatures.repository;

import my.sheshenya.springbootfeatures.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCrudRepository extends CrudRepository<Project, Long> {
}
