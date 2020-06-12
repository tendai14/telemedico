package com.skyrock.telemedico.Models;

import java.util.List;

public class ProgramStagesResponseModelList extends PageModel {

    private List<ProgramStagesModel> programStages;

    public List<ProgramStagesModel> getProgramStages() {
        return programStages;
    }

    public void setProgramStages(List<ProgramStagesModel> programStages) {
        this.programStages = programStages;
    }
}
