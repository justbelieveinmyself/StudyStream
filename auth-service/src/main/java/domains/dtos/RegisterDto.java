package domains.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    @NotBlank(message = "")
    private String username;
    @NotBlank(message = "")
    private String password;
    @Email(message = "")
    private String email;
}
