package com.weekend.mango.security.services;

import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.repositories.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserEntityRepository userEntityRepository;


    public UserDetailsServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsImpl.build(user);
    }


}
