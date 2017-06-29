package com.github.jasonwangdev.datetimepicker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.jasonwangdev.datetimepicker.OnDateTimePickerDialogFragmentClickListener;
import com.github.jasonwangdev.datetimepicker.TimePickerDialogFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        TimePickerDialogFragment dialogFragment = TimePickerDialogFragment.getInstance(calendar);
        dialogFragment.setOnDateTimePickerDialogFragmentClickListener(new OnDateTimePickerDialogFragmentClickListener() {
            @Override
            public void onDateTimeClear() {
                Log.d("TAG", "Clear");
            }

            @Override
            public void onDateTimeSet(Calendar calendar) {
                Log.d("TAG", String.format("%tT", calendar.getTime().getTime()));
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "SEGSEG");
    }
}
