package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) { this.ruleNameService = ruleNameService; }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @RequestMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.getRuleNames());
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(Model model) {
        RuleName ruleName = new RuleName();
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.save(ruleName);
            model.addAttribute("ruleNames", ruleNameService.getRuleNames());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleName(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getRuleNames());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleName(id);
        ruleNameService.delete(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getRuleNames());
        return "redirect:/ruleName/list";
    }
}
