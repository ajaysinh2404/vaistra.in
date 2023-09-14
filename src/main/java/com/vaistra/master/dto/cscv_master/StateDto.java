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

public class StateDto {
    private Integer stateId;

    @NotEmpty(message = "State name shouldn't be empty.")
    @NotNull(message = "State name shouldn't be null.")
    @NotBlank(message = "State name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State name should only contain alphabets and spaces.")
    @Size(min = 3, max = 250, message = "State name should have a length between 3 and 250 characters.")
    private String stateName;

    private Boolean isActive;

    @NotNull(message = "Country Id shouldn't be null.")
    @Min(value=0,message = "Country Id should be positive digits only.")
    private Integer countryId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country name should only contain alphabets and spaces.")
    private String countryName;


}
