package com.tt.calorie.repository;

import com.tt.calorie.model.CalorieIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CalorieIntakeRepository extends JpaRepository<CalorieIntake, Long> {

    List<CalorieIntake> findAllByUserId(Long userId);

    @Query("select cal from CalorieIntake cal where cal.intakeDate >= :after and cal.intakeDate <= :before")
    List<CalorieIntake> findAllByIntakeDates(Date after, Date before);

    @Query("select cal from CalorieIntake cal where cal.userId = :userId and (cal.intakeDate >= :after and cal.intakeDate <= :before)")
    List<CalorieIntake> findAllByUserIdAndIntakeDates(Long userId, Date after, Date before);
}
