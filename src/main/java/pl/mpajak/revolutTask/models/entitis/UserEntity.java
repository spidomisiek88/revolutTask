package pl.mpajak.revolutTask.models.entitis;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(name = "is_delete")
    private int isDelete;

    @OneToMany(mappedBy = "user")
    List<Account> accounts;
}
