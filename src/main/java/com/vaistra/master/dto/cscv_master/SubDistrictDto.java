package com.vaistra.master.dto.cscv_master;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SubDistrictDto {
    private Integer subDistrictId;

    @NotEmpty(message = "Sub-district name shouldn't be empty.")
    @NotNull(message = "Sub-district name shouldn't be null.")
    @NotBlank(message = "Sub-district name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Sub-district name should only contain alphabets and spaces.")
    @Size(min = 3, max = 250, message = "Sub-district name should have a length between 3 and 250 characters.")
    private String subDistrictName;

    private Boolean isActive;

    @NotNull(message = "District id shouldn't be null.")
    @Min(value=0,message = "District Id should be positive digits only.")
    private Integer districtId;

    @Min(value=0,message = "State Id should be positive digits only.")
    private Integer stateId;

    @Min(value=0,message = "Country Id should be positive digits only.")
    private Integer countryId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country name should only contain alphabets and spaces.")
    private String countryName;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State name should only contain alphabets and spaces.")
    private String stateName;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "District name should only contain alphabets and spaces.")
    private String districtName;

}
