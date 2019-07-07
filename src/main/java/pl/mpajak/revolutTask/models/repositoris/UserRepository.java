package pl.mpajak.revolutTask.models.repositoris;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mpajak.revolutTask.models.entitis.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM 'user' WHERE 'name' = ?1")
    Optional<UserEntity> findUserByName(String userName);

    @Query(nativeQuery = true, value = "SELECT * FROM `user` WHERE `id` = ?1")
    Optional<UserEntity> findUserById(int userId);

    @Query(nativeQuery = true, value = "UPDATE `user` SET `is_delete` = 1 WHERE `id` = ?1")
    @Modifying
    void softDeleteUserById(int userId);
}
