package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.notFound.RuleNameNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    public final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) { this.ruleNameRepository = ruleNameRepository; }

    @Override
    public RuleName getRuleName(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new RuleNameNotFoundException("RuleName does not exist (id provided: " + id));
    }

    @Override
    public Iterable<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }

    @Override
    public void save(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    @Override
    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
