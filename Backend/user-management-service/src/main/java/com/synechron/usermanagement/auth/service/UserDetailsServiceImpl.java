package com.synechron.usermanagement.auth.service;

import com.synechron.usermanagement.auth.model.CustomUserDetails;
import com.synechron.usermanagement.model.User;
import com.synechron.usermanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.error("Email not found: " + email);
            throw new UsernameNotFoundException("User not found for email: " + email);
        }
        logger.info("User authenticated successfully!");
        return new CustomUserDetails(user);
    }
}