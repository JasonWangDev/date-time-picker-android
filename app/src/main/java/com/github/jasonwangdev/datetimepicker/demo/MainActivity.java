package com.github.jasonwangdev.datetimepicker.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.jasonwangdev.datetimepicker.DateTimePickerDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateTimePickerDialogFragment.getInstance().show(getSupportFragmentManager(), "SEGSEG");
    }
}
