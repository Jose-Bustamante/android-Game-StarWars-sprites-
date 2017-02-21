package com.example.jose_user.juegojedis;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Jose-User on 02/02/2016.
 */
public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }
}
