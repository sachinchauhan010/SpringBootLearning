package org.learning.authify.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

    @NotBlank(message = "Name should not be Empty")
    private String name;
    @Email
    @NotBlank(message = "Email should not be Empty")
    private String email;
    @Size(min = 6, message = "Password should be atleast 6 character")
    private String password;
    private Boolean isAccountVerified;

}
