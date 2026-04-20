package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component
public class AppBootstrapConfigurator implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppBootstrapConfigurator.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.enabled:true}")
    private boolean adminBootstrapEnabled;

    @Value("${APP_ADMIN_NAME:System Admin}")
    private String adminName;

    @Value("${APP_ADMIN_EMAIL:admin@moviebookingsystem.local}")
    private String adminEmail;

    @Value("${APP_ADMIN_PASSWORD:admin123}")
    private String adminPassword;

    @Value("${APP_ADMIN_PHONE:9999999999}")
    private String adminPhone;

    public AppBootstrapConfigurator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!adminBootstrapEnabled) {
            LOGGER.info("Admin bootstrap is disabled.");
            return;
        }

        if (userRepository.existsByEmail(adminEmail)) {
            LOGGER.info("Admin bootstrap skipped. User with email {} already exists.", adminEmail);
            return;
        }

        User adminUser = User.builder()
                .name(adminName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .phoneNumber(adminPhone)
                .role(Role.ADMIN)
                .build();

        userRepository.save(adminUser);
        LOGGER.info("Admin bootstrap completed. Created admin user with email {}.", adminEmail);
    }
}
