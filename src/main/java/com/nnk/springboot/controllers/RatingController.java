package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.services.RatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/rating")
@Tag(name = "Rating", description = "Manages ratings provided by financial rating agencies")
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
        Iterable<Rating> ratingList = ratingService.getRatings();
        List<RatingDTO> ratings = StreamSupport.stream(ratingList.spliterator(), false)
                        .map(rating -> new RatingDTO(
                                rating.getId(),
                                rating.getMoodysRating(),
                                rating.getSandPRating(),
                                rating.getFitchRating(),
                                rating.getOrderNumber()
                        ))
                        .toList();

        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Model model) {
        RatingDTO ratingDTO = new RatingDTO();
        model.addAttribute("ratingDTO", ratingDTO);
        return "rating/add";
    }

    @PostMapping("/validate")
    @ResponseBody
    public RedirectView validate(@Valid RatingDTO ratingDTO, BindingResult result) {
        if (!result.hasErrors()) {
            Rating rating = Rating.builder()
                    .moodysRating(ratingDTO.getMoodysRating())
                    .sandPRating(ratingDTO.getSandPRating())
                    .fitchRating(ratingDTO.getFitchRating())
                    .orderNumber(ratingDTO.getOrderNumber())
                    .build();

            ratingService.save(rating);
            return new RedirectView("/rating/list");
        }
        return new RedirectView("/rating/add");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRating(id);
        RatingDTO ratingDTO = new RatingDTO(
                rating.getId(),
                rating.getMoodysRating(),
                rating.getSandPRating(),
                rating.getFitchRating(),
                rating.getOrderNumber()
        );

        model.addAttribute("ratingDTO", ratingDTO);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public RedirectView updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("rating/update");
        }
        Rating rating = Rating.builder()
                .id(id)
                .moodysRating(ratingDTO.getMoodysRating())
                .sandPRating(ratingDTO.getSandPRating())
                .fitchRating(ratingDTO.getFitchRating())
                .orderNumber(ratingDTO.getOrderNumber())
                .build();

        ratingService.save(rating);
        return new RedirectView("/rating/list");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public RedirectView deleteRating(@PathVariable("id") Integer id) {
        Rating rating = ratingService.getRating(id);
        ratingService.delete(rating);
        return new RedirectView("/rating/list");
    }
}
