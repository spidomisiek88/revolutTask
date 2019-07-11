package pl.mpajak.revolutTask.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mpajak.revolutTask.models.Services.UserService;
import pl.mpajak.revolutTask.models.Services.UserSession;
import pl.mpajak.revolutTask.models.entitis.UserEntity;
import pl.mpajak.revolutTask.models.repositoris.UserRepository;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserSession userSession;
    @InjectMocks
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    UserEntity testUser;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);

        testUser = new UserEntity();
        testUser.setId(0);
        testUser.setName("testUser");
        testUser.setIsDelete(0);
        testUser.setAccounts(new ArrayList<>());
    }
}
