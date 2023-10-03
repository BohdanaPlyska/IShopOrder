package com.example.ishoporder.model.request;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "name can't be blank")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @NotNull(message ="price can't be empty")
    private BigDecimal price;

    @Getter
    @NotNull(message ="price can't be empty")
    @Range(min = 1)
    private Integer amount;

}
