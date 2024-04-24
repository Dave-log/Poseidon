package com.nnk.springboot.oldControllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/bidList")
@Tag(name = "Bid List")
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
        model.addAttribute("bidLists", bidListService.getBidLists());
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(Model model) {
        BidList bidList = new BidList();
        model.addAttribute("bidList", bidList);
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.save(bidList);
            model.addAttribute("bidLists", bidListService.getBidLists());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setId(id);
        bidListService.save(bidList);
        model.addAttribute("bidLists", bidListService.getBidLists());
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        bidListService.delete(bidList);
        model.addAttribute("bidLists", bidListService.getBidLists());
        return "redirect:/bidList/list";
    }
}
