package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

public interface RuleNameService {

    RuleName getRuleName(Integer id);
    Iterable<RuleName> getRuleNames();
    void save(RuleName ruleName);
    void delete(RuleName ruleName);
}
