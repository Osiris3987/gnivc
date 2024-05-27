package com.example.gateway_service_gnivc.security.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private String userId;
    private String username;
    private List<String> roles;
    //private Set<String> roles; //replace string with enum, to make static roles?
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapToGrantedAuthorities(roles);
    }

    @Override
    public String getPassword() {
        return "default";
    }

    @Override
    public String getUsername() {
        return username;
    }

    public List<String> getRoles(){
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
