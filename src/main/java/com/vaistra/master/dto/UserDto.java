package com.vaistra.master.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private Integer userId;

    @NotEmpty(message = "Email shouldn't be empty.")
    @NotNull(message = "Email shouldn't be null.")
    @NotBlank(message = "Email shouldn't be blank.")
    @Size(min = 1, max = 250, message = "Email should have a length between 1 and 250 characters.")
    @Email(message = "Invalid email address")
    private String email;


    @NotEmpty(message = "Password shouldn't be empty.")
    @NotNull(message = "Password shouldn't be null.")
    @NotBlank(message = "Password shouldn't be blank.")
    @Size(min = 1, max = 128, message = "Password should have a length between 1 and 128 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

    @NotEmpty(message = "Name shouldn't be empty.")
    @NotNull(message = "Name shouldn't be null.")
    @NotBlank(message = "Name shouldn't be blank.")
    @Size(min = 1, max = 250, message = "Name should have a length between 1 and 250 characters.")
    private String name;

}
