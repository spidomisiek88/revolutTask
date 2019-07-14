package pl.mpajak.revolutTask.models.Services;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import pl.mpajak.revolutTask.models.entitis.AccountEntity;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class TransferSession {

    private double transferAmount;
    private AccountEntity sendingAccount;
    private AccountEntity receivingAccount;
    private boolean isTransferFinished;
}
