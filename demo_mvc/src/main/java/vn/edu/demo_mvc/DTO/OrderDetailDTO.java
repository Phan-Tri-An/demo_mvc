package vn.edu.demo_mvc.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class OrderDetailDTO {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
