package com.vaistra.master.dto.mines_master;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EquipmentDto {
    private Integer equipmentId;

    @NotEmpty(message = "Equipment Name shouldn't be empty.")
    @NotNull(message = "Equipment Name shouldn't be null.")
    @NotBlank(message = "Equipment Name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Equipment Name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Equipment Name should have a length between 2 and 250 characters.")
    private String equipmentName;

}
