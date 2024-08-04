package com.airbnb.service;

import com.airbnb.model.User;

public interface UserServices {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserById(Long userId) throws Exception;
}
