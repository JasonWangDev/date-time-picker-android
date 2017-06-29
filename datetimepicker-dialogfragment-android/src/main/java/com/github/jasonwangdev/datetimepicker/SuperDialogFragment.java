package com.github.jasonwangdev.datetimepicker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public abstract class SuperDialogFragment extends DialogFragment {

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDialogNoTitle();

        return createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        setDialogSize();

        super.onResume();
    }


    protected void setPickerDividerColor(View picker, int colorRes) {
        if (null == picker)
            return;

        Resources system = Resources.getSystem();
        int[] ids;
        if (picker instanceof DatePicker)
        {
            ids = new int[]{system.getIdentifier("day", "id", "android"),
                            system.getIdentifier("month", "id", "android"),
                            system.getIdentifier("year", "id", "android")};
        }
        else if (picker instanceof TimePicker)
        {
            ids = new int[]{system.getIdentifier("hour", "id", "android"),
                            system.getIdentifier("minute", "id", "android"),
                            system.getIdentifier("amPm", "id", "android")};
        }
        else
            return;

        for (int id : ids)
        {
            NumberPicker dayPicker = (NumberPicker) picker.findViewById(id);
            setPickerDividerColor(dayPicker, colorRes);
        }
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


    private void setPickerDividerColor(NumberPicker picker, int colorRes) {
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
