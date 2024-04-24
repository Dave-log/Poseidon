package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.notFound.BidListNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTests {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    @Test
    public void testGetBidList_ExistingBidList() {
        // Arrange
        BidList bidList = new BidList();
        Integer id = 1;
        bidList.setId(id);
        when(bidListRepository.findById(id)).thenReturn(Optional.of(bidList));

        // Act
        BidList existingBidList = bidListService.getBidList(id);

        // Assert
        assertEquals(id, existingBidList.getId());
        verify(bidListRepository, times(1)).findById(id);
    }

    @Test
    public void testGetBidList_NonExistingBidList() {
        // Arrange
        Integer id = 1;
        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BidListNotFoundException.class, () -> {
            bidListService.getBidList(id);
        });
        verify(bidListRepository, times(1)).findById(id);
    }

    @Test
    public void testGetBidLists_ReturnsBidLists() {
        // Arrange
        List<BidList> bidListList = new ArrayList<>();
        bidListList.add(BidList.builder().id(1).account("Account1").build());
        bidListList.add(BidList.builder().id(2).account("Account2").build());
        when(bidListRepository.findAll()).thenReturn(bidListList);

        // Act
        Iterable<BidList> result = bidListService.getBidLists();

        // Assert
        assertNotNull(result);
        List<BidList> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("Account1", resultList.getFirst().getAccount());
        assertEquals("Account2", resultList.getLast().getAccount());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void testGetBidLists_ReturnsEmptyList() {
        // Arrange
        when(bidListRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Iterable<BidList> result = bidListService.getBidLists();

        // Assert
        assertNotNull(result);
        List<BidList> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.isEmpty());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        BidList bidList = new BidList();

        // Act
        bidListService.save(bidList);

        // Assert
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void testDelete() {
        // Arrange
        BidList bidList = new BidList();

        // Act
        bidListService.delete(bidList);

        // Assert
        verify(bidListRepository, times(1)).delete(bidList);
    }
}
