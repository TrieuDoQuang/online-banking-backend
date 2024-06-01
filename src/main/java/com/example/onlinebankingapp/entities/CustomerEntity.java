package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

//in charge: Trieu
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="customers") // Specifies the name of the table in the database
public class CustomerEntity extends AbstractUser implements OAuth2User, UserDetails // Implementing OAuth2User and UserDetails interfaces
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name="pin_number", length = 6)
    private Long pinNumber; // Represents the PIN number of the customer

    @Column(name = "is_active")
    private boolean active; // Indicates whether the customer's account is active

    // Implementing OAuth2User and UserDetails interfaces
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // Returning true as default implementation

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // Returning true as default implementation

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // Returning true as default implementation

    @Override
    public boolean isEnabled() {
        return true;
    } // Returning true as default implementation
}
