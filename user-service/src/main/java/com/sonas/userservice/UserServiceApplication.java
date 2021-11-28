package com.sonas.userservice;

import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
import com.sonas.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Random;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication implements  CommandLineRunner  {

	@Autowired
	private UserRepository userRepository;

	Random random = new Random();

	public void createUsers() {

		User user = new User();
		Long id = (long) random.nextInt(100);
		user.setUserId(id);
		user.setEmail("user" + id + "@gmail.com");
		user.setPassword("user" + id);
		user.setName("Mary");
		user.setLastName("Jane");
		user.setUsername("user" + id);
		user.setUserType(UserType.BASIC);
		user.setContactId(1L);
		User save = this.userRepository.save(user);
		System.out.println(save);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createUsers();
	}
}
