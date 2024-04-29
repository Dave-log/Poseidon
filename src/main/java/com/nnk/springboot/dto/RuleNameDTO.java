package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleNameDTO {

    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotBlank(message = "Json is mandatory")
    private String json;

    @NotBlank(message = "Template is mandatory")
    private String template;

    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;
}
