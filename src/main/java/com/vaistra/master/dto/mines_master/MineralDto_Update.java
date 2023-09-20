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

public class MineralDto_Update {
    private Integer mineralId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Mineral Name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Mineral Name should have a length between 2 and 250 characters.")
    private String mineralName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Category only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Category should have a length between 2 and 250 characters.")
    private String category;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "ATR Name only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "ATR Name should have a length between 2 and 250 characters.")
    private String atrName;

    @Pattern(regexp = "^\\d{6}(\\d{2})?$", message = "Invalid HSN Code.")
    @Size(min = 6, max = 8, message = "HSN code should have a length between 6 and 8 characters.")
    private String hsnCode;

    private String[] grade;

}
