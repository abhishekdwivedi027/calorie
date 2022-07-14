package com.tt.calorie.model;

import com.tt.calorie.common.authorization.Role;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column
    private Long age;
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column
    private Double weight;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "calorie_limit", nullable = false)
    private Long calorieLimit;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String token;
}
