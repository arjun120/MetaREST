package com.arjun.metarest.Authentication;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
 

import java.util.Optional;


import com.arjun.metarest.dao.UserDAO;
import com.arjun.metarest.domain.User;
import com.google.common.collect.ImmutableSet;

public class DAuthenticator implements Authenticator<BasicCredentials, User>{

    private final UserDAO userdao;

    public DAuthenticator(UserDAO userdao) {
        this.userdao = userdao;
    }
 
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException 
    {
        if (!userdao.exists(credentials.getUsername())){
            return Optional.empty();
        }
        if (userdao.getPassword(credentials.getUsername()).equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername(), ImmutableSet.of("ADMIN", "USER")));
        }
        return Optional.empty();
    }
    
}
