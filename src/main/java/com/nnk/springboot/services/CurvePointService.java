package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

public interface CurvePointService {

    CurvePoint getCurvePoint(Integer id);
    Iterable<CurvePoint> getCurvePoints();
    void save(CurvePoint curvePoint);
    void delete(CurvePoint curvePoint);
}
