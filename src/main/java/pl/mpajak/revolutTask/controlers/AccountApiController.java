package pl.mpajak.revolutTask.controlers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.mpajak.revolutTask.models.Services.AccountService;
import pl.mpajak.revolutTask.models.Services.TransferSession;
import pl.mpajak.revolutTask.models.entitis.AccountEntity;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountApiController {

    final AccountService accountService;
    TransferSession transferSession;

    @Autowired
    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
        this.transferSession = new TransferSession();
    }

    @GetMapping(value = "/accounts", produces = "application/json")
    public ResponseEntity getUsers() {

        return ResponseEntity.ok(accountService.getAllAccaunts());
    }

    @GetMapping(value = "/account/{accountId}", produces = "application/json")
    public ResponseEntity getAccountById(@PathVariable("accountId") int accountId) {

        Optional<AccountEntity> optionalAccountEntity = accountService.getAccountById(accountId);
        if (!optionalAccountEntity.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalAccountEntity.get());
    }

    @GetMapping(value = "/account/update/{accountId}/{accountBalance}", produces = "application/json")
    public ResponseEntity updateAccountBalance(@PathVariable("accountId") int accountId,
                                         @PathVariable("accountBalance") double accountBalance) {

        Optional<AccountEntity> optionalAccountEntity = accountService.getAccountById(accountId);
        if (!optionalAccountEntity.isPresent())
            return ResponseEntity.notFound().build();

        accountService.updateAccountBalance(optionalAccountEntity.get(), accountBalance);

        return ResponseEntity.ok(optionalAccountEntity.get());
    }

    @GetMapping(value = "/account/transfer/{sendingAccountId}/{receivingAccountId}/{transferAmount}",
            produces = "application/json")
    public ResponseEntity transferFundsBetweenAccounts(@PathVariable("sendingAccountId") int sendingAccountId,
                                                       @PathVariable("receivingAccountId") int receivingAccountId,
                                                       @PathVariable("transferAmount") double transferAmount) {

        Optional<AccountEntity> optionalSendingAccountEntity = accountService.getAccountById(sendingAccountId);
        Optional<AccountEntity> optionalReceivingAccountEntity = accountService.getAccountById(receivingAccountId);
        if (!optionalSendingAccountEntity.isPresent() || !optionalReceivingAccountEntity.isPresent())
            return ResponseEntity.notFound().build();

        transferSession.setSendingAccount(optionalSendingAccountEntity.get());
        transferSession.setReceivingAccount(optionalReceivingAccountEntity.get());
        transferSession.setTransferAmount(transferAmount);

        accountService.transferFundsBetweenAccounts(transferSession);

        Iterable<AccountEntity> changedAccounts =
                Arrays.asList(optionalSendingAccountEntity.get(), optionalReceivingAccountEntity.get());

        return ResponseEntity.ok(changedAccounts);
    }
}
