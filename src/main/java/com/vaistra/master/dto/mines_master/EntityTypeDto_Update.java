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

public class EntityTypeDto_Update {
    private Integer entityTypeId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Entity Type should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Entity Type should have a length between 2 and 250 characters.")
    private String entityTypeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Short Name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 50, message = "Short Name should have a length between 2 and 50 characters.")
    private String shortName;

}
