package pl.mpajak.revolutTask.models.entitis;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "account_balance")
    private double accountBalance;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "is_delete")
    private int isDelete;
}