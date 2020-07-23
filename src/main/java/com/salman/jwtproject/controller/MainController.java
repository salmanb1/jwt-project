package com.salman.jwtproject.controller;

import com.salman.jwtproject.model.AuthenticationRequest;
import com.salman.jwtproject.model.AuthenticationResponse;
import com.salman.jwtproject.service.JwtUtil;
import com.salman.jwtproject.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;    // This object authenticates users

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping({ "/hello" })
    public String hello() {
        return "<h1> Helloooooooo!!! </h1>";
    }

    /* Client passes in username & pw and the end result should be a ResponseEntity that carries the token */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            System.out.println("Incorrect username or password");
            throw new Exception("Incorrect username or password", e);
        }

        // Token needs user details from the user object
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        System.out.println("UN: " + userDetails.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // .ok means 200 msg and the payload of the ResponseEntity will contain the JWT
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
