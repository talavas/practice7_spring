package shpp.level4.entities;

import lombok.Data;
import shpp.level4.constants.Roles;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Roles name;

}
