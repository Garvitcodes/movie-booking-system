package com.example.demo.dto.request;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
