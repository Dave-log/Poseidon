package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.services.BidListService;
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
@RequestMapping("/bidList")
@Tag(name = "Bid List", description = "Manages operations for bid lists")
public class BidListController {

    private final BidListService bidListService;

    @Autowired
    public BidListController(BidListService bidListService) { this.bidListService = bidListService; }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        Iterable<BidList> bidListIterable = bidListService.getBidLists();
        List<BidListDTO> bidLists = StreamSupport.stream(bidListIterable.spliterator(), false)
                        .map(bidList -> new BidListDTO(
                                bidList.getId(),
                                bidList.getAccount(),
                                bidList.getType(),
                                bidList.getBidQuantity()
                        ))
                        .toList();

        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidListForm(Model model) {
        BidListDTO bidListDTO = new BidListDTO();
        model.addAttribute("bidListDTO", bidListDTO);
        return "bidList/add";
    }

    @PostMapping("/validate")
    @ResponseBody
    public RedirectView validate(@Valid BidListDTO bidListDTO, BindingResult result) {
        if (!result.hasErrors()) {
            BidList bidList = BidList.builder()
                    .account(bidListDTO.getAccount())
                    .type(bidListDTO.getType())
                    .bidQuantity(bidListDTO.getBidQuantity())
                    .build();

            bidListService.save(bidList);
            return new RedirectView("/bidList/list");
        }
        return new RedirectView("bidList/add");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        BidListDTO bidListDTO = new BidListDTO(
                bidList.getId(),
                bidList.getAccount(),
                bidList.getType(),
                bidList.getBidQuantity()
        );

        model.addAttribute("bidListDTO", bidListDTO);
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public RedirectView updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("bidList/update");
        }
        BidList bidList = BidList.builder()
                .id(id)
                .account(bidListDTO.getAccount())
                .type(bidListDTO.getType())
                .bidQuantity(bidListDTO.getBidQuantity())
                .build();

        bidListService.save(bidList);
        return new RedirectView("/bidList/list");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public RedirectView deleteBid(@PathVariable("id") Integer id) {
        BidList bidList = bidListService.getBidList(id);
        bidListService.delete(bidList);
        return new RedirectView("/bidList/list");
    }
}
