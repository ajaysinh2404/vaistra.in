package com.vaistra.master.dto.cscv_master;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CountryDto {
    private Integer countryId;

    @NotEmpty(message = "Country name shouldn't be empty.")
    @NotNull(message = "Country name shouldn't be null.")
    @NotBlank(message = "Country name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country name should only contain alphabets and spaces.")
    @Size(min = 3, max = 250, message = "Country name should have a length between 3 and 250 characters.")
    private String countryName;

    private Boolean isActive;

}
