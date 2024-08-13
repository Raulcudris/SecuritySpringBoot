package com.security;
import com.security.models.ERole;
import com.security.models.RoleEntity;
import com.security.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.security.repositories.UserRepository;

import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityApplication {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

	/*@Bean
	CommandLineRunner init() {
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("raulcudriz@gmail.com")
					.username("raul")
					.password(passwordEncoder.encode("raul123"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			UserEntity userEntity2 = UserEntity.builder()
					.email("freddynavarro@gmail.com")
					.username("freddyNavarro")
					.password(passwordEncoder.encode("freddy123"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			UserEntity userEntity3 = UserEntity.builder()
					.email("josebarrios@gmail.com")
					.username("josebarrios")
					.password(passwordEncoder.encode("jose123"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);

		};
	}*/
}
