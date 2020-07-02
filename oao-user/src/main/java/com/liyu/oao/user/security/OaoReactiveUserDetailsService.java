package com.liyu.oao.user.security;

import com.liyu.oao.security.OaoUserDetails;
import com.liyu.oao.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OaoReactiveUserDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findByUsernameMono(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                .map(user -> new OaoUserDetails(user.getUsername(), user.getPassword()));
    }
}
