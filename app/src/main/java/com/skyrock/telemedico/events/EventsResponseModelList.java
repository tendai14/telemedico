package com.skyrock.telemedico.events;

import com.skyrock.telemedico.Models.PageModel;

import java.util.List;

public class EventsResponseModelList extends PageModel {

   private List<EventsModel> events;

    public List<EventsModel> getEvents() {
        return events;
    }

    public void setEvents(List<EventsModel> events) {
        this.events = events;
    }
}
