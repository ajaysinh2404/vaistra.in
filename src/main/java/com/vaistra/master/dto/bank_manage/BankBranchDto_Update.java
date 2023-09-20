package com.vaistra.master.dto.bank_manage;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class BankBranchDto_Update {
    private Integer branchId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Branch name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Branch name should have a length between 2 and 250 characters.")
    private String branchName;

    @Pattern(regexp = "^[A-Za-z0-9]{6}", message = "Invalid Branch Code.")
    private String branchCode;

    private String branchAddress;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC Code.")
    private String branchIfsc;

    @Pattern(regexp = "^[0-9+\\s]+$", message = "Phone Number should only contain numeric, + and spaces.")
    @Size(min = 10, max = 15, message = "Phone Number should have a length between 10 and 15 digits.")
    private String branchPhoneNumber;

    @Pattern(regexp = "^[0-9]{1,9}$", message = "Invalid MICR Code.")
    private String branchMicr;

    private LocalTime fromTiming;

    private LocalTime toTiming;

    @Min(value=0,message = "Bank Id should be positive digits only.")
    private Integer bankId;

    @Pattern(regexp = "^[a-zA-Z09\\s]+$", message = "Bank name should only contain alphabets,numeric and spaces.")
    private String bankName;

    @Min(value=0,message = "State Id should be positive digits only.")
    private Integer stateId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State name should only contain alphabets and spaces.")
    private String stateName;

    @NotNull(message = "District Id shouldn't be null.")
    @Min(value=0,message = "District Id should be positive digits only.")
    private Integer districtId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "District name should only contain alphabets and spaces.")
    private String districtName;

    private Boolean isActive;

}
