package vn.edu.demo_mvc.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data

public class OrderRequestDTO {
    @NotNull
    private Long customerId;

    private String voucherCode;

    @NotEmpty
    private List<OrderDetailDTO> item;
}
