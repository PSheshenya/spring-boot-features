package my.sheshenya.springbootfeatures.repository;

import my.sheshenya.springbootfeatures.entity.UserProjectId;
import my.sheshenya.springbootfeatures.entity.UserProjectRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRelationCrudRepository extends CrudRepository<UserProjectRelation, UserProjectId> {

    @Query("select upr from UserProjectRelation upr where upper(upr.userName)=upper(:userName)")
    List<UserProjectRelation> findUserProjectRelationsByUserNameIgnoreCase(@Param("userName") String userName );

    @Query("select upr from UserProjectRelation upr where upr.projectId = :projectId")
    List<UserProjectRelation> findUserProjectRelationsByProjectId(@Param("projectId") Long projectId );
}
