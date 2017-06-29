package com.github.jasonwangdev.datetimepicker;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by jason on 2017/6/29.
 */

public abstract class SuperDialogFragment extends DialogFragment {

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    protected static final String KEY_YEAR = "Year";
    protected static final String KEY_MONTH = "Month";
    protected static final String KEY_DAY = "Day";

    protected static final String KEY_HOUR = "Hour";
    protected static final String KEY_MINUTE = "Minute";


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

}
