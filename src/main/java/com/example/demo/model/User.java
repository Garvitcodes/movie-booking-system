package com.example.demo.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique =true, nullable = false)
    private String phoneNumber;
}
