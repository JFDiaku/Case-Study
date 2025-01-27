package com.capstone.CaseStudy.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private String image;
    private String fullName;
    private String firstName;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             boolean accountIsEnabled, boolean accountNonExpired, boolean credentialsNonExpired ,
                             boolean accountNonLocked,  String image, String fullName, String firstName) {
        super(username, password, accountIsEnabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities );
        this.image = image ;
        this.fullName = fullName;
        this.firstName = firstName;
    }

    public String getImage() {
        return image;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {return firstName;}

}
