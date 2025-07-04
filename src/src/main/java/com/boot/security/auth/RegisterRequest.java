package com.boot.security.auth;

import com.boot.security.auth.validation.UserEmailValid;
import com.boot.security.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank
  private String firstname;
  private String lastname;
  @Email
  @NotBlank
  @UserEmailValid(message = "Email já cadastrado")
  private String email;
  @NotBlank
  private String password;

  private Role role;
}
