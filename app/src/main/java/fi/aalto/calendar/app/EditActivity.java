package fi.aalto.calendar.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

    /*
    To call this activity :

    Add an Event :
    startActivity(intent);

    Edit an Event :
    intent.putExtra(EditActivity.EVENT_PARAMETER, event);
    startActivity(intent);

     */

public class EditActivity extends FragmentActivity {

    public static String EVENT_PARAMETER = "event";

    EditText name;
    EditText location;
    EditText startDate;
    EditText startHour;
    EditText endDate;
    EditText endHour;

    CalendarEvent event;

    private DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
    private DateTimeFormatter hourFormat = DateTimeFormat.forPattern("HH:mm");

    private static String TWO_NUMBER_FORMAT = "%02d";
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

                Observable<CalendarEvent> saveObservable;
                CalendarApplication app = (CalendarApplication) getApplication();
                if(event != null) {

                    CalendarEvent editedEvent = new CalendarEvent(event.getId(), name.getText().toString(), location.getText().toString(), startEventDate, endEventDate);
                    saveObservable =  ApiClient.editEvent(editedEvent, app.httpClient, app.objectMapper);
                }
                else {
                    CalendarEvent newEvent = new CalendarEvent("", name.getText().toString(), location.getText().toString(), startEventDate, endEventDate );
                    saveObservable =  ApiClient.addEvent(newEvent, app.httpClient, app.objectMapper);
                }

                saveObservable.
                        subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    Toast.makeText(getApplicationContext(), "Saved event succesfully!", Toast.LENGTH_LONG).show();
                                    finish();
                                },
                                throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show());

            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Event is not valid. Please fill all the fields.", Toast.LENGTH_LONG).show();

                e.printStackTrace();
            } catch (Exception e) {
                System.err.println(e.getMessage());
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
        event = (CalendarEvent) intent.getSerializableExtra(EVENT_PARAMETER);

        if(event != null) {
            name.setText(event.getDescription());
            location.setText(event.getLocation());
            startDate.setText(dateFormat.print(event.getStartTime()));
            startHour.setText(hourFormat.print(event.getStartTime()));
            endDate.setText(dateFormat.print(event.getEndTime()));
            endHour.setText(hourFormat.print(event.getEndTime()));
        }
        //Set a listener to the button
        Button save = (Button) findViewById(R.id.buttonManage);
        save.setOnClickListener(clickListenerButtonManage);



    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = TimePickerFragment.newInstance((view, hourOfDay, minute) ->
                ((TextView)v).setText(String.format(TWO_NUMBER_FORMAT + ":" + TWO_NUMBER_FORMAT, hourOfDay, minute))
        );
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = DatePickerFragment.newInstance((view, year, month, dayOfMonth) ->

                ((TextView)v).setText( String.format(year+"-"+TWO_NUMBER_FORMAT + "-" + TWO_NUMBER_FORMAT, month + 1, dayOfMonth))
        );
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
}



