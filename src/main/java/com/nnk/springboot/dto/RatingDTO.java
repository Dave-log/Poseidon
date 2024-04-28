package com.nnk.springboot.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    @NotNull
    private Integer id;

    private String moodysRating;
    private String sandPRating;
    private String fitchRating;

    @NotNull(message = "Order Number is mandatory")
    @Digits(integer = 10, fraction = 0, message = "orderNumber must be a integer")
    private Integer orderNumber;
}
