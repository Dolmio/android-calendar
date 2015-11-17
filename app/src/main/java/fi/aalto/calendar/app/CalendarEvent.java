package fi.aalto.calendar.app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Date;

public class CalendarEvent implements Serializable{

    private final String _id;
    private final String description;
    private final String location;
    private final LocalDate startTime;
    private final LocalDate endTime;

    @JsonCreator
    public CalendarEvent(@JsonProperty("_id") String _id,
                         @JsonProperty("description") String description,
                         @JsonProperty("location") String location,
                         @JsonProperty("startTime") Date startTime,
                         @JsonProperty("endTime") Date endTime) {

        this._id = _id;
        this.description = description;
        this.location = location;
        this.startTime = LocalDate.fromDateFields(startTime);
        this.endTime = LocalDate.fromDateFields(endTime);

    }

    public String getDescription() {return description;}
    public String getLocation() {return location;}
    public LocalDate getEndTime() {return endTime;}
    public LocalDate getStartTime() {return startTime;}

    @Override
    public String toString() {
        return description;
    }
}
