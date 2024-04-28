package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.services.RuleNameService;
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
@RequestMapping("/ruleName")
@Tag(name = "Rule Name", description = "Manages rules and criteria used to evaluate financial data")
public class RuleNameController {

    private final RuleNameService ruleNameService;

    @Autowired
    public RuleNameController(RuleNameService ruleNameService) { this.ruleNameService = ruleNameService; }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        Iterable<RuleName> ruleNameList = ruleNameService.getRuleNames();
        List<RuleNameDTO> ruleNames = StreamSupport.stream(ruleNameList.spliterator(), false)
                        .map(ruleName -> {
                            RuleNameDTO ruleNameDTO = new RuleNameDTO();
                            ruleNameDTO.setId(ruleName.getId());
                            ruleNameDTO.setName(ruleName.getName());
                            ruleNameDTO.setDescription(ruleName.getDescription());
                            ruleNameDTO.setJson(ruleName.getJson());
                            ruleNameDTO.setTemplate(ruleName.getTemplate());
                            ruleNameDTO.setSqlPart(ruleName.getSqlStr());
                            ruleNameDTO.setSqlStr(ruleName.getSqlPart());
                            return ruleNameDTO;
                        })
                        .toList();

        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(Model model) {
        RuleNameDTO ruleNameDTO = new RuleNameDTO();
        model.addAttribute("ruleNameDTO", ruleNameDTO);
        return "ruleName/add";
    }

    @PostMapping("/validate")
    @ResponseBody
    public RedirectView validate(@Valid RuleNameDTO ruleNameDTO, BindingResult result) {
        if (!result.hasErrors()) {
            RuleName ruleName = RuleName.builder()
                    .name(ruleNameDTO.getName())
                    .description(ruleNameDTO.getDescription())
                    .json(ruleNameDTO.getJson())
                    .template(ruleNameDTO.getTemplate())
                    .sqlPart(ruleNameDTO.getSqlPart())
                    .sqlStr(ruleNameDTO.getSqlStr())
                    .build();

            ruleNameService.save(ruleName);
            return new RedirectView("/ruleName/list");
        }
        return new RedirectView("/ruleName/add");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleName(id);
        RuleNameDTO ruleNameDTO = new RuleNameDTO(
                ruleName.getId(),
                ruleName.getName(),
                ruleName.getDescription(),
                ruleName.getJson(),
                ruleName.getTemplate(),
                ruleName.getSqlStr(),
                ruleName.getSqlPart()
        );

        model.addAttribute("ruleNameDTO", ruleNameDTO);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public RedirectView updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDTO ruleNameDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/ruleName/update");
        }

        RuleName ruleName = RuleName.builder()
                .id(id)
                .name(ruleNameDTO.getName())
                .description(ruleNameDTO.getDescription())
                .json(ruleNameDTO.getJson())
                .template(ruleNameDTO.getTemplate())
                .sqlPart(ruleNameDTO.getSqlPart())
                .sqlStr(ruleNameDTO.getSqlStr())
                .build();

        ruleNameService.save(ruleName);
        return new RedirectView("/ruleName/list");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public RedirectView deleteRuleName(@PathVariable("id") Integer id) {
        RuleName ruleName = ruleNameService.getRuleName(id);
        ruleNameService.delete(ruleName);
        return new RedirectView("/ruleName/list");
    }
}
