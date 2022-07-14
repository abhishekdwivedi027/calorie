package com.tt.calorie.controller;

import com.google.common.base.Preconditions;
import com.tt.calorie.common.authorization.Permission;
import com.tt.calorie.model.User;
import com.tt.calorie.service.AuthorizationService;
import com.tt.calorie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    @PreAuthorize("hasAuthority('CRUD_USER')")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('CRUD_USER')")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CRUD_USER')")
    public User createUser(@RequestBody User user) {
        Preconditions.checkNotNull(user);
        return userService.createUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CRUD_USER')")
    public User updateUser(@RequestBody User user) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(userService.getUser(user.getId()));
        return userService.updateUser(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CRUD_USER')")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PATCH_CALORIE_LIMIT_FOR_SELF_ONLY')")
    public User patchUser(@PathVariable("id") Long id, @RequestBody Map<String, Object> changes) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(userService.getUser(id));
        Preconditions.checkNotNull(changes);
        Preconditions.checkNotNull(changes.get("calorieLimit"));
        if (authorizationService.isUserNotPermitted(id, Permission.PATCH_CALORIE_LIMIT_FOR_SELF_ONLY)) {
            throw new AccessDeniedException("Forbidden");
        }
        return userService.patchUser(id, Long.valueOf((Integer)changes.get("calorieLimit")));
    }
}
