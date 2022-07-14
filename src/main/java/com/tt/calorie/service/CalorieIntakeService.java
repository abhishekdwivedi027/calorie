package com.tt.calorie.service;

import com.tt.calorie.model.CalorieIntake;
import com.tt.calorie.repository.CalorieIntakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CalorieIntakeService {

    @Autowired
    private CalorieIntakeRepository calorieIntakeRepository;

    public List<CalorieIntake> get() {
        return calorieIntakeRepository.findAll();
    }

    public CalorieIntake getById(Long id) {
        return calorieIntakeRepository.getById(id);
    }

    public List<CalorieIntake> getByUserId(Long userId) {
        return calorieIntakeRepository.findAllByUserId(userId);
    }

    public List<CalorieIntake> getByDates(Date after, Date before) {
        if (before == null) {
            before = new Date();
        }
        return calorieIntakeRepository.findAllByIntakeDates(after, before);
    }

    public List<CalorieIntake> getByUserIdAndDates(Long userId, Date after, Date before) {
        if (before == null) {
            before = new Date();
        }
        return calorieIntakeRepository.findAllByUserIdAndIntakeDates(userId, after, before);
    }

    public CalorieIntake create(CalorieIntake calorieIntake) {
        if (calorieIntake.getCreatedAt() == null) {
            calorieIntake.setCreatedAt(LocalDateTime.now());
        }
        return calorieIntakeRepository.save(calorieIntake);
    }

    public CalorieIntake update(CalorieIntake calorieIntake) {
        return calorieIntakeRepository.save(calorieIntake);
    }

    public void delete(Long id) {
        calorieIntakeRepository.deleteById(id);
    }
}
