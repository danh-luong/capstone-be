package core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Column(name = "username", length = 500)
    private String username;
    @Column(name = "password", length = 500)
    private String password;
    @Column(name = "fullname", length = 500)
    private String fullname;
    @Column(name = "status", length = 500)
    private String status;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "token")
    private String token;
}
