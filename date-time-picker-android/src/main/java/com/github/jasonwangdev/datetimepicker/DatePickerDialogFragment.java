package com.github.jasonwangdev.datetimepicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public class DatePickerDialogFragment extends SuperDialogFragment {

    public static DatePickerDialogFragment getInstance() {
        return getInstance(null);
    }

    public static DatePickerDialogFragment getInstance(Calendar calendar) {
        Bundle bundle = new Bundle();
        if (null != calendar)
        {
            bundle.putInt(KEY_YEAR, calendar.get(Calendar.YEAR));
            bundle.putInt(KEY_MONTH, calendar.get(Calendar.MONTH));
            bundle.putInt(KEY_DAY, calendar.get(Calendar.DAY_OF_MONTH));
        }

        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_datepicker, container, false);

        return view;
    }

}
