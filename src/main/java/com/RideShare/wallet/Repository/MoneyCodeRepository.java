package com.RideShare.wallet.Repository;

import com.RideShare.wallet.Model.MoneyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyCodeRepository  extends JpaRepository<MoneyCode,Integer> {

    MoneyCode findMoneyCodeByCode(String code);

    List<MoneyCode> findMoneyCodeByValidIsTrue();
}
