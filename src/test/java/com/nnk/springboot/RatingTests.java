package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nnk.springboot.repositories.RatingRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RatingTests {

	private final RatingRepository ratingRepository;

    public RatingTests(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Test
	public void ratingTest() {
		Rating rating = Rating.builder()
				.moodysRating("Moodys Rating")
				.sandPRating("SandP Rating")
				.fitchRating("Fitch Rating")
				.orderNumber(10)
				.build();

		// Save
		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
        assertEquals(10, (int) rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
        assertEquals(20, (int) rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
        assertFalse(listResult.isEmpty());

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertFalse(ratingList.isPresent());
	}
}
