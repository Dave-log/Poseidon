package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) { this.curvePointRepository = curvePointRepository; }

    @Override
    public CurvePoint getCurvePoint(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new CurvePointNotFoundException("CurvePoint does not exist (id provided: " + id));
    }

    @Override
    public Iterable<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    @Override
    public void save(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void delete(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }
}
