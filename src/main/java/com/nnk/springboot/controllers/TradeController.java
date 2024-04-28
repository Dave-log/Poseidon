package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.services.TradeService;
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
@RequestMapping("/trade")
@Tag(name = "Trade", description = "Allows CRUD financial trading operations")
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) { this.tradeService = tradeService; }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        Iterable<Trade> tradeList = tradeService.getTrades();
        List<TradeDTO> trades = StreamSupport.stream(tradeList.spliterator(), false)
                        .map(trade -> {
                            TradeDTO tradeDTO = new TradeDTO();
                            tradeDTO.setId(trade.getId());
                            tradeDTO.setAccount(trade.getAccount());
                            tradeDTO.setType(trade.getType());
                            tradeDTO.setBuyQuantity(trade.getBuyQuantity());
                            return tradeDTO;
                        })
                        .toList();

        model.addAttribute("trades", trades);
        return "trade/list";
    }

    @GetMapping("/add")
    public String addTradeForm(Model model) {
        TradeDTO tradeDTO = new TradeDTO();
        model.addAttribute("tradeDTO", tradeDTO);
        return "trade/add";
    }

    @PostMapping("/validate")
    @ResponseBody
    public RedirectView validate(@Valid TradeDTO tradeDTO, BindingResult result) {
        if (!result.hasErrors()) {
            Trade trade = Trade.builder()
                    .account(tradeDTO.getAccount())
                    .type(tradeDTO.getType())
                    .buyQuantity(tradeDTO.getBuyQuantity())
                    .build();

            tradeService.save(trade);
            return new RedirectView("/trade/list");
        }
        return new RedirectView("/trade/add");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        TradeDTO tradeDTO = new TradeDTO(
                trade.getId(),
                trade.getAccount(),
                trade.getType(),
                trade.getBuyQuantity()
        );

        model.addAttribute("tradeDTO", tradeDTO);
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public RedirectView updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/trade/update");
        }
        Trade trade = Trade.builder()
                .id(id)
                .account(tradeDTO.getAccount())
                .type(tradeDTO.getType())
                .buyQuantity(tradeDTO.getBuyQuantity())
                .build();

        tradeService.save(trade);
        return new RedirectView("/trade/list");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public RedirectView deleteTrade(@PathVariable("id") Integer id) {
        Trade trade = tradeService.getTrade(id);
        tradeService.delete(trade);
        return new RedirectView("/trade/list");
    }
}
