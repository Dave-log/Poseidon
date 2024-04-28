package com.nnk.springboot.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidListDTO {

        @NotNull
        private Integer id;

        @NotBlank(message = "Account is mandatory")
        @Size(max = 30)
        private String account;

        @NotBlank(message = "Type is mandatory")
        @Size(max = 30)
        private String type;

        @NotNull(message = "Bid quantity is mandatory")
        @Digits(integer = 3, fraction = 2, message = "Bid quantity allows two digits after the decimal point")
        private Double bidQuantity;
}
