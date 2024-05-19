package com.example.onlinebankingapp.controllers;
import com.example.onlinebankingapp.dtos.CustomerDTO;
import com.example.onlinebankingapp.dtos.CustomerLoginDTO;
import com.example.onlinebankingapp.dtos.RefreshTokenDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TokenEntity;
import com.example.onlinebankingapp.responses.Customer.LoginResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.Customer.CustomerResponse;
import com.example.onlinebankingapp.services.Customer.CustomerService;
import com.example.onlinebankingapp.services.Token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final TokenService tokenService;

    @PostMapping("/insertCustomer")
    public ResponseEntity<?> insertCustomer(@Valid @RequestBody CustomerDTO customerDTO)
    {
        try {
            CustomerEntity customerEntityResponse = customerService.insertCustomer(customerDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .result(CustomerResponse.fromUserResponse(customerEntityResponse))
                            .message("Insert successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody CustomerLoginDTO customerLoginDTO,  HttpServletRequest request)
    {
        try {
            String token = customerService.login(customerLoginDTO);
            String customerAgent = request.getHeader("Customer-Agent");
            CustomerEntity customer = customerService.getCustomerDetailsFromToken(token);
            TokenEntity jwtToken = tokenService.addToken(customer, token, isMobileDevice(customerAgent));

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
                    .refreshToken(jwtToken.getRefreshToken())
                    .fullName(customer.getName())
                    .id(customer.getId())
                    .build();
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .result(loginResponse)
                            .message("Login successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken (@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        try {
        CustomerEntity customer = customerService.getCustomerDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());
        TokenEntity jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), customer);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken.getToken())
                .tokenType(jwtToken.getTokenType())
                .refreshToken(jwtToken.getRefreshToken())
                .fullName(customer.getName())
                .id(customer.getId())
                .build();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .result(loginResponse)
                        .message("Refresh token successfully")
                        .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    private boolean isMobileDevice(String customerAgent) {
        // Kiểm tra Customer-Agent header để xác định thiết bị di động
        // Ví dụ đơn giản:
        return customerAgent != null && customerAgent.toLowerCase().contains("mobile");
    }
}
