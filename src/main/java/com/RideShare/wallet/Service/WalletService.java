package com.RideShare.wallet.Service;

import com.RideShare.Response.ApiResponseClass;
import com.RideShare.reservation.Repository.ReservationRepository;
import com.RideShare.utils.Service.UtilsService;
import com.RideShare.utils.Validator.ObjectsValidator;
import com.RideShare.user.Repository.ClientRepository;
import com.RideShare.utils.exception.ApiRequestException;
import com.RideShare.wallet.Model.MoneyCode;
import com.RideShare.wallet.Model.Wallet;
import com.RideShare.wallet.Repository.MoneyCodeRepository;
import com.RideShare.wallet.Repository.WalletRepository;
import com.RideShare.wallet.Request.CreateWalletRequest;
import com.RideShare.wallet.Request.MoneyCodeRequest;
import com.RideShare.wallet.Response.MoneyCodeResponse;
import com.RideShare.wallet.Response.WalletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private MoneyCodeRepository moneyCodeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UtilsService utilsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectsValidator<CreateWalletRequest> createWalletValidator;
    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public ApiResponseClass CreateMyWallet(CreateWalletRequest request) {
        createWalletValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getName();

        var client = clientRepository.findByPhone(phone).orElseThrow(
                () -> new ApiRequestException("Client not found")
        );

//        if (client.getWallet() == null) {
            var wallet = Wallet.builder()
                    .client(client)
                    .balance(0.0)
                    .bankAccount(request.getBankAccount())
                    .securityCode(passwordEncoder.encode(request.getSecurityCode()))
                    .build();

            walletRepository.save(wallet);

            return new ApiResponseClass("Wallet Added Successfully", HttpStatus.CREATED, LocalDateTime.now(), wallet);
//        }
//        else
//        {
//            if(request.getBankAccount()!=null)
//                client.getWallet().setBankAccount(request.getBankAccount());
//            clientRepository.save(client);
//            return new ApiResponseClass("Wallet Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
//
//        }

    }

//    @Scheduled(timeUnit = TimeUnit.MINUTES , fixedRate = 30)
    public void createNewCodes(){
        MoneyCode moneyCode= MoneyCode.builder()
                .code("Wassem" + LocalDateTime.now())
                .balance(200000.0)
                .valid(true)
                .build();
        MoneyCode moneyCode2= MoneyCode.builder()
                .code("Abd-alaziz" + LocalDateTime.now())
                .balance(50000.0)
                .valid(true)
                .build();

        moneyCodeRepository.save(moneyCode);
        moneyCodeRepository.save(moneyCode2);
    }

    @Transactional
    public ApiResponseClass AddMoneyToWallet(MoneyCodeRequest request) {

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client =  clientRepository.findByPhone(authentication.getName()).orElseThrow(
                ()-> new ApiRequestException("Client not found")
        );

        if (client.getWallet()==null)
            throw new ApiRequestException("PLEASE CREATE WALLET FIRST");

        var foundcode = moneyCodeRepository.findMoneyCodeByCode(request.getCode());

        if (foundcode==null)
            throw new ApiRequestException("CODE NOT CORRECT");
        if (foundcode.isValid())
        {
            client.getWallet().setBalance(foundcode.getBalance()+client.getWallet().getBalance());
            clientRepository.save(client);
            foundcode.setValid(false);
            moneyCodeRepository.save(foundcode);

            return new ApiResponseClass("Money Added To Wallet Successfully",
                    HttpStatus.ACCEPTED,
                    LocalDateTime.now(),
                    WalletResponse.builder().balance(client.getWallet().getBalance()).build());
        }
        throw new ApiRequestException("CODE NOT VALID");
    }

    public ApiResponseClass GetMyWallet() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client =  clientRepository.findByPhone(authentication.getName()).get();

        if (client.getWallet()==null)
            throw new ApiRequestException("PLEASE CREATE WALLET FIRST");

        return new ApiResponseClass("Wallet Returned Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),client.getWallet());

    }


    public ApiResponseClass DeleteMyWallet() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client =  clientRepository.findByPhone(authentication.getName()).get();

        if (client.getWallet()==null)
            throw new ApiRequestException("PLEASE CREATE WALLET FIRST");


//        if (client.getWallet().getBalance()!=0)
//        {
//            EmailStructure emailStructure=EmailStructure.builder()
//                    .subject(" Money Added To Your Bank Account")
//                    .message("Mr. "+client.getFirst_name() +",Your Money In The Wallet Added to Your Bank Account After You Delete Your Wallet  , "+ client.getWallet().getBalance()+"$ Added To Your Account" )
//                    .build();
//            emailService.sendMail(client.getEmail(),emailStructure);
//        }
        walletRepository.delete(client.getWallet());

        return new ApiResponseClass("Wallet Deleted Successfully",HttpStatus.ACCEPTED,LocalDateTime.now());

    }

    public ApiResponseClass getAllValidCode(){
        List<MoneyCode> codes = moneyCodeRepository.findMoneyCodeByValidIsTrue();
        List<MoneyCodeResponse> moneyCodeList = new ArrayList<>();
        for (MoneyCode moneyCode : codes) {
            moneyCodeList.add(MoneyCodeResponse.builder()
                    .id(moneyCode.getId())
                    .code(moneyCode.getCode())
                    .amount(moneyCode.getBalance())
                    .build());
        }
        return new ApiResponseClass("Code List",HttpStatus.ACCEPTED,LocalDateTime.now(),moneyCodeList);
    }
}
