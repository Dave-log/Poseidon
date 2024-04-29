package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.impl.TradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(TradeController.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeServiceImpl tradeService;

    @Test
    public void testHome() throws Exception {
        Trade trade1 = new Trade();
        trade1.setId(1);
        trade1.setAccount("Account1");
        trade1.setType("Type1");
        trade1.setBuyQuantity(10.0);

        Trade trade2 = new Trade();
        trade2.setId(2);
        trade2.setAccount("Account2");
        trade2.setType("Type2");
        trade2.setBuyQuantity(20.0);

        List<Trade> tradeList = Arrays.asList(trade1, trade2);

        when(tradeService.getTrades()).thenReturn(tradeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeExists("trades"))
                .andExpect(MockMvcResultMatchers.view().name("trade/list"));
    }

    @Test
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("tradeDTO"))
                .andExpect(MockMvcResultMatchers.view().name("trade/add"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        int tradeId = 1;
        Trade trade = new Trade();
        trade.setId(tradeId);
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);

        when(tradeService.getTrade(tradeId)).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/{id}", tradeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("tradeDTO"))
                .andExpect(MockMvcResultMatchers.view().name("trade/update"));
    }

    @Test
    public void testDeleteTrade() throws Exception {
        int tradeId = 1;
        Trade trade = new Trade();
        trade.setId(tradeId);

        when(tradeService.getTrade(tradeId)).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/{id}", tradeId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));

        verify(tradeService).delete(trade);
    }
}
