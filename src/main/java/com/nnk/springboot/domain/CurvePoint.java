package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "curve_id")
    private Integer curveId;
    @Column(name = "as_of_date")
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    @Column(name = "creation_date")
    private Timestamp creationDate;
}
