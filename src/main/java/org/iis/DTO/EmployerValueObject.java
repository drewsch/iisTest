package org.iis.DTO;

import org.apache.log4j.Logger;

import java.util.Objects;

public class EmployerValueObject {

    final static Logger logger = Logger.getLogger(EmployerValueObject.class.getName());

    public String ID_FIELD = "ID";
    public String DEP_CODE_FIELD = "DepCode";
    public String DEP_JOB_FIELD = "DepJob";
    public String DESCRIPTION_FIELD = "Description";

    private int id;
    private String depCode;
    private String depJob;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployerValueObject that = (EmployerValueObject) o;
        return Objects.equals(depCode, that.depCode) && Objects.equals(depJob, that.depJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }
}
