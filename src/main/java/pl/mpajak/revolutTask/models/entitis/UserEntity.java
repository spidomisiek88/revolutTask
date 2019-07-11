package pl.mpajak.revolutTask.models.entitis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(name = "is_delete")
    private int isDelete;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<AccountEntity> accounts;

    public boolean isDelete() {
        return isDelete > 0 ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return getId() == that.getId() &&
                getIsDelete() == that.getIsDelete() &&
                getName().equals(that.getName()) &&
                getAccounts().equals(that.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getIsDelete(), getAccounts());
    }
}
