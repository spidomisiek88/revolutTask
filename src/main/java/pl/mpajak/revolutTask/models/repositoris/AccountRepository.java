package pl.mpajak.revolutTask.models.repositoris;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mpajak.revolutTask.models.entitis.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM `account` WHERE `id` = ?1")
    Optional<AccountEntity> findAccountById(int accountId);

    @Query(nativeQuery = true, value = "UPDATE `account` SET `account_balance` = ?2 WHERE `id` = ?1")
    @Modifying
    void updateAccoutBallance(int accountId, double updatedValueOfBalance);

    @Query(nativeQuery = true, value = "UPDATE `account` SET `is_delete` = 1 WHERE `id` = ?1")
    @Modifying
    void softDeleteAccountById(int accountId);
}
