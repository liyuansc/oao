package com.liyu.oao.uaa.security;

import com.liyu.oao.api.model.LoginUser;
import com.liyu.oao.common.model.OaoGrantedAuthority;
import com.liyu.oao.security.OaoUserDetails;
import com.liyu.oao.user.feign.IUserClient;
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
    private IUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = userClient.findLoginUserByUsername(username);
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
