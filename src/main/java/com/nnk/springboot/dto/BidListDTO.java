package com.nnk.springboot.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BidListDTO(
        @NotBlank(message = "Account is mandatory")
        @Size(max = 30)
        String account,

        @NotBlank(message = "Type is mandatory")
        @Size(max = 30)
        String type,

        @NotNull(message = "Bid quantity is mandatory")
        @Digits(integer = 3, fraction = 2, message = "Bid quantity allows two digits after the decimal point")
        Double bidQuantity)
{}
