package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    public void testGetRating_ExistingRating() {
        // Arrange
        int id = 1;
        Rating expectedRating = new Rating();
        expectedRating.setId(id);
        when(ratingRepository.findById(id)).thenReturn(Optional.of(expectedRating));

        // Act
        Rating actualRating = ratingService.getRating(id);

        // Assert
        assertEquals(expectedRating, actualRating);
        verify(ratingRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRating_NonExistingRating() {
        // Arrange
        int id = 1;
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRating(id));
        verify(ratingRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRatings() {
        // Arrange
        List<Rating> expectedRatings = new ArrayList<>();
        when(ratingRepository.findAll()).thenReturn(expectedRatings);

        // Act
        Iterable<Rating> actualRatings = ratingService.getRatings();

        // Assert
        assertEquals(expectedRatings, actualRatings);
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testSaveRating() {
        // Arrange
        Rating rating = new Rating();

        // Act
        ratingService.save(rating);

        // Assert
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void testDeleteRating() {
        // Arrange
        Rating rating = new Rating();

        // Act
        ratingService.delete(rating);

        // Assert
        verify(ratingRepository, times(1)).delete(rating);
    }
}
