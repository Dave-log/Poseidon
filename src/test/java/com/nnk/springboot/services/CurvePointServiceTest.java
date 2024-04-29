package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Test
    public void testGetCurvePoint_ExistingCurvePoint() {
        // Arrange
        int id = 1;
        CurvePoint expectedCurvePoint = new CurvePoint();
        expectedCurvePoint.setId(id);
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(expectedCurvePoint));

        // Act
        CurvePoint actualCurvePoint = curvePointService.getCurvePoint(id);

        // Assert
        assertEquals(expectedCurvePoint, actualCurvePoint);
        verify(curvePointRepository, times(1)).findById(id);
    }

    @Test
    public void testGetCurvePoint_NonExistingCurvePoint() {
        // Arrange
        int id = 1;
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CurvePointNotFoundException.class, () -> curvePointService.getCurvePoint(id));
        verify(curvePointRepository, times(1)).findById(id);
    }

    @Test
    public void testGetCurvePoints() {
        // Arrange
        List<CurvePoint> expectedCurvePoints = new ArrayList<>();
        when(curvePointRepository.findAll()).thenReturn(expectedCurvePoints);

        // Act
        Iterable<CurvePoint> actualCurvePoints = curvePointService.getCurvePoints();

        // Assert
        assertEquals(expectedCurvePoints, actualCurvePoints);
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testSaveCurvePoint() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

        // Act
        curvePointService.save(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void testDeleteCurvePoint() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

        // Act
        curvePointService.delete(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).delete(curvePoint);
    }
}
