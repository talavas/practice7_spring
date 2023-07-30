package shpp.level4.entities;

import lombok.Data;
import shpp.level4.constants.TaskStatuses;

import javax.persistence.*;

@Entity
@Data
@Table(name = "statuses")
public class TaskStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TaskStatuses name;
}
