package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.notFound.RuleNameNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.impl.RuleNameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTests {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Test
    public void testGetRuleName_ExistingRuleName() {
        // Arrange
        RuleName ruleName = new RuleName();
        Integer id = 1;
        ruleName.setId(id);
        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        // Act
        RuleName existingRuleName = ruleNameService.getRuleName(id);

        // Assert
        assertEquals(id, existingRuleName.getId());
        verify(ruleNameRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRuleName_NonExistingRuleName() {
        // Arrange
        Integer id = 1;
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuleNameNotFoundException.class, () -> ruleNameService.getRuleName(id));
        verify(ruleNameRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRuleNames_ReturnsRuleNames() {
        // Arrange
        List<RuleName> ruleNameList = new ArrayList<>();
        ruleNameList.add(RuleName.builder().id(1).name("Name1").build());
        ruleNameList.add(RuleName.builder().id(2).name("Name2").build());
        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

        // Act
        Iterable<RuleName> result = ruleNameService.getRuleNames();

        // Assert
        assertNotNull(result);
        List<RuleName> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("Name1", resultList.getFirst().getName());
        assertEquals("Name2", resultList.getLast().getName());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void testGetRuleNames_ReturnsEmptyList() {
        // Arrange
        when(ruleNameRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Iterable<RuleName> result = ruleNameService.getRuleNames();

        // Assert
        assertNotNull(result);
        List<RuleName> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.isEmpty());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        RuleName ruleName = new RuleName();

        // Act
        ruleNameService.save(ruleName);

        // Assert
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    public void testDelete() {
        // Arrange
        RuleName ruleName = new RuleName();

        // Act
        ruleNameService.delete(ruleName);

        // Assert
        verify(ruleNameRepository, times(1)).delete(ruleName);
    }
}
