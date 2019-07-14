package pl.mpajak.revolutTask.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mpajak.revolutTask.models.Services.AccountService;
import pl.mpajak.revolutTask.models.Services.TransferSession;
import pl.mpajak.revolutTask.models.entitis.AccountEntity;
import pl.mpajak.revolutTask.models.entitis.UserEntity;
import pl.mpajak.revolutTask.models.repositoris.AccountRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    TransferSession userSession;
    @InjectMocks
    AccountService accountService;
    @Autowired
    MockMvc mockMvc;
    UserEntity testUser;
    AccountEntity firstTestAccount;
    AccountEntity secondTestAccount;
    TransferSession testTransferSession;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        testUser = new UserEntity();
        testUser.setId(0);
        testUser.setName("testUser");
        testUser.setIsDelete(0);
        testUser.setAccounts(new ArrayList<>());

        firstTestAccount = new AccountEntity();
        firstTestAccount.setId(0);
        firstTestAccount.setUser(testUser);
        firstTestAccount.setIsDelete(0);
        firstTestAccount.setAccountBalance(200);

        secondTestAccount = new AccountEntity();
        secondTestAccount.setId(0);
        secondTestAccount.setUser(testUser);
        secondTestAccount.setIsDelete(0);
        secondTestAccount.setAccountBalance(50);

        testTransferSession = new TransferSession();
        testTransferSession.setSendingAccount(firstTestAccount);
        testTransferSession.setReceivingAccount(secondTestAccount);
        testTransferSession.setTransferAmount(0);
        testTransferSession.setTransferFinished(true);
    }

    @Test
    public void shouldUpdateAccountBalance() {
        double expectedAccountBalance = 100;


        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertEquals(expectedAccountBalance, accountService.updateAccountBalance(firstTestAccount, expectedAccountBalance).getAccountBalance());
    }

    @Test
    public void shouldReduceAccountBalance() {
        testTransferSession.setTransferAmount(100);

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertTrue(accountService.reductionAccountBalances(testTransferSession));
    }

    @Test
    public void shouldNotReduceAccountBalance() {
        testTransferSession.setTransferAmount(300);

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertFalse(accountService.reductionAccountBalances(testTransferSession));
    }

    @Test
    public void shouldRaiseAccountBalance() {
        double expectedRaisedAccountBalance = 150;
        testTransferSession.setTransferAmount(100);

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertEquals(expectedRaisedAccountBalance,
                accountService.raisingAccountBalance(testTransferSession).getAccountBalance());
    }

    @Test
    public void shouldTransferFundsBetweenAccounts() {
        testTransferSession.setTransferAmount(100);

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertTrue(accountService.transferFundsBetweenAccounts(testTransferSession));
    }

    @Test
    public void shouldNotTransferFundsBetweenAccounts() {
        testTransferSession.setTransferAmount(300);

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertFalse(accountService.transferFundsBetweenAccounts(testTransferSession));
    }

    @Test
    public void shouldNotTransferFundsBetweenAccountsBecausetransferIsNotFinished() {
        testTransferSession.setTransferAmount(100);
        testTransferSession.setTransferFinished(false);

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertFalse(accountService.transferFundsBetweenAccounts(testTransferSession));
    }
}
