package com.nnk.springboot.oldControllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trade")
@Tag(name = "Trade")
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
        model.addAttribute("trades", tradeService.getTrades());
        return "trade/list";
    }

    @GetMapping("/add")
    public String addTradeForm(Model model) {
        Trade trade = new Trade();
        model.addAttribute("trade", trade);
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.getTrades());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        trade.setId(id);
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.getTrades());
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        tradeService.delete(trade);
        model.addAttribute("trades", tradeService.getTrades());
        return "redirect:/trade/list";
    }
}
