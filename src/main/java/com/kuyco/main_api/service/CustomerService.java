package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CustomerDto;
import com.kuyco.main_api.dto.TopUpBalanceDto;

public interface CustomerService {
    void topUpBalance(Long customerId, TopUpBalanceDto topUpBalanceDto);
}
