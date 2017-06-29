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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_date:
            {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialogFragment dialog = DatePickerDialogFragment.getInstance(calendar);
                dialog.setOnDateTimePickerDialogFragmentClickListener(new OnDateTimePickerDialogFragmentClickListener() {
                    @Override
                    public void onDateTimeClear() {
                        showToast("Clear Click");
                    }

                    @Override
                    public void onDateTimeSet(Calendar calendar) {
                        showToast(String.format("%tF", calendar.getTime().getTime()));
                    }
                });
                dialog.show(getSupportFragmentManager(), "TAG");
                break;
            }
            case R.id.btn_time:
            {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialogFragment dialog = TimePickerDialogFragment.getInstance(calendar);
                dialog.setOnDateTimePickerDialogFragmentClickListener(new OnDateTimePickerDialogFragmentClickListener() {
                    @Override
                    public void onDateTimeClear() {
                        showToast("Clear Click");
                    }

                    @Override
                    public void onDateTimeSet(Calendar calendar) {
                        showToast(String.format("%tT", calendar.getTime().getTime()));
                    }
                });
                dialog.show(getSupportFragmentManager(), "TAG");
                break;
            }
            case R.id.btn_datetime:
            {
                Calendar calendar = Calendar.getInstance();
                DateTimePickerDialogFragment dialog = DateTimePickerDialogFragment.getInstance(calendar);
                dialog.setOnDateTimePickerDialogFragmentClickListener(new OnDateTimePickerDialogFragmentClickListener() {
                    @Override
                    public void onDateTimeClear() {
                        showToast("Clear Click");
                    }

                    @Override
                    public void onDateTimeSet(Calendar calendar) {
                        showToast(String.format("%tF %<tT", calendar.getTime().getTime()));
                    }
                });
                dialog.show(getSupportFragmentManager(), "TAG");
                break;
            }
        }
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
