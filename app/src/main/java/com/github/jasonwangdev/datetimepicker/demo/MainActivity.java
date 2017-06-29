package com.github.jasonwangdev.datetimepicker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.jasonwangdev.datetimepicker.DatePickerDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.getInstance();
        dialogFragment.show(getSupportFragmentManager(), "SEGEG");
    }
}
