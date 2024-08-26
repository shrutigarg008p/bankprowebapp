package com.bankpro.app.security;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankpro.app.dao.UserRegistorRepository;
import com.bankpro.app.model.Users;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * UserDetails service that reads the user credentials from the database, using a JPA repository.
 *
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserRegistorRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<?> li = userRepository.getUserForLogin(username);

        if (li == null || li.isEmpty()) {
            String message = "Username not found" + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
        Object[] rowObj = (Object[]) li.get(0);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found user in database: " + rowObj[0].toString());

        return new org.springframework.security.core.userdetails.User(username, rowObj[1].toString(), authorities);
    }
}
