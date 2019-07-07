package pl.mpajak.revolutTask.services;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCollectionOfAccounts() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("TestUser");
        userEntity.setIsDelete(0);

        AccountEntity firstAccout = new AccountEntity();
        firstAccout.setAccountBalance(400);
        firstAccout.setIsDelete(0);
        firstAccout.setUser(userEntity);

        AccountEntity secondAccout = new AccountEntity();
        secondAccout.setAccountBalance(500);
        secondAccout.setIsDelete(0);
        secondAccout.setUser(userEntity);

        List<AccountEntity> expectedStructureAccountEntities = new ArrayList<>();
        expectedStructureAccountEntities.add(firstAccout);
        expectedStructureAccountEntities.add(secondAccout);

        Iterable<AccountEntity> actualStructureAccountEntities = accountService.getAllAccaunts();

        assertThat(actualStructureAccountEntities, equalTo(expectedStructureAccountEntities));
    }

    @Test
    public void shouldDeletedAccount() throws Exception {
        int anyId = anyInt();

        Mockito.when(accountService.deleteAccountById(anyId)).thenReturn(true);

        mockMvc.perform(delete("/api/account/delete/" + anyId))
                .andExpect(status().isOk());
    }
}
