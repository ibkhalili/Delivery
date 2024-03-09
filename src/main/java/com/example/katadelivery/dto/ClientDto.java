package com.example.katadelivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientDto {
    private String firstName;
    private String lastName;

    @NotBlank(message = "email is mandatory")
    private String email;
    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Size(min = 6, max = 10)
    private String password;

}
