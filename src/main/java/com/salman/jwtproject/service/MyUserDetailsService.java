package com.salman.jwtproject.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/* The UserDetailsService is a core interface in Spring Security framework,
    which is used to retrieve the user’s authentication and authorization information.  */
@Service
public class MyUserDetailsService implements UserDetailsService {

    /* You pass in a username and it is expected that you return a user from wherever you have stored that user's
     * details */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("foo", "foo", new ArrayList<>());
        // Right now, we are just returning a user with username and password 'foo'
        User userToReturn = searchUserFromList(username);
        if (userToReturn == null){
            throw new UsernameNotFoundException("Invalid User");
        }
        return userToReturn;
    }

    ArrayList<User> dummyUsers ;

    public MyUserDetailsService() {
        dummyUsers = new ArrayList<>();
        dummyUsers.add(new User("user1", "user1", new ArrayList<>()));
        dummyUsers.add(new User("user2", "user2", new ArrayList<>()));
        dummyUsers.add(new User("user3", "user3", new ArrayList<>()));
        dummyUsers.add(new User("user4", "user4", new ArrayList<>()));
        dummyUsers.add(new User("user5", "user5", new ArrayList<>()));
    }

    User searchUserFromList(String username){
        for (int i = 0; i < dummyUsers.size(); i++){
            if (dummyUsers.get(i).getUsername().equals(username)){
                return dummyUsers.get(i);
            }
        }
        return null;
    }

}
