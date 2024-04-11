package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.notFound.RatingNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) { this.ratingRepository = ratingRepository; }

    @Override
    public Rating getRating(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(STR."Rating does not exist (id provided: \{id}"));
    }

    @Override
    public Iterable<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void delete(Rating rating) {
        ratingRepository.delete(rating);
    }
}
