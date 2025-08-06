package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @Email
    private String email;

    @Size(min = 3, max = 50)
    private String username;

    @Size(min = 8)
    private String password;

}
