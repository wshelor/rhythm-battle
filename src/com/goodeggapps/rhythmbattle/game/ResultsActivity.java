package com.goodeggapps.rhythmbattle.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodeggapps.rhythmbattle.R;
import com.google.ads.AdView;


public class ResultsActivity extends Activity {

    	public static OnTouchListener exitListener;
    	
    	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.results);
	        setVolumeControlStream(AudioManager.STREAM_MUSIC);
	        
	        SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);

	        int ULDevTip = settings.getInt("ULDevTip", 0);
	        int currentLevel = settings.getInt("CurrentLevel", 1);
	        int enemiesDefeated = settings.getInt("CurrentLevelED", 0);
	        int songLevel = settings.getInt("CurrentLevelSL", 0); 
	        final int challengeLevel = settings.getInt("ChallengeLevel", 0); 
	        final boolean upgradePointsEarned = settings.getBoolean("SongLevelUp", false);
	        int upgradePointsTotal = settings.getInt("UpgradePoints", 0); 
	        int totalEnemies = settings.getInt("TotalEnemies", 0);

	        if (ULDevTip > 0) {
	            AdView adView = (AdView) findViewById(R.id.adView);
	            adView.setVisibility(View.GONE);
	            }

	        
	        int songPointsTotal = 0;
        	exitListener = new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
			        if (upgradePointsEarned == true && challengeLevel == 0) {
			        }
					finish();
					return true;
				}};
	        
	        if (upgradePointsEarned == true) {
	        	int Level1SL = settings.getInt("SongLevel1", 0);
	        	int Level2SL = settings.getInt("SongLevel2", 0);
	        	int Level3SL = settings.getInt("SongLevel3", 0);
	        	int Level4SL = settings.getInt("SongLevel4", 0);
	        	int Level5SL = settings.getInt("SongLevel5", 0);
	        	if (Level1SL == 5 && Level2SL == 5 
	        			&& Level3SL == 5 && Level4SL == 5 && Level5SL == 5) {
	 				AlertDialog.Builder alertbox = new AlertDialog.Builder(ResultsActivity.this);
					alertbox.setTitle("Challenge Mode Unlocked!");
					alertbox.setCancelable(false);
					alertbox.setIcon(R.drawable.oldicon);
				    alertbox.setMessage("" +
			         	"Congratulations!  You have earned MAX level on every song!  You have unlocked CHALLENGE MODE!  Why not take a second to rate us?");
			        alertbox.setPositiveButton("No, Challenge Time!",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							}
						});
			        alertbox.setNegativeButton("Sure, Rate Us!", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String url = "market://details?id=com.goodeggapps.rhythmbattle";
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
					});
			        alertbox.show();
        			settings.edit().putBoolean("ChallengeUnlocked", true).commit();
	        	}
	        }
	        TextView EnemiesDefeatedTV = (TextView) findViewById(R.id.RSEnemiesDefeated);
        	TextView SongPointsTV = (TextView) findViewById(R.id.RSSongPoints);
	        TextView SongLevelTV = (TextView) findViewById(R.id.RSSongLevel);
	     	TextView UpgradePointsTotalTV = (TextView) findViewById(R.id.RSUpgradePointsTotal);
	     	TextView doneMessage = (TextView) findViewById(R.id.doneMessage);
	        TextView EnemiesDefeatedTVTitle = (TextView) findViewById(R.id.RSEnemiesDefeatedTitle);
        	TextView SongPointsTVTitle = (TextView) findViewById(R.id.RSSongPointsTitle);
	        TextView SongLevelTVTitle = (TextView) findViewById(R.id.RSSongLevelTitle);
	     	TextView UpgradePointsTotalTVTitle = (TextView) findViewById(R.id.RSUpgradePointsTotalTitle);
	     	TextView ChallengeComplete = (TextView) findViewById(R.id.ChallengeComplete);
			ImageView levelTitle = (ImageView) findViewById(R.id.levelTitle);
			EnemiesDefeatedTV.setOnTouchListener(exitListener);
			doneMessage.setOnTouchListener(exitListener);
			SongPointsTV.setOnTouchListener(exitListener);
			SongLevelTV.setOnTouchListener(exitListener);
			UpgradePointsTotalTV.setOnTouchListener(exitListener);
	        EnemiesDefeatedTVTitle.setOnTouchListener(exitListener);
        	SongPointsTVTitle.setOnTouchListener(exitListener);
	        SongLevelTVTitle.setOnTouchListener(exitListener);
	     	UpgradePointsTotalTVTitle.setOnTouchListener(exitListener);
	     	ChallengeComplete.setOnTouchListener(exitListener);
	     	
	     	
			switch (currentLevel) {
			case 1: 
				levelTitle.setImageResource(R.drawable.piracysmall);
				break;
			case 2:
				levelTitle.setImageResource(R.drawable.japansmall);
				break;
			case 3:
				levelTitle.setImageResource(R.drawable.indiasmall);
				break;
			case 4:
				levelTitle.setImageResource(R.drawable.medievalsmall);
				break;
			case 5:
				levelTitle.setImageResource(R.drawable.spacesmall);
				break;
			}

			songPointsTotal = (enemiesDefeated * 100) / totalEnemies;
			Log.d("test ", " enemiesDefeated" + enemiesDefeated + " Total Enemies " + totalEnemies);
			if (songPointsTotal > 100)songPointsTotal = 100;
	     	LinearLayout mainLayout = (LinearLayout) findViewById(R.id.RSMainLayout);
	     	EnemiesDefeatedTV.setText("" + enemiesDefeated);
	    	SongPointsTV.setText("" + songPointsTotal + "%");
	       	SongLevelTV.setText("" + songLevel);
        	settings.edit().putInt("UpgradePoints", upgradePointsTotal + enemiesDefeated).commit();
	    	UpgradePointsTotalTV.setText("" + (upgradePointsTotal + enemiesDefeated));
	    	if (challengeLevel == 0) {
	    		
	    	} else {
		        EnemiesDefeatedTV.setVisibility(View.GONE);
	        	SongPointsTV.setVisibility(View.GONE);
		        SongLevelTV.setVisibility(View.GONE);
		     	UpgradePointsTotalTV.setVisibility(View.GONE);
		     	doneMessage.setVisibility(View.GONE);
		        EnemiesDefeatedTVTitle.setVisibility(View.GONE);
	        	SongPointsTVTitle.setVisibility(View.GONE);
		        SongLevelTVTitle.setVisibility(View.GONE);
		     	UpgradePointsTotalTVTitle.setVisibility(View.GONE);
		     	ChallengeComplete.setVisibility(View.VISIBLE);
	    		
	    	switch (challengeLevel){
	    		case 1:
	    			switch (currentLevel) {
		    		case 1:
		    	        settings.edit().putBoolean("Challenge11", true).commit();  
		    			break;
		    		case 2:
		    			settings.edit().putBoolean("Challenge21", true).commit();
		    			break;
		    		case 3:
		    			settings.edit().putBoolean("Challenge31", true).commit();
		    			break;
		    		case 4:
		    			settings.edit().putBoolean("Challenge41", true).commit();
		    			break;
		    		case 5:
		    			settings.edit().putBoolean("Challenge51", true).commit();
		    			break;
		    		default:break;
	    			}
	    			break;
	    		case 2:
	    			switch (currentLevel) {
		    		case 1:
		    			settings.edit().putBoolean("Challenge12", true).commit();
		    			break;
		    		case 2:
		    			settings.edit().putBoolean("Challenge22", true).commit();
		    			break;
		    		case 3:
		    			settings.edit().putBoolean("Challenge32", true).commit();
		    			break;
		    		case 4:
		    			settings.edit().putBoolean("Challenge42", true).commit();
		    			break;
		    		case 5:
		    			settings.edit().putBoolean("Challenge52", true).commit();
		    			break;
		    		default:break;
	    			}
	    			break;
	    		case 3:
	    			switch (currentLevel) {
		    		case 1:
		    			settings.edit().putBoolean("Challenge13", true).commit();
		    			break;
		    		case 2:
		    			settings.edit().putBoolean("Challenge23", true).commit();
		    			break;
		    		case 3:
		    			settings.edit().putBoolean("Challenge33", true).commit();
		    			break;
		    		case 4:
		    			settings.edit().putBoolean("Challenge43", true).commit();
		    			break;
		    		case 5:
		    			settings.edit().putBoolean("Challenge53", true).commit();
		    			break;
		    		default:break;
	    			}
	    			break;
	    		default:
	    			break;
	    		}
	    	}
		     mainLayout.setOnTouchListener(exitListener);
	    }
}