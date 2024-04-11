package com.nnk.springboot.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;

@MappedSuperclass
public abstract class FinancialProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected  Integer id;

    @NotBlank(message = "Account is mandatory")
    protected String account;

    @NotBlank(message = "Type is mandatory")
    protected String type;

    protected String security;
    protected String status;
    protected String trader;
    protected String benchmark;
    protected String book;
    protected String creationName;
    protected Timestamp creationDate;
    protected String revisionName;
    protected Timestamp revisionDate;
    protected String dealName;
    protected String dealType;
    protected String sourceListId;
    protected String side;
}
