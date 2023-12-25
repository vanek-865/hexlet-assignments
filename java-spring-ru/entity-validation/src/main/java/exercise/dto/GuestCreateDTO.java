package exercise.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class GuestCreateDTO {

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
}
// END
