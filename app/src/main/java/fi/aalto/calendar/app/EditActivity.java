package fi.aalto.calendar.app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;

    /*
    To call this activity :

    Add an Event :
    startActivity(intent);

    Edit an Event :
    Date format : "YYYY-MM-DD HH:MM"

    intent.putExtra(IDCalendarEvent, "rth1sd56df4df");
    intent.putExtra(NameCalendarEvent, "A name");
    intent.putExtra(LocationCalendarEvent, "A location");
    intent.putExtra(StartDateCalendarEvent, "YYYY-MM-DD HH:MM");
    intent.putExtra(EndDateCalendarEvent, "YYYY-MM-DD HH:MM");
    startActivity(intent);

     */

public class EditActivity extends Activity {

    EditText name = null;
    EditText location = null;
    EditText startDate = null;
    EditText startHour = null;
    EditText endDate = null;
    EditText endHour = null;

    String IDCalendarEvent = "";
    String NameCalendarEvent = "";
    String LocationCalendarEvent = "";
    String StartDateCalendarEvent = "";
    String EndDateCalendarEvent = "";

    private View.OnClickListener clickListenerButtonManage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // String and format to create a Date object
            String startEvent = startDate.getText().toString().concat(" ").concat(startHour.getText().toString());
            String endEvent = endDate.getText().toString().concat(" ").concat(endHour.getText().toString());
            SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");

            try {
                //Date objects
                Date startEventDate = format.parse(startEvent);
                Date endEventDate = format.parse(endEvent);

                //Information to a CalendarEvent object
                CalendarEvent editableEvent = new CalendarEvent(name.getText().toString(), location.getText().toString(), startEventDate, endEventDate);

                //Now in JSON
                ObjectMapper mapper = new ObjectMapper();
                String editableEventInJSON = mapper.writeValueAsString(editableEvent);

                //The request
                    //TODO
                RequestBody formBody = new FormEncodingBuilder()
                        .add("bla", "value")
                        .build();

                //Call addEvent or editEvent (ApiClient)
                    //TODO

                //Leave the activity with a toast which contains the result
                    //TODO

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = (EditText) findViewById(R.id.editTextName);
        location = (EditText) findViewById(R.id.editTextLocation);
        startDate = (EditText) findViewById(R.id.editTextStartDate);
        startHour = (EditText) findViewById(R.id.editTextStartHour);
        endDate = (EditText) findViewById(R.id.editTextEndDate);
        endHour = (EditText) findViewById(R.id.editTextEndTime);

        //If you call this activity to add an event or to update an event
        Intent intent = getIntent();
        IDCalendarEvent = intent.getStringExtra("IDCalendarEvent");
        if (IDCalendarEvent != null && !IDCalendarEvent.isEmpty()) {
            NameCalendarEvent = intent.getStringExtra("NameCalendarEvent");
            LocationCalendarEvent = intent.getStringExtra("LocationCalendarEvent");
            StartDateCalendarEvent = intent.getStringExtra("StartDateCalendarEvent");
            EndDateCalendarEvent = intent.getStringExtra("EndDateCalendarEvent");

            //Fill the view with the information already known
            name.setText(NameCalendarEvent);
            location.setText(LocationCalendarEvent);

            //Split the date in 2 parts for the view
            String[] startDateparts = StartDateCalendarEvent.split(" ");
            String[] endDateparts = EndDateCalendarEvent.split(" ");

            startDate.setText(startDateparts[0]);
            startHour.setText(startDateparts[1]);
            endDate.setText(endDateparts[0]);
            endHour.setText(endDateparts[1]);
        }

        //Set a listener to the button
        Button save = (Button) findViewById(R.id.buttonManage);
        save.setOnClickListener(clickListenerButtonManage);

    }
}


