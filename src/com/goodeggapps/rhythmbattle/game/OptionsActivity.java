package com.goodeggapps.rhythmbattle.game;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.goodeggapps.rhythmbattle.R;

public class OptionsActivity extends PreferenceActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.options);
	}

}
