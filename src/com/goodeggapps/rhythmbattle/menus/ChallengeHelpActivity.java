package com.goodeggapps.rhythmbattle.menus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.goodeggapps.rhythmbattle.R;

public class ChallengeHelpActivity extends Activity {

	
	public static int currentPicture;
    public static ImageView helpPicture;
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.challengehelp);

	        helpPicture = (ImageView) findViewById(R.id.helppicture);
		    helpPicture.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
				});
	    }
}