package com.tt.calorie.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "calorie_intakes")
public class CalorieIntake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "food_name")
    private String foodName;
    @Column(name = "calorie_value", nullable = false)
    private Long calorieValue;
    @Column(name = "intake_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date intakeDate;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
