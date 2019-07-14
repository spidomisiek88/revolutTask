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
    public boolean transferFundsBetweenAccounts(TransferSession transferSession) {
        if (!transferSession.isTransferFinished())
            return false;

        transferSession.setTransferFinished(false);

        if (!reductionAccountBalances(transferSession))
            return false;
        raisingAccountBalance(transferSession);

        transferSession.setTransferFinished(true);
        return true;
    }

    @Transactional
    public boolean reductionAccountBalances(TransferSession transferSession) {

        if (transferSession.getTransferAmount() > transferSession.getSendingAccount().getAccountBalance())
            return false;

        double reductionedAccountBalances = transferSession.getSendingAccount().getAccountBalance() - transferSession.getTransferAmount();
        updateAccountBalance(transferSession.getSendingAccount(), reductionedAccountBalances);

        return true;
    }

    @Transactional
    public AccountEntity raisingAccountBalance(TransferSession transferSession) {

        double raisedAccountBalances = transferSession.getReceivingAccount().getAccountBalance() + transferSession.getTransferAmount();

        return updateAccountBalance(transferSession.getReceivingAccount(), raisedAccountBalances);
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
