package com.psgv.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.enums.ProfileEnum;
import com.psgv.helpdesk.api.repository.UserRepository;

@SpringBootApplication
public class HelpDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}
	
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            initUsers(userRepository);
        };
    }
    
	private void initUsers(UserRepository userRepository) {
        User admin = new User();
        admin.setEmail("admin@helpdesk.com");
        admin.setPassword("123456");
        admin.setProfile(ProfileEnum.ROLE_ADMIN);

        User find = userRepository.findByEmail("admin@helpdesk.com");
        if (find == null) {
            userRepository.save(admin);
        }
    }
}
