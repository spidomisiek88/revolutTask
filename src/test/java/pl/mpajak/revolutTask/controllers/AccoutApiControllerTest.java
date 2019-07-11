package pl.mpajak.revolutTask.controllers;

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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccoutApiController {

    @Mock
    AccountRepository accountRepository;
    @Mock
    UserSession userSession;
    @InjectMocks
    AccountService accountService;
    @Autowired
    MockMvc mockMvc;
    UserEntity testUser;
    AccountEntity testAccount;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDeletedAccount() throws Exception {
        int anyId = anyInt();

        Mockito.when(accountService.deleteAccountById(anyId)).thenReturn(true);

        mockMvc.perform(delete("/api/account/delete/" + anyId))
                .andExpect(status().isOk());
    }
}
