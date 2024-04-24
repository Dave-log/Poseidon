package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.notFound.RatingNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.impl.RatingServiceImpl;
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
public class RatingServiceTests {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    public void testGetRating_ExistingRating() {
        // Arrange
        Rating rating = new Rating();
        Integer id = 1;
        rating.setId(id);
        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        // Act
        Rating existingRating = ratingService.getRating(id);

        // Assert
        assertEquals(id, existingRating.getId());
        verify(ratingRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRating_NonExistingRating() {
        // Arrange
        Integer id = 1;
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RatingNotFoundException.class, () -> {
            ratingService.getRating(id);
        });
        verify(ratingRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRatings_ReturnsRatings() {
        // Arrange
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(Rating.builder().id(1).orderNumber(1).build());
        ratingList.add(Rating.builder().id(2).orderNumber(2).build());
        when(ratingRepository.findAll()).thenReturn(ratingList);

        // Act
        Iterable<Rating> result = ratingService.getRatings();

        // Assert
        assertNotNull(result);
        List<Rating> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals(1, resultList.getFirst().getOrderNumber());
        assertEquals(2, resultList.getLast().getOrderNumber());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testGetRatings_ReturnsEmptyList() {
        // Arrange
        when(ratingRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Iterable<Rating> result = ratingService.getRatings();

        // Assert
        assertNotNull(result);
        List<Rating> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.isEmpty());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        Rating rating = new Rating();

        // Act
        ratingService.save(rating);

        // Assert
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void testDelete() {
        // Arrange
        Rating rating = new Rating();

        // Act
        ratingService.delete(rating);

        // Assert
        verify(ratingRepository, times(1)).delete(rating);
    }
}
