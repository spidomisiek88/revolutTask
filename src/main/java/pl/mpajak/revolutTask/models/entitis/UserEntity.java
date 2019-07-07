package pl.mpajak.revolutTask.models.entitis;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    List<AccountEntity> accounts;

    public boolean isDelete() {
        return isDelete > 0 ? true : false;
    }
}
