package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.notFound.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.impl.CurvePointServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTests {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Test
    public void testGetCurvePoint_ExistingCurvePoint() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();
        Integer id = 1;
        curvePoint.setId(id);
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        // Act
        CurvePoint existingCurvePoint = curvePointService.getCurvePoint(id);

        // Assert
        assertEquals(id, existingCurvePoint.getId());
        verify(curvePointRepository, times(1)).findById(id);
    }

    @Test
    public void testGetCurvePoint_NonExistingCurvePoint() {
        // Arrange
        Integer id = 1;
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CurvePointNotFoundException.class, () -> {
            curvePointService.getCurvePoint(id);
        });
        verify(curvePointRepository, times(1)).findById(id);
    }

    @Test
    public void testGetCurvePoints_ReturnsCurvePoints() {
        // Arrange
        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(CurvePoint.builder().id(1).value(1d).build());
        curvePointList.add(CurvePoint.builder().id(2).value(2d).build());
        when(curvePointRepository.findAll()).thenReturn(curvePointList);

        // Act
        Iterable<CurvePoint> result = curvePointService.getCurvePoints();

        // Assert
        assertNotNull(result);
        List<CurvePoint> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals(1d, resultList.getFirst().getValue());
        assertEquals(2d, resultList.getLast().getValue());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testGetCurvePoints_ReturnsEmptyList() {
        // Arrange
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Iterable<CurvePoint> result = curvePointService.getCurvePoints();

        // Assert
        assertNotNull(result);
        List<CurvePoint> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.isEmpty());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

        // Act
        curvePointService.save(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void testDelete() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

        // Act
        curvePointService.delete(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).delete(curvePoint);
    }
}
