package com.RideShare.wallet.Controller;

import com.RideShare.wallet.Request.CreateWalletRequest;
import com.RideShare.wallet.Request.MoneyCodeRequest;
import com.RideShare.wallet.Service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "My Wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping
    @Operation(
            description = "This end point build to get my wallet information",
            summary = "Get my wallet info",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getMyWallet(){
        return ResponseEntity.ok().body(walletService.GetMyWallet());
    }
    @PostMapping
    @Operation(
            description = "This endpoint build to create personal wallet to client",
            summary = "Create Wallet for user",
            responses = {
                    @ApiResponse(
                            description = "Done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> createWallet(@RequestBody CreateWalletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.CreateMyWallet(request));
    }
    @PutMapping
    @Operation(
            description = "This endpoint build to add money to my wallet by enter moneyCode from money card",
            summary = "Add money to my wallet by code",
            responses = {
                    @ApiResponse(
                            description = "Add money done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> addMoneyToWallet(@RequestBody MoneyCodeRequest request){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(walletService.AddMoneyToWallet(request));
    }

    @GetMapping("All-valid-codes")
    public ResponseEntity<?> getAllValidCodes(){
        return ResponseEntity
                .ok(walletService.getAllValidCode());
    }
}
