package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

public interface TradeService {

    Trade getTrade(Integer id);
    Iterable<Trade> getTrades();
    void save(Trade trade);
    void delete(Trade trade);
}
