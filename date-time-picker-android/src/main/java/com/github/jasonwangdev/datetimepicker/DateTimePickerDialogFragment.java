package com.github.jasonwangdev.datetimepicker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

/**
 * Created by jason on 2017/6/28.
 */

public class DateTimePickerDialogFragment extends DialogFragment {

    public static DateTimePickerDialogFragment getInstance() {
        Bundle bundle = new Bundle();

        DateTimePickerDialogFragment dialog = new DateTimePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDialogNoTitle();
        View view = inflater.inflate(R.layout.dialogfragment_datetimepicker, container, false);

        setDatePickerDividerColor((DatePicker) view.findViewById(R.id.datePicker));
        setTimePickerDividerColor((TimePicker) view.findViewById(R.id.timePicker));

        return view;
    }

    @Override
    public void onResume() {
        setDialogSize();

        super.onResume();
    }


    private void setDialogNoTitle() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    private void setDialogSize() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        Configuration configuration = getResources().getConfiguration();
        switch ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK))
        {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;

            default:
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                }
                else
                {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                break;
        }
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private void setTimePickerDividerColor(TimePicker picker) {
        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("hour", "id", "android");
        int monthId = system.getIdentifier("minute", "id", "android");
        int yearId = system.getIdentifier("amPm", "id", "android");

        NumberPicker dayPicker = (NumberPicker) picker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) picker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) picker.findViewById(yearId);

//        setDividerColor(dayPicker);
//        setDividerColor(monthPicker);
//        setDividerColor(yearPicker);
    }

    private void setDatePickerDividerColor(DatePicker picker) {
        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("day", "id", "android");
        int monthId = system.getIdentifier("month", "id", "android");
        int yearId = system.getIdentifier("year", "id", "android");

        NumberPicker dayPicker = (NumberPicker) picker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) picker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) picker.findViewById(yearId);

//        setDividerColor(dayPicker);
//        setDividerColor(monthPicker);
//        setDividerColor(yearPicker);
    }

    private void setDividerColor(NumberPicker picker, int colorRes) {
        if (picker == null)
            return;

        int count = picker.getChildCount();
        for (int i = 0; i < count; i++)
        {
            try
            {
                Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(getActivity(), colorRes));
                dividerField.set(picker, colorDrawable);
                picker.invalidate();
            }
            catch (Exception e) { }
        }
    }

}
