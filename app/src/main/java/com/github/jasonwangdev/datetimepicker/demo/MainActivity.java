package com.github.jasonwangdev.datetimepicker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.jasonwangdev.datetimepicker.DatePickerDialogFragment;
import com.github.jasonwangdev.datetimepicker.DateTimePickerDialogFragment;
import com.github.jasonwangdev.datetimepicker.OnDateTimePickerDialogFragmentClickListener;
import com.github.jasonwangdev.datetimepicker.TimePickerDialogFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_date).setOnClickListener(this);
        findViewById(R.id.btn_time).setOnClickListener(this);
        findViewById(R.id.btn_datetime).setOnClickListener(this);

        restoring();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_date:
                showDatePickerDialogFragment();
                break;

            case R.id.btn_time:
                showTimePickerDialogFragment();
                break;

            case R.id.btn_datetime:
                showDateTimePickerDialogFragment();
                break;
        }
    }


    private void restoring() {
        resetListener();
    }

    private void resetListener() {
        resetDatePickerDialogListener();
        resetTimePickerDialogListener();
        resetDateTimePickerDialogListener();
    }

    private void resetDatePickerDialogListener() {
        DatePickerDialogFragment dialog = (DatePickerDialogFragment) getSupportFragmentManager().findFragmentByTag("DatePickerDialogFragment");
        if (null != dialog)
            dialog.setOnDateTimePickerDialogFragmentClickListener(onDatePickerClickerListener);
    }

    private void resetTimePickerDialogListener() {
        TimePickerDialogFragment dialog = (TimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag("TimePickerDialogFragment");
        if (null != dialog)
            dialog.setOnDateTimePickerDialogFragmentClickListener(onTimePickerClickerListener);
    }

    private void resetDateTimePickerDialogListener() {
        DateTimePickerDialogFragment dialog = (DateTimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag("DateTimePickerDialogFragment");
        if (null != dialog)
            dialog.setOnDateTimePickerDialogFragmentClickListener(onDateTimePickerClickerListener);
    }


    private void showDatePickerDialogFragment() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialogFragment dialog = DatePickerDialogFragment.getInstance(calendar);
        dialog.setOnDateTimePickerDialogFragmentClickListener(onDatePickerClickerListener);
        dialog.show(getSupportFragmentManager(), "DatePickerDialogFragment");
    }

    private void showTimePickerDialogFragment() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialogFragment dialog = TimePickerDialogFragment.getInstance(calendar);
        dialog.setOnDateTimePickerDialogFragmentClickListener(onTimePickerClickerListener);
        dialog.show(getSupportFragmentManager(), "TimePickerDialogFragment");
    }

    private void showDateTimePickerDialogFragment() {
        Calendar calendar = Calendar.getInstance();
        DateTimePickerDialogFragment dialog = DateTimePickerDialogFragment.getInstance(calendar);
        dialog.setOnDateTimePickerDialogFragmentClickListener(onDateTimePickerClickerListener);
        dialog.show(getSupportFragmentManager(), "DateTimePickerDialogFragment");
    }


    private OnDateTimePickerDialogFragmentClickListener onDatePickerClickerListener = new OnDateTimePickerDialogFragmentClickListener() {
        @Override
        public void onDateTimeClear() {
            showToast("DatePickerDialog: Clear Click");
        }

        @Override
        public void onDateTimeSet(Calendar calendar) {
            showToast("DatePickerDialog: " + String.format("%tF", calendar.getTime().getTime()));
        }
    };

    private OnDateTimePickerDialogFragmentClickListener onTimePickerClickerListener = new OnDateTimePickerDialogFragmentClickListener() {
        @Override
        public void onDateTimeClear() {
            showToast("TimePickerDialog: Clear Click");
        }

        @Override
        public void onDateTimeSet(Calendar calendar) {
            showToast("TimePickerDialog: " + String.format("%tT", calendar.getTime().getTime()));
        }
    };

    private OnDateTimePickerDialogFragmentClickListener onDateTimePickerClickerListener = new OnDateTimePickerDialogFragmentClickListener() {
        @Override
        public void onDateTimeClear() {
            showToast("DateTimePickerDialog: Clear Click");
        }

        @Override
        public void onDateTimeSet(Calendar calendar) {
            showToast("DateTimePickerDialog: " + String.format("%tF %<tT", calendar.getTime().getTime()));
        }
    };


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
