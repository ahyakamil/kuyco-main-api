package com.kuyco.main_api.controller;

import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.config.JwtUtil;
import com.kuyco.main_api.dto.RegisterDto;
import com.kuyco.main_api.dto.TopUpBalanceDto;
import com.kuyco.main_api.helper.AuthHelper;
import com.kuyco.main_api.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthHelper authHelper;

    @PostMapping("/topup")
    public ResponseEntity<ResponseWrapper<Void>> register(@RequestBody TopUpBalanceDto topUpBalanceDto, HttpServletRequest request) {
        customerService.topUpBalance(authHelper.getAccount(request).getCustomer().getId(), topUpBalanceDto);
        return new ResponseWrapper<Void>().buildResponseOk();
    }

}
