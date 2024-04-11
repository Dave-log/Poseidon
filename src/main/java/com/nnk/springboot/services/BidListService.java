package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

public interface BidListService {

    BidList getBidList(Integer id);
    Iterable<BidList> getBidLists();
    void save(BidList bidList);
    void delete(BidList bidList);
}
