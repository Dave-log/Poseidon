package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.notFound.BidListNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListServiceImpl(BidListRepository bidListRepository) { this.bidListRepository = bidListRepository; }

    @Override
    public BidList getBidList(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new BidListNotFoundException("BidList does not exist (id provided: " + id));
    }

    @Override
    public Iterable<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    @Override
    public void save(BidList bidList) {
        bidListRepository.save(bidList);
    }

    @Override
    public void delete(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
