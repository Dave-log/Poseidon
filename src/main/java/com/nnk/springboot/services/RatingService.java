package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

public interface RatingService {

    Rating getRating(Integer id);
    Iterable<Rating> getRatings();
    void save(Rating rating);
    void delete(Rating rating);
}
