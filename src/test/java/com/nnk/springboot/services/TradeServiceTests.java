package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.notFound.TradeNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.impl.TradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTests {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    public void testGetTrade_ExistingTrade() {
        // Arrange
        Trade trade = new Trade();
        Integer id = 1;
        trade.setId(id);
        when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        // Act
        Trade existingTrade = tradeService.getTrade(id);

        // Assert
        assertEquals(id, existingTrade.getId());
        verify(tradeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTrade_NonExistingTrade() {
        // Arrange
        Integer id = 1;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TradeNotFoundException.class, () -> tradeService.getTrade(id));
        verify(tradeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTrades_ReturnsTrades() {
        // Arrange
        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(Trade.builder().id(1).account("Account1").build());
        tradeList.add(Trade.builder().id(2).account("Account2").build());
        when(tradeRepository.findAll()).thenReturn(tradeList);

        // Act
        Iterable<Trade> result = tradeService.getTrades();

        // Assert
        assertNotNull(result);
        List<Trade> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("Account1", resultList.getFirst().getAccount());
        assertEquals("Account2", resultList.getLast().getAccount());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testGetTrades_ReturnsEmptyList() {
        // Arrange
        when(tradeRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Iterable<Trade> result = tradeService.getTrades();

        // Assert
        assertNotNull(result);
        List<Trade> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.isEmpty());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        Trade trade = new Trade();

        // Act
        tradeService.save(trade);

        // Assert
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void testDelete() {
        // Arrange
        Trade trade = new Trade();

        // Act
        tradeService.delete(trade);

        // Assert
        verify(tradeRepository, times(1)).delete(trade);
    }
}
