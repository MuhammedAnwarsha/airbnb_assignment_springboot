package com.airbnb.service;

import com.airbnb.config.JwtProvider;
import com.airbnb.model.User;
import com.airbnb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String username = JwtProvider.getUsernameFromToken(jwt);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new Exception("user not found with id: " + userId);
        }
        return user.get();
    }
}
