package com.github.jasonwangdev.datetimepicker;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public class TimePickerDialogFragment extends SuperDialogFragment implements View.OnClickListener {

    private static final String KEY_HOUR = "Hour";
    private static final String KEY_MINUTE = "Minute";

    private OnDateTimePickerDialogFragmentClickListener listener;

    private TimePicker timePicker;


    public static TimePickerDialogFragment getInstance() {
        return getInstance(null);
    }

    public static TimePickerDialogFragment getInstance(Calendar calendar) {
        Bundle bundle = new Bundle();
        if (null != calendar)
        {
            bundle.putInt(KEY_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
            bundle.putInt(KEY_MINUTE, calendar.get(Calendar.MINUTE));
        }

        TimePickerDialogFragment dialog = new TimePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_timepicker, container, false);

        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        setPickerDividerColor(timePicker, R.color.PickerDialogFragment_Picker_DividerColor);
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
            Calendar timePickerCalendar = getCalendar(timePicker);
            if (null != listener)
                listener.onDateTimeSet(timePickerCalendar);

            dismiss();
        }
    }


    public void setOnDateTimePickerDialogFragmentClickListener(OnDateTimePickerDialogFragmentClickListener listener) {
        this.listener = listener;
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
