package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TradeTests {

	private final TradeRepository tradeRepository;

    public TradeTests(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Test
	public void tradeTest() {
		Trade trade = Trade.builder()
				.account("Trade Account")
				.type("Type")
				.build();

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getId());
        assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
        assertEquals("Trade Account Update", trade.getAccount());

		// Find
		List<Trade> listResult = tradeRepository.findAll();
        assertFalse(listResult.isEmpty());

		// Delete
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}
