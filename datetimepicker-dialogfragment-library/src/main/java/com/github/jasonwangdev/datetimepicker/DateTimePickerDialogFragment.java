package com.github.jasonwangdev.datetimepicker;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/28.
 */

public class DateTimePickerDialogFragment extends SuperDialogFragment implements View.OnClickListener {

    private static final String KEY_YEAR = "Year";
    private static final String KEY_MONTH = "Month";
    private static final String KEY_DAY = "Day";
    private static final String KEY_HOUR = "Hour";
    private static final String KEY_MINUTE = "minute";

    private DatePicker datePicker;
    private TimePicker timePicker;


    public static DateTimePickerDialogFragment getInstance() {
        return getInstance(null);
    }

    public static DateTimePickerDialogFragment getInstance(Calendar calendar) {
        Bundle bundle = new Bundle();
        if (null != calendar)
        {
            bundle.putInt(KEY_YEAR, calendar.get(Calendar.YEAR));
            bundle.putInt(KEY_MONTH, calendar.get(Calendar.MONTH));
            bundle.putInt(KEY_DAY, calendar.get(Calendar.DAY_OF_MONTH));
            bundle.putInt(KEY_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
            bundle.putInt(KEY_MINUTE, calendar.get(Calendar.MINUTE));
        }

        DateTimePickerDialogFragment dialog = new DateTimePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_datetimepicker, container, false);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        setPickerDividerColor(datePicker, R.color.PickerDialogFragment_Picker_DividerColor);
        setPickerDividerColor(timePicker, R.color.PickerDialogFragment_Picker_DividerColor);

        initDatePicker();
        initTimePicker();

        initButton(view);

        return view;
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_today)
        {
            Calendar currentCalendar = Calendar.getInstance();
            setDatePicker(currentCalendar);
            setTimePicker(currentCalendar);
        }
        else if (viewId == R.id.btn_clear)
        {
            if (null != listener)
                listener.onDateTimeClear();

            dismiss();
        }
        else if (viewId == R.id.btn_ok)
        {
            Calendar calendar = getCalendar(datePicker, timePicker);
            if (null != listener)
                listener.onDateTimeSet(calendar);

            dismiss();
        }
    }


    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (getArguments().containsKey(KEY_YEAR))
        {
            calendar.set(Calendar.YEAR, getArguments().getInt(KEY_YEAR));
            calendar.set(Calendar.MONTH, getArguments().getInt(KEY_MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, getArguments().getInt(KEY_DAY));
        }

        setDatePicker(calendar);
    }

    private void initTimePicker() {
        Calendar calendar = Calendar.getInstance();
        if (getArguments().containsKey(KEY_HOUR))
        {
            calendar.set(Calendar.HOUR_OF_DAY, getArguments().getInt(KEY_HOUR));
            calendar.set(Calendar.MINUTE, getArguments().getInt(KEY_MINUTE));
        }

        setTimePicker(calendar);
    }

    private void initButton(View view) {
        view.findViewById(R.id.btn_today).setOnClickListener(this);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
    }


    private void setDatePicker(Calendar calendar) {
        datePicker.updateDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setTimePicker(Calendar calendar) {
        if (Build.VERSION.SDK_INT >= 23 )
        {
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
        }
        else
        {
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }
    }

}
