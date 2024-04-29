package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.impl.CurvePointServiceImpl;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CurvePointController.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointServiceImpl curvePointService;

    @Test
    public void testHome() throws Exception {
        List<CurvePoint> curvePointList = new ArrayList<>();
        CurvePoint curvePoint1 = new CurvePoint();
        curvePoint1.setId(1);
        curvePoint1.setCurveId(1);
        curvePoint1.setTerm(2.0);
        curvePoint1.setValue(3.0);

        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint1.setId(2);
        curvePoint1.setCurveId(1);
        curvePoint1.setTerm(3.0);
        curvePoint1.setValue(4.0);

        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);

        when(curvePointService.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoints"))
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"));
    }

    @Test
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePointDTO"))
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        CurvePoint mockCurvePoint = new CurvePoint();
        mockCurvePoint.setId(1);
        mockCurvePoint.setCurveId(1);
        mockCurvePoint.setTerm(5.0);
        mockCurvePoint.setValue(10.0);

        when(curvePointService.getCurvePoint(anyInt())).thenReturn(mockCurvePoint);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePointDTO"))
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"));
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        int id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(id);

        when(curvePointService.getCurvePoint(id)).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));

        verify(curvePointService).delete(curvePoint);
    }
}
