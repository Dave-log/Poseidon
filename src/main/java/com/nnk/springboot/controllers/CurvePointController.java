package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.services.CurvePointService;
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
@RequestMapping("/curvePoint")
@Tag(name = "Curve Point", description = "Manages curve points used in financial analysis")
public class CurvePointController {

    private final CurvePointService curvePointService;

    @Autowired
    public CurvePointController(CurvePointService curvePointService) { this.curvePointService = curvePointService; }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        Iterable<CurvePoint> curvePointList = curvePointService.getCurvePoints();
        List<CurvePointDTO> curvePoints = StreamSupport.stream(curvePointList.spliterator(), false)
                        .map(curvePoint -> new CurvePointDTO(
                                curvePoint.getId(),
                                curvePoint.getCurveId(),
                                curvePoint.getTerm(),
                                curvePoint.getValue()
                        ))
                        .toList();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addCurvePointForm(Model model) {
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        model.addAttribute("curvePointDTO", curvePointDTO);
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    @ResponseBody
    public RedirectView validate(@Valid CurvePointDTO curvePointDTO, BindingResult result) {
        if (!result.hasErrors()) {
            CurvePoint curvePoint = CurvePoint.builder()
                    .curveId(curvePointDTO.getCurveId())
                    .term(curvePointDTO.getTerm())
                    .value(curvePointDTO.getValue())
                    .build();

            curvePointService.save(curvePoint);
            return new RedirectView("/curvePoint/list");
        }
        return new RedirectView("curvePoint/add");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);
        CurvePointDTO curvePointDTO = new CurvePointDTO(
                curvePoint.getId(),
                curvePoint.getCurveId(),
                curvePoint.getTerm(),
                curvePoint.getValue()
        );

        model.addAttribute("curvePointDTO", curvePointDTO);
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public RedirectView updateBid(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("curvePoint/update");
        }
        CurvePoint curvePoint = CurvePoint.builder()
                .id(id)
                .curveId(curvePointDTO.getCurveId())
                .term(curvePointDTO.getTerm())
                .value(curvePointDTO.getValue())
                .build();

        curvePointService.save(curvePoint);
        return new RedirectView("/curvePoint/list");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public RedirectView deleteBid(@PathVariable("id") Integer id) {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);
        curvePointService.delete(curvePoint);
        return new RedirectView("/curvePoint/list");
    }
}
