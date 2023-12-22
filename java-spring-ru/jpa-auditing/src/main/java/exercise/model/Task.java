package exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

// BEGIN
@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String description;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDate updatedAt;
}
// END
