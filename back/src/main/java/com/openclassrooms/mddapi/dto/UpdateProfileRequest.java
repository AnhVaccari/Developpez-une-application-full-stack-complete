package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @Email
    private String email;

    @Size(min = 3, max = 50)
    private String username;

    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).*$", message = "Le mot de passe doit contenir au moins : un chiffre, une minuscule, une majuscule et un caractère spécial")
    private String password;

}
