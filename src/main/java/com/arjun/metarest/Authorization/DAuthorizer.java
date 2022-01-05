package com.arjun.metarest.Authorization;

import com.arjun.metarest.domain.User;

import io.dropwizard.auth.Authorizer;

public class DAuthorizer implements Authorizer<User> 
{
    @Override
    public boolean authorize(User user, String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}