package com.skyrock.telemedico.Models;

import java.util.List;

public class DataElementResponseModelList extends PageModel {

   private List<DataElementsModel> dataElements;

    public List<DataElementsModel> getDataElements() {
        return dataElements;
    }

    public void setDataElements(List<DataElementsModel> dataElements) {
        this.dataElements = dataElements;
    }
}
