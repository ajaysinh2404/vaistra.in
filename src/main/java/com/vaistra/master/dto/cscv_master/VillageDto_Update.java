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

public class VillageDto_Update {
    private Integer villageId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Village name should only contain alphabets and spaces.")
    @Size(min = 3, max = 250, message = "Village name should have a length between 3 and 250 characters.")
    private String villageName;

    private Boolean isActive;

    @NotNull(message = "Sub-district id shouldn't be null.")
    @Min(value=0,message = "Sub-district Id should be positive digits only.")
    private Integer subDistrictId;

    @NotNull(message = "District id shouldn't be null.")
    @Min(value=0,message = "District Id should be positive digits only.")
    private Integer districtId;

    @NotNull(message = "State id shouldn't be null.")
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

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "District name should only contain alphabets and spaces.")
    private String subDistrictName;

}
