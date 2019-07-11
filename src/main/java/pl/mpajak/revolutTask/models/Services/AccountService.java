package pl.mpajak.revolutTask.models.Services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public boolean transferFundsBetweenAccounts(AccountEntity sendingAccount, AccountEntity receivingAccount, double transferAmount) {

        if (!reductionAccountBalances(sendingAccount, transferAmount))
            return false;
        raisingAccountBalance(receivingAccount, transferAmount);

        return true;
    }

    @Transactional
    public boolean reductionAccountBalances(AccountEntity accountEntityToReductioning, double amountReduction) {

        if (amountReduction > accountEntityToReductioning.getAccountBalance())
            return false;

        double reductionedAccountBalances = accountEntityToReductioning.getAccountBalance() - amountReduction;
        updateAccountBalance(accountEntityToReductioning, reductionedAccountBalances);

        return true;
    }

    @Transactional
    public AccountEntity raisingAccountBalance(AccountEntity accountEntityToRaising, double amountRaise) {

        double raisedAccountBalances = accountEntityToRaising.getAccountBalance() + amountRaise;

        return updateAccountBalance(accountEntityToRaising, raisedAccountBalances);
    }

    @Transactional
    public AccountEntity updateAccountBalance(AccountEntity accountEntity, double accountBalance) {

        accountEntity.setAccountBalance(accountBalance);
        accountRepository.save(accountEntity);

        return accountEntity;
    }

    @Transactional
    public boolean deleteAccountById(int accountId){

        Optional<AccountEntity> optionalAccountEntity = getAccountById(accountId);
        if (optionalAccountEntity.isPresent()) {
            accountRepository.softDeleteAccountById(accountId);
            return getAccountById(accountId).get().isDelete();
        }

        return false;
    }
}
