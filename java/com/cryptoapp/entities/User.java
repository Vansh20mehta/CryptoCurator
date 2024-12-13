package com.cryptoapp.entities;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long userId;
        
        private String name;
        private String email;
        private String password;

        private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

        private String phonenumber;   

        private Providers provider=Providers.SELF;
        private Providers providerId;






        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }
    
        public String getPassword() {
            return password;
        }
    
        @Override
        public String getUsername() {
            return email;
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

       
        

}
