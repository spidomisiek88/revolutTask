package pl.mpajak.revolutTask.models.entitis;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
@Data
public class AccountEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "account_balance")
    private double accountBalance;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "is_delete")
    private int isDelete;

    public boolean isDelete() {
        return isDelete > 0 ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity)) return false;
        AccountEntity that = (AccountEntity) o;
        return getId() == that.getId() &&
                Double.compare(that.getAccountBalance(), getAccountBalance()) == 0 &&
                getIsDelete() == that.getIsDelete() &&
                getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountBalance(), getUser(), getIsDelete());
    }
}
