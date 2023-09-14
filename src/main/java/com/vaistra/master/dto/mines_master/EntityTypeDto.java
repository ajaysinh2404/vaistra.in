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
public class EntityTypeDto {
    private Integer entityTypeId;

    @NotEmpty(message = "Entity Type shouldn't be empty.")
    @NotNull(message = "Entity Type  shouldn't be null.")
    @NotBlank(message = "Entity Type shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Entity Type should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Entity Type should have a length between 2 and 250 characters.")
    private String entityTypeName;

    @NotEmpty(message = "Short Name shouldn't be empty.")
    @NotNull(message = "Short Name shouldn't be null.")
    @NotBlank(message = "Short Name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Short Name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 50, message = "Short Name should have a length between 2 and 50 characters.")
    private String shortName;


}
