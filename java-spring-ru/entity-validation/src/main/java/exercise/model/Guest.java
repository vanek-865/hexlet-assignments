package exercise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;




@Entity
@Table(name = "guests")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // BEGIN
    @NotNull(message = "name is null")
    private String name;

    @Email(message = "email not valid")
    private String email;

    @Size(min = 11, max = 13, message = "phone number not valid size")
    @Pattern(regexp = "^\\+.*\\d$", message = "phone number not valid")
    private String phoneNumber;

    @Pattern(regexp = "^\\d{4}$", message = "card number not valid")
    private String clubCard;

    @NotNull(message = "card is null")
    @Future(message = "card not valid")
    private LocalDate cardValidUntil;
    // END

    @CreatedDate
    private LocalDate createdAt;
}
