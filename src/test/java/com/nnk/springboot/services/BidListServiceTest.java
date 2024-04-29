package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.impl.BidListServiceImpl;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    @Test
    public void testGetBidList_ExistingBidList() {
        // Arrange
        int id = 1;
        BidList expectedBidList = new BidList();
        expectedBidList.setId(id);
        when(bidListRepository.findById(id)).thenReturn(Optional.of(expectedBidList));

        // Act
        BidList actualBidList = bidListService.getBidList(id);

        // Assert
        assertEquals(expectedBidList, actualBidList);
        verify(bidListRepository, times(1)).findById(id);
    }

    @Test
    public void testGetBidList_NonExistingBidList() {
        // Arrange
        int id = 1;
        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BidListNotFoundException.class, () -> bidListService.getBidList(id));

        verify(bidListRepository, times(1)).findById(id);
    }

    @Test
    public void testGetBidLists() {
        // Arrange
        List<BidList> expectedBidLists = new ArrayList<>();
        when(bidListRepository.findAll()).thenReturn(expectedBidLists);

        // Act
        Iterable<BidList> actualBidLists = bidListService.getBidLists();

        // Assert
        assertEquals(expectedBidLists, actualBidLists);
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void testSaveBidList() {
        // Arrange
        BidList bidList = new BidList();

        // Act
        bidListService.save(bidList);

        // Assert
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void testDeleteBidList() {
        // Arrange
        BidList bidList = new BidList();

        // Act
        bidListService.delete(bidList);

        // Assert
        verify(bidListRepository, times(1)).delete(bidList);
    }
}
