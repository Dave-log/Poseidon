package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.services.impl.RatingServiceImpl;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(RatingController.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingServiceImpl ratingService;

    @Test
    public void testHome() throws Exception {
        List<Rating> mockRatingList = new ArrayList<>();
        mockRatingList.add(new Rating(1, "AAA", "AA", "AAA", 1));
        mockRatingList.add(new Rating(2, "BBB", "BB", "BBB", 2));

        when(ratingService.getRatings()).thenReturn(mockRatingList);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeExists("ratings"))
                .andExpect(MockMvcResultMatchers.view().name("rating/list"));
    }

    @Test
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(MockMvcResultMatchers.view().name("rating/add"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        int ratingId = 1;
        Rating mockRating = new Rating();
        mockRating.setId(ratingId);
        mockRating.setMoodysRating("AAA");
        mockRating.setSandPRating("AA");
        mockRating.setFitchRating("AAA");
        mockRating.setOrderNumber(1);

        when(ratingService.getRating(ratingId)).thenReturn(mockRating);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/" + ratingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(MockMvcResultMatchers.view().name("rating/update"));
    }

    @Test
    public void testDeleteRating() throws Exception {
        Rating mockRating = new Rating(1, "AAA", "AA", "AAA", 1);

        when(ratingService.getRating(1)).thenReturn(mockRating);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).delete(mockRating);
    }
}
