package com.example.lfo.p1;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DatePickerFragment extends Fragment {
    private ScrollView scrollView;
    private TextView tvFromDate;
    private TextView tvToDate;
    private DatePicker datePickerFrom;
    private DatePicker datePickerTo;
    private Button btnSetDateInterval;
    private Controller controller;
    private Calendar calendar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String formattedDateFrom;
    private String formattedDateTo;

    public DatePickerFragment() {
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        tvFromDate = (TextView) view.findViewById(R.id.tvDateFrom);
        tvToDate = (TextView) view.findViewById(R.id.tvDateTo);
        datePickerFrom = (DatePicker) view.findViewById(R.id.datePickerBegin);
        datePickerTo = (DatePicker) view.findViewById(R.id.datePickerEnd);
        btnSetDateInterval = (Button) view.findViewById(R.id.btnSetDateInterval);
        btnSetDateInterval.setOnClickListener(new SetDateButtonListener());
        calendar = Calendar.getInstance();
        datePickerFrom.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                formattedDateFrom = dateFormat.format(calendar.getTime());
                Log.d("Chosen from date ", "onDateSet: " + formattedDateFrom);
            }
        });
        datePickerTo.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                formattedDateTo = dateFormat.format(calendar.getTime());
                Log.d("Chosen to date ", "onDateSet: " + formattedDateTo);
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class SetDateButtonListener implements View.OnClickListener {

        public void onClick(View view) {
            if (formattedDateFrom == null || formattedDateTo == null) {
                Toast.makeText(getActivity(), "Choose from date and to date",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                controller.setDates(formattedDateFrom,formattedDateTo);
                controller.setMenuDateViews();
                Toast.makeText(getActivity(), "Date from: " + formattedDateFrom
                        + " to: " + formattedDateTo,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
