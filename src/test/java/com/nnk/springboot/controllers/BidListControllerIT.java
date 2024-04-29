package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.impl.BidListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(BidListController.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListServiceImpl bidListService;

    @Test
    public void testHome() throws Exception {
        List<BidList> bidLists = new ArrayList<>();

        BidList bidList1 = new BidList();
        bidList1.setId(1);
        bidList1.setAccount("Account1");
        bidList1.setType("Type1");
        bidList1.setBidQuantity(100.0);

        BidList bidList2 = new BidList();
        bidList2.setId(2);
        bidList2.setAccount("Account2");
        bidList2.setType("Type2");
        bidList2.setBidQuantity(200.0);

        bidLists.add(bidList1);
        bidLists.add(bidList2);

        when(bidListService.getBidLists()).thenReturn(bidLists);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("bidLists"))
                .andExpect(MockMvcResultMatchers.view().name("bidList/list"));
    }

    @Test
    public void testAddBidListForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("bidListDTO"))
                .andExpect(MockMvcResultMatchers.view().name("bidList/add"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        BidList mockBidList = new BidList();
        mockBidList.setId(1);
        mockBidList.setAccount("Account1");
        mockBidList.setType("Type1");
        mockBidList.setBidQuantity(10.0);

        when(bidListService.getBidList(anyInt())).thenReturn(mockBidList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("bidListDTO"))
                .andExpect(MockMvcResultMatchers.view().name("bidList/update"));
    }

    @Test
    public void testDeleteBid() throws Exception {
        int id = 1;
        BidList bidList = new BidList();
        bidList.setId(id);

        when(bidListService.getBidList(id)).thenReturn(bidList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));

        verify(bidListService).delete(bidList);
    }
}
