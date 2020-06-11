package com.skyrock.telemedico.events;

import java.util.List;

public class EventsModel {

    private String orgUnit;
    private String program;
    private String programStage;
    private String trackedEntityInstance;
    private String orgUnitName;
    private String created;
    private String storedBy;
    private List<DataValuesModel> dataValues;

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgramStage() {
        return programStage;
    }

    public void setProgramStage(String programStage) {
        this.programStage = programStage;
    }

    public String getTrackedEntityInstance() {
        return trackedEntityInstance;
    }

    public void setTrackedEntityInstance(String trackedEntityInstance) {
        this.trackedEntityInstance = trackedEntityInstance;
    }

    public String getOrgUnitName() {
        return orgUnitName;
    }

    public void setOrgUnitName(String orgUnitName) {
        this.orgUnitName = orgUnitName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStoredBy() {
        return storedBy;
    }

    public void setStoredBy(String storedBy) {
        this.storedBy = storedBy;
    }

    public List<DataValuesModel> getDataValues() {
        return dataValues;
    }

    public void setDataValues(List<DataValuesModel> dataValues) {
        this.dataValues = dataValues;
    }
}
