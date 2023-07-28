package shpp.level4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tasks")
public class TodoTask {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @ManyToOne
        @JoinColumn(name = "status_id")
        private TaskStatus status;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created")
        private Date created;

        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "updated")
        private Date updated;

}
