package com.example.sweetshop.config;

import com.example.sweetshop.model.Role;
import com.example.sweetshop.model.Role.ERole;
import com.example.sweetshop.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
	  return args -> {
	    if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
	      roleRepository.save(new Role(ERole.ROLE_USER));
	    }
	    if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
	      roleRepository.save(new Role(ERole.ROLE_ADMIN));
	    }
	  };
	}

}
