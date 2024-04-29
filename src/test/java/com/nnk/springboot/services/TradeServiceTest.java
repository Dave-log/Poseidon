package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    public void testGetTrade_ExistingTrade() {
        // Arrange
        int id = 1;
        Trade expectedTrade = new Trade();
        expectedTrade.setId(id);
        when(tradeRepository.findById(id)).thenReturn(Optional.of(expectedTrade));

        // Act
        Trade actualTrade = tradeService.getTrade(id);

        // Assert
        assertEquals(expectedTrade, actualTrade);
        verify(tradeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTrade_NonExistingTrade() {
        // Arrange
        int id = 1;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TradeNotFoundException.class, () -> tradeService.getTrade(id));
        verify(tradeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTrades() {
        // Arrange
        List<Trade> expectedTrades = new ArrayList<>();
        when(tradeRepository.findAll()).thenReturn(expectedTrades);

        // Act
        Iterable<Trade> actualTrades = tradeService.getTrades();

        // Assert
        assertEquals(expectedTrades, actualTrades);
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testSaveTrade() {
        // Arrange
        Trade trade = new Trade();

        // Act
        tradeService.save(trade);

        // Assert
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void testDeleteTrade() {
        // Arrange
        Trade trade = new Trade();

        // Act
        tradeService.delete(trade);

        // Assert
        verify(tradeRepository, times(1)).delete(trade);
    }
}
