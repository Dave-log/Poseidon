package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

import static org.mockito.Mockito.*;

@WebMvcTest(RuleNameController.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class RuleNameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    public void testHome() throws Exception {
        // Given
        List<RuleName> ruleNameList = new ArrayList<>();
        ruleNameList.add(new RuleName(1, "TestRule1", "Description1", "Json1", "Template1", "SqlStr1", "SqlPart1"));
        ruleNameList.add(new RuleName(2, "TestRule2", "Description2", "Json2", "Template2", "SqlStr2", "SqlPart2"));

        when(ruleNameService.getRuleNames()).thenReturn(ruleNameList);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("ruleNames"))
                .andExpect(MockMvcResultMatchers.view().name("ruleName/list"));
    }

    @Test
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("ruleNameDTO"))
                .andExpect(MockMvcResultMatchers.view().name("ruleName/add"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Rule Name");
        ruleName.setDescription("Test Description");
        ruleName.setJson("Test Json");
        ruleName.setTemplate("Test Template");
        ruleName.setSqlStr("Test SqlStr");
        ruleName.setSqlPart("Test SqlPart");

        when(ruleNameService.getRuleName(1)).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("ruleNameDTO"))
                .andExpect(MockMvcResultMatchers.view().name("ruleName/update"));
    }

    @Test
    public void testDeleteRuleName() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Rule Name");
        ruleName.setDescription("Test Description");
        ruleName.setJson("Test Json");
        ruleName.setTemplate("Test Template");
        ruleName.setSqlStr("Test SqlStr");
        ruleName.setSqlPart("Test SqlPart");

        when(ruleNameService.getRuleName(1)).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).delete(ruleName);
    }
}
