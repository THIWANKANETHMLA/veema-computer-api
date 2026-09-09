package lk.ijse.veema_computer.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private int status;
    private T body;
    private String message;

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}