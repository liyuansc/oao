package com.liyu.oao.uaa.security;

import com.liyu.oao.security.OaoUserDetails;
import com.liyu.oao.uaa.service.IUserService;
import com.liyu.oao.user.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OaoUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new OaoUserDetails(user.getUsername(), user.getPassword());
    }
}
