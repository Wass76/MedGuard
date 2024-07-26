package com.CareemSystem.user.Model;

import com.CareemSystem.wallet.Model.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table
public class Client extends BaseUser implements UserDetails {


    private Boolean active;

    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "client")
    private Wallet wallet;



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
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
