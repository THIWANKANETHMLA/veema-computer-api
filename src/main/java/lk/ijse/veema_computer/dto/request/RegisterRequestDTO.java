package lk.ijse.veema_computer.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(
            min = 3,
            max = 50,
            message = "Username must contain 3 to 50 characters"
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "Username can contain letters, numbers, dots, underscores and hyphens"
    )
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(
            min = 8,
            max = 72,
            message = "Password must contain 8 to 72 characters"
    )
    private String password;

    @JsonIgnore
    @AssertTrue(message = "Password must not exceed 72 bytes")
    public boolean isPasswordWithinByteLimit() {
        return password == null
                || password.getBytes(StandardCharsets.UTF_8).length <= 72;
    }
}