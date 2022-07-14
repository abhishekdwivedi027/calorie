package com.tt.calorie;

import com.tt.calorie.common.authorization.Role;
import com.tt.calorie.model.Gender;
import com.tt.calorie.model.User;
import com.tt.calorie.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalorieApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	// TODO
	// Documentation
	// Testing
	// Logging
	// Error Handling

	public static void main(String[] args) {
		SpringApplication.run(CalorieApplication.class, args);
	}

	@Override
	public void run(String... args) {

		if (CollectionUtils.isEmpty(userService.getUsers())) {
			User superuser = new User();
			superuser.setFirstName("TopTal");
			superuser.setLastName("Administrator");
			superuser.setGender(Gender.OTHERS);
			superuser.setRole(Role.ADMIN);
			superuser.setCalorieLimit(1000L);
			superuser.setUsername("James");
			superuser.setPassword("Bond");
			userService.createUser(superuser);
		}
	}
}
