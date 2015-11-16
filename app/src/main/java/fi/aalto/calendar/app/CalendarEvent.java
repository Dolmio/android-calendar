package fi.aalto.calendar.app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Date;

public class CalendarEvent implements Serializable{

    private final String description;
    private final LocalDate startTime;
    private final LocalDate endTime;

    @JsonCreator
    public CalendarEvent(@JsonProperty("description") String description,
                         @JsonProperty("startTime") Date startTime,
                         @JsonProperty("endTime") Date endTime) {

        this.description = description;
        this.startTime = LocalDate.fromDateFields(startTime);
        this.endTime = LocalDate.fromDateFields(endTime);

    }

    public String getDescription() {
        return description;
    }
    public LocalDate getEndTime() {return endTime;}
    public LocalDate getStartTime() {return startTime;}

    @Override
    public String toString() {
        return description;
    }
}
