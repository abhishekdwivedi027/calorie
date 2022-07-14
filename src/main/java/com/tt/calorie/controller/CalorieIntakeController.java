package com.tt.calorie.controller;

import com.google.common.base.Preconditions;
import com.tt.calorie.common.authorization.Permission;
import com.tt.calorie.model.CalorieIntake;
import com.tt.calorie.service.AuthorizationService;
import com.tt.calorie.service.CalorieIntakeService;
import com.tt.calorie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/calorieIntakes")
public class CalorieIntakeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CalorieIntakeService calorieIntakeService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_CALORIE_INTAKE_ENTRY')")
    public List<CalorieIntake> getCalorieIntakes() {
        return calorieIntakeService.get();
    }

    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasAuthority('READ_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY') " +
            "or hasAuthority('READ_CALORIE_INTAKE_ENTRY')")
    public List<CalorieIntake> getCalorieIntakes(@PathVariable("userId") Long userId) {
        Preconditions.checkNotNull(userId);
        Preconditions.checkNotNull(userService.getUser(userId));
        if (authorizationService.isUserNotPermitted(userId, Permission.READ_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY)) {
            throw new AccessDeniedException("Forbidden");
        }
        return calorieIntakeService.getByUserId(userId);
    }

    @GetMapping(value = "/filter")
    @PreAuthorize("hasAuthority('READ_CALORIE_INTAKE_ENTRY')")
    public List<CalorieIntake> getCalorieIntakesBetweenDates(@RequestParam(value = "after") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date after,
                                                             @RequestParam(value = "before", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date before) {
        return calorieIntakeService.getByDates(after, before);
    }

    @GetMapping(value = "/{userId}/filter")
    @PreAuthorize("hasAuthority('READ_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY') " +
            "or hasAuthority('READ_CALORIE_INTAKE_ENTRY')")
    public List<CalorieIntake> getCalorieIntakesBetweenDates(@PathVariable("userId") Long userId,
                                                             @RequestParam(value = "after") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date after,
                                                             @RequestParam(value = "before", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date before) {
        Preconditions.checkNotNull(userId);
        Preconditions.checkNotNull(userService.getUser(userId));
        if (authorizationService.isUserNotPermitted(userId, Permission.READ_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY)) {
            throw new AccessDeniedException("Forbidden");
        }
        return calorieIntakeService.getByUserIdAndDates(userId, after, before);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY') " +
            "or hasAuthority('CREATE_CALORIE_INTAKE_ENTRY')")
    public CalorieIntake createCalorieIntake(@RequestBody CalorieIntake calorieIntake) {
        Preconditions.checkNotNull(calorieIntake);
        Preconditions.checkNotNull(calorieIntake.getUserId());
        Preconditions.checkNotNull(userService.getUser(calorieIntake.getUserId()));
        if (authorizationService.isUserNotPermitted(calorieIntake.getUserId(), Permission.CREATE_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY)) {
            throw new AccessDeniedException("Forbidden");
        }
        return calorieIntakeService.create(calorieIntake);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('UPDATE_CALORIE_INTAKE_ENTRY')")
    public CalorieIntake updateCalorieIntake(@PathVariable("id") Long id, @RequestBody CalorieIntake calorieIntake) {
        Preconditions.checkNotNull(calorieIntake);
        Preconditions.checkNotNull(calorieIntakeService.getById(calorieIntake.getId()));
        return calorieIntakeService.update(calorieIntake);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('DELETE_CALORIE_INTAKE_ENTRY')")
    public void deleteCalorieIntake(@PathVariable("id") Long id) {
        calorieIntakeService.delete(id);
    }
}
