package ru.mipt.apps.cleankpm.activities.tabs.event;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ru.mipt.apps.cleankpm.R;
import ru.mipt.apps.cleankpm.activities.tabs.OnDataPass;
import ru.mipt.apps.cleankpm.tabObjects.Event;

/**
 * Created by User on 2/7/2015.
 */
    public class AddNewEventActivity extends FragmentActivity implements View.OnClickListener, OnDataPass {

    public static final int SECRET = Event.SECRET; //no one sees your intention
    public static final int SUBTLE = Event.SUBTLE; // shown on your profile
    public static final int IMPOSING = Event.IMPOSING; // everyone sees in his feed
    protected static final int START = 0;
    protected static final int END = 1;
    protected EditText editText;
    protected Button btnFinish;
    protected RadioButton rbtnImpose;
    protected RadioButton rbtnSecret;
    protected RadioButton rbtnMyPage;
    protected RadioGroup rgroupSubtle;
    protected Button startTimeBtn;
    protected Button endTimeBtn;
    protected Button calendarBtn;
    protected Event event;
    protected EditText numFriends;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_event);
        initializeFields();
        setFieldListeners();
    }

    private void initializeFields(){
        editText = (EditText)findViewById(R.id.eventNameEntry);
        editText.setText("");
        btnFinish = (Button) findViewById(R.id.finishAddingEventButton);
        btnFinish.setEnabled(false);
        rbtnImpose = (RadioButton) findViewById(R.id.imposeRBtn);
        rbtnSecret = (RadioButton) findViewById(R.id.secretRBtn);
        rbtnMyPage =(RadioButton) findViewById(R.id.myPageRBtn);
        rgroupSubtle = (RadioGroup) findViewById(R.id.radio);
        startTimeBtn = (Button) findViewById(R.id.chooseStartTimeEventButton);
        endTimeBtn = (Button) findViewById(R.id.chooseEndTimeEventButton);
        calendarBtn = (Button) findViewById(R.id.chooseDateEventButton);
        event = new Event();
        numFriends = (EditText) findViewById(R.id.editNumberOfFriendsText);
    }
    private void setFieldListeners(){
        btnFinish.setOnClickListener(this);
        startTimeBtn.setOnClickListener(this);
        endTimeBtn.setOnClickListener(this);
        calendarBtn.setOnClickListener(this);
        rbtnImpose.setId(IMPOSING);
        rbtnSecret.setId(SECRET);
        rbtnMyPage.setId(SUBTLE);
        numFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                testReadiness();
            }

            @Override
            public void afterTextChanged(Editable s) {
                testReadiness();
            }
        });
    }
    private void returnEvent(){
        Intent intent = new Intent();
        intent.putExtra("event created", event);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){

                case R.id.finishAddingEventButton: {
                    //saveAll();
                    if (editText.getText().toString().equals("")){
                        Toast.makeText(this, "set event name first", Toast.LENGTH_LONG).show();
                    }else{
                        event.setEventName(editText.getText().toString());
                        int rb_id = rgroupSubtle.getCheckedRadioButtonId();
                        switch(rb_id){

                            case IMPOSING : {
                                event.setSubtle(Event.IMPOSING);
                                returnEvent();
                                break;
                            }
                            case SECRET : {
                                event.setSubtle(Event.SECRET);
                                returnEvent();
                                break;
                            }
                            case SUBTLE : {
                                event.setSubtle(Event.SUBTLE);
                                returnEvent();
                                break;
                            }
                            case -1: {
                                Toast.makeText(this, "choose subtlicity level first", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }

                    break;
                }

                case R.id.chooseStartTimeEventButton: {
                    getTime(START);

                    break;
                }

                case R.id.chooseEndTimeEventButton: {
                    getTime(END);

                    break;
                }

                case R.id.chooseDateEventButton: {
                    getDate();

                    break;
                }
        }
    }
    protected void testReadiness(){
        String str = numFriends.getText().toString();
        if (str.matches("")){
            btnFinish.setEnabled(false);
        }else{
            event.setMinFriends(Integer.parseInt(str));
            if (!(event.isYetUnset())){
                btnFinish.setEnabled(true);
            }
        }


    }
    protected void getTime(int startOrEnd){
        Bundle timeBundle = new Bundle();
        timeBundle.putInt("time",startOrEnd);
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.setArguments(timeBundle);
        timePicker.show(getFragmentManager(), "timePicker");

    }
    protected void getDate(){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "timePicker");
    }
    @Override
    public void onTimePass(ArrayList<Integer> al) {
        int a = al.get(0);
        switch(a){
            case START:{
                event.setHoursEnd(al.get(1));
                event.setMinutesEnd(al.get(2));
                break;
            }
            case END:{
                event.setHoursStart(al.get(1));
                event.setMinutesStart(al.get(2));
                break;
            }
        }
        testReadiness();
    }

    @Override
    public void onDatePass(ArrayList<Integer> al) {
        event.setYear(al.get(0));
        event.setMonth(al.get(1));
        event.setDay(al.get(2));
        testReadiness();
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        int start_or_end;
        OnDataPass dataPasser;

        @Override
        public void onAttach(Activity a) {
            super.onAttach(a);
            dataPasser = (OnDataPass) a;
        }
        public void passData(ArrayList<Integer> al) {
            dataPasser.onTimePass(al);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            start_or_end = getArguments().getInt("time");
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(start_or_end);
            a.add(hourOfDay);
            a.add(minute);
            passData(a);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        OnDataPass dataPasser;

        @Override
        public void onAttach(Activity a) {
            super.onAttach(a);
            dataPasser = (OnDataPass) a;
        }
        public void passData(ArrayList<Integer> al) {
            dataPasser.onDatePass(al);
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(year);
            a.add(month);
            a.add(day);
            passData(a);
        }
    }
}
