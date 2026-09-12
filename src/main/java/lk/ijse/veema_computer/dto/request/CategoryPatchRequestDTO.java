package lk.ijse.veema_computer.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryPatchRequestDTO {

    @Size(
            min = 2,
            max = 100,
            message = "Category name must contain 2 to 100 characters"
    )
    private String name;

    @Size(
            max = 500,
            message = "Description cannot exceed 500 characters"
    )
    private String description;

    private Boolean active;
}
