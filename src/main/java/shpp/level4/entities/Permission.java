package shpp.level4.entities;

import lombok.Data;
import shpp.level4.constants.Permissions;

import javax.persistence.*;

@Entity
@Data
@Table(name = "permissions")
public class Permission {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private Permissions name;
}
