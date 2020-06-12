package com.skyrock.telemedico.Models;

import java.util.List;

public class ProgramsResponseModelList extends PageModel {

    private List<ProgramsModel> programs;

    public List<ProgramsModel> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramsModel> programs) {
        this.programs = programs;
    }
}
