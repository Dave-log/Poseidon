package com.nnk.springboot.oldControllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rating")
@Tag(name = "Rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) { this.ratingService = ratingService; }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.getRatings());
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Model model) {
        Rating rating = new Rating();
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.getRatings());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRating(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.getRatings());
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRating(id);
        ratingService.delete(rating);
        model.addAttribute("ratings", ratingService.getRatings());
        return "redirect:/rating/list";
    }
}
