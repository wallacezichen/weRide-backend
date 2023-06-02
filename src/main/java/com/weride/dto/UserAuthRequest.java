package com.weride.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserAuthRequest {

  @NotBlank(message = "should not be blank")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@ucsd\\.edu$", message = "should be ending with @ucsd.edu")
  private String email;


  @NotBlank(message = "should not be blank")
  @Size(min = 8, message = "length should be 8 or longer")
  private String password;
}



