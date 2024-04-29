package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Test
    public void testGetRuleName_ExistingRuleName() {
        // Arrange
        int id = 1;
        RuleName expectedRuleName = new RuleName();
        expectedRuleName.setId(id);
        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(expectedRuleName));

        // Act
        RuleName actualRuleName = ruleNameService.getRuleName(id);

        // Assert
        assertEquals(expectedRuleName, actualRuleName);
        verify(ruleNameRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRuleName_NonExistingRuleName() {
        // Arrange
        int id = 1;
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuleNameNotFoundException.class, () -> ruleNameService.getRuleName(id));
        verify(ruleNameRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRuleNames() {
        // Arrange
        List<RuleName> expectedRuleNames = new ArrayList<>();
        when(ruleNameRepository.findAll()).thenReturn(expectedRuleNames);

        // Act
        Iterable<RuleName> actualRuleNames = ruleNameService.getRuleNames();

        // Assert
        assertEquals(expectedRuleNames, actualRuleNames);
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void testSaveRuleName() {
        // Arrange
        RuleName ruleName = new RuleName();

        // Act
        ruleNameService.save(ruleName);

        // Assert
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    public void testDeleteRuleName() {
        // Arrange
        RuleName ruleName = new RuleName();

        // Act
        ruleNameService.delete(ruleName);

        // Assert
        verify(ruleNameRepository, times(1)).delete(ruleName);
    }
}
