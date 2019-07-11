package pl.mpajak.revolutTask.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
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
import pl.mpajak.revolutTask.models.Services.UserSession;
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
    UserSession userSession;
    @InjectMocks
    AccountService accountService;
    @Autowired
    MockMvc mockMvc;
    UserEntity testUser;
    AccountEntity firstTestAccount;
    AccountEntity secondTestAccount;

//    @BeforeEach
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
    }

    @Test
    public void shouldUpdateAccountBalance() {
        double expectedAccountBalance = 100;

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertEquals(expectedAccountBalance, accountService.updateAccountBalance(firstTestAccount, expectedAccountBalance).getAccountBalance());
    }

    @Test
    public void shouldReduceAccountBalance() {
        double amountReduction = 100;

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertTrue(accountService.reductionAccountBalances(firstTestAccount, amountReduction));
    }

    @Test
    public void shouldNotReduceAccountBalance() {
        double amountReduction = 300;

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertFalse(accountService.reductionAccountBalances(firstTestAccount, amountReduction));
    }

    @Test
    public void shouldRaiseAccountBalance() {
        double amountRaise = 100;
        double expectedRaisedAccountBalance = 300;

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertEquals(expectedRaisedAccountBalance,
                accountService.raisingAccountBalance(firstTestAccount, amountRaise).getAccountBalance());
    }

    @Test
    public void shouldTransferFundsBetweenAccounts() {
        double transferAmount = 100;

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertTrue(accountService.transferFundsBetweenAccounts(firstTestAccount, secondTestAccount, transferAmount));
    }

    @Test
    public void shouldNotTransferFundsBetweenAccounts() {
        double transferAmount = 300;

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertFalse(accountService.transferFundsBetweenAccounts(firstTestAccount, secondTestAccount, transferAmount));
    }
}
