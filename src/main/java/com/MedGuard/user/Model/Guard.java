package com.MedGuard.user.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Guard extends BaseUser  {

    private LocalDate birthDate;
    private Double rank;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return role.getAuthorities();
//    }


}
