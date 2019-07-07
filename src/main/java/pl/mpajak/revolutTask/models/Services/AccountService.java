package pl.mpajak.revolutTask.models.Services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mpajak.revolutTask.models.entitis.AccountEntity;
import pl.mpajak.revolutTask.models.repositoris.AccountRepository;

import java.util.Optional;

@Service
@Data
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Iterable<AccountEntity> getAllAccaunts() {
        return accountRepository.findAll();
    }

    public Optional<AccountEntity> getAccountById(int accountId) {
        return accountRepository.findAccountById(accountId);
    }

    public boolean deleteAccountById(int accountId){

        Optional<AccountEntity> optionalUserEntity = getAccountById(accountId);
        if (optionalUserEntity.isPresent()) {
            accountRepository.softDeleteAccountById(accountId);
            return getAccountById(accountId).get().isDelete();
        }

        return false;
    }
}
