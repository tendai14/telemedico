package com.skyrock.telemedico.Models;

import java.util.List;

public class OrganisationUnitResponseModelList extends PageModel {

    private List<OrganisationUnitsModel> organisationUnits;

    public List<OrganisationUnitsModel> getOrganisationUnits() {
        return organisationUnits;
    }

    public void setOrganisationUnits(List<OrganisationUnitsModel> organisationUnits) {
        this.organisationUnits = organisationUnits;
    }
}
