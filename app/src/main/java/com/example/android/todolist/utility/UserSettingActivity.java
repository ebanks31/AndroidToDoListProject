package com.example.android.todolist.utility;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.example.android.todolist.R;

/**
 * The type User setting activity.
 */
public class UserSettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    /**
     * The type My preference fragment.
     */
    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        @SuppressWarnings("deprecation")
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.user_settings);
        }
    }
}
