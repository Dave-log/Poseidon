package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository) { this.tradeRepository = tradeRepository; }

    @Override
    public Trade getTrade(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new TradeNotFoundException("Trade does not exist (id provided: " + id));
    }

    @Override
    public Iterable<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public void save(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public void delete(Trade trade) {
        tradeRepository.delete(trade);
    }
}
