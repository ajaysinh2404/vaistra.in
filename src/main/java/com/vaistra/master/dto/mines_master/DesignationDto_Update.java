package com.vaistra.master.dto.mines_master;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DesignationDto_Update {
    private Integer designationId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Designation should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Designation should have a length between 2 and 250 characters.")
    private String designationName;

}
