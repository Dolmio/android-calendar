package fi.aalto.calendar.app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarEvent {

    private final String description;

    @JsonCreator
    public CalendarEvent(@JsonProperty("description") String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
