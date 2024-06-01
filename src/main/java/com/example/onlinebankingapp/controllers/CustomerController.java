package com.example.onlinebankingapp.controllers;
import com.example.onlinebankingapp.dtos.ChangePasswordCustomerDTO;
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
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final TokenService tokenService;

    //This endpoint allows clients to insert a new customer into the system/ registering account.
    //In charge: Trieu
    @PostMapping("/insertCustomer")
    public ResponseEntity<?> insertCustomer(@Valid @RequestBody CustomerDTO customerDTO)
    {
        try {
            //insert a customer
            CustomerEntity customerEntityResponse = customerService.insertCustomer(customerDTO);

            //return data in response
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .result(CustomerResponse.fromCustomerResponse(customerEntityResponse))
                            .message("Insert successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    //This endpoint perform login action
    //In charge: Trieu
    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody CustomerLoginDTO customerLoginDTO,  HttpServletRequest request)
    {
        try {
            //authenticate login using email, password in DTO
            String token = customerService.login(customerLoginDTO);

            //build response and jwt token
            String customerAgent = request.getHeader("Customer-Agent");
            CustomerEntity customer = customerService.getCustomerDetailsFromToken(token);
            TokenEntity jwtToken = tokenService.addToken(customer, token, isMobileDevice(customerAgent));

            //build response
            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
                    .refreshToken(jwtToken.getRefreshToken())
                    .fullName(customer.getName())
                    .id(customer.getId())
                    .build();

            //return response with jwt token and user info
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

    //This endpoint allows clients to refresh an expired access token using a valid refresh token
    //In charge: Trieu
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken (@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        try {
            // Retrieve customer details from the refresh token
        CustomerEntity customer = customerService.getCustomerDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());

            // Refresh the access token
        TokenEntity jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), customer);

            // Return a successful response with the new access token
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

    //end point for getting a customer by its id
    // in charge: Dat
    @GetMapping("/getById/{customerId}")
    public ResponseEntity<?> getCustomerById(@Valid @PathVariable("customerId") long customerId)
    {
        try {
            //getting a customer by its id
            CustomerEntity customerEntityResponse = customerService.getCustomerById(customerId);

            //return result in response
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .result(CustomerResponse.fromCustomerResponse(customerEntityResponse))
                            .message("get Pin Number successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    //end point for changing password of a customer
    // in charge: Dat
    @PutMapping("/changePassword/{customerId}")
    public ResponseEntity<?> changePassword (@PathVariable Long customerId, @Valid @RequestBody ChangePasswordCustomerDTO customerDTO ) throws Exception {
        try {
            //change user password
            customerService.changePassword(customerId, customerDTO);

            //return result in response
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .result(customerDTO)
                            .message("Change password successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    // Check if it's a mobile device
    // in charge: Trieu
    private boolean isMobileDevice(String customerAgent) {
        // Kiểm tra Customer-Agent header để xác định thiết bị di động
        // Ví dụ đơn giản:
        return customerAgent != null && customerAgent.toLowerCase().contains("mobile");
    }
}
