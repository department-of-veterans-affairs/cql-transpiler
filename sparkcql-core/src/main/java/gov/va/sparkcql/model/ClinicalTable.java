package gov.va.sparkcql.model;

import java.time.LocalDateTime;

public abstract class ClinicalTable<T> {

    private String patientCorrelationId;

    private String practictionerCorrelationId;

    private String primaryCode;

    private LocalDateTime primaryStartDate;

    private LocalDateTime primaryEndDate;

    private T entity;

    public String getPatientCorrelationId() {
        return patientCorrelationId;
    }

    public void setPatientCorrelationId(String patientCorrelationId) {
        this.patientCorrelationId = patientCorrelationId;
    }

    public String getPractictionerCorrelationId() {
        return practictionerCorrelationId;
    }

    public void setPractictionerCorrelationId(String practictionerCorrelationId) {
        this.practictionerCorrelationId = practictionerCorrelationId;
    }

    public String getPrimaryCode() {
        return primaryCode;
    }

    public void setPrimaryCode(String primaryCode) {
        this.primaryCode = primaryCode;
    }

    public LocalDateTime getPrimaryStartDate() {
        return primaryStartDate;
    }

    public void setPrimaryStartDate(LocalDateTime primaryStartDate) {
        this.primaryStartDate = primaryStartDate;
    }

    public LocalDateTime getPrimaryEndDate() {
        return primaryEndDate;
    }

    public void setPrimaryEndDate(LocalDateTime primaryEndDate) {
        this.primaryEndDate = primaryEndDate;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}