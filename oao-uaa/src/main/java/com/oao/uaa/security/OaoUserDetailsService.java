package com.oao.uaa.security;

import com.oao.common.model.OaoGrantedAuthority;
import com.oao.security.OaoUserDetails;
import com.oao.user.feign.UserClient;
import com.oao.user.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class OaoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = userClient.findLoginUserByUsername(username).check().getData();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<GrantedAuthority> as = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(new OaoGrantedAuthority(role.getId(), role.getCode()).getAuthority()))
                .collect(Collectors.toSet());
        return new OaoUserDetails(user.getId(), user.getUsername(), user.getPassword(), as);
    }
}
