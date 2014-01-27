package com.goodeggapps.rhythmbattle.menus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.goodeggapps.rhythmbattle.R;
import com.goodeggapps.rhythmbattle.game.UpgradeActivity;


public class MenuActivity extends Activity {

	
    public static SharedPreferences settings;
    public static Integer upgradeLocked;
    public static TextView upgradeDescription;
    public static int Level1Score;
    public static int Level2Score;
    public static int Level3Score;
    public static int Level4Score;
    public static int Level5Score;
    public static boolean ULUnlockAll;
    public static boolean unlocked;

	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main_new);
	        setVolumeControlStream(AudioManager.STREAM_MUSIC);
	        //sets up the font
	        Typeface font = Typeface.createFromAsset(getAssets(),"font/pf_tempesta_five.ttf");
	        
	        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
	        if (firstrun){
	        	getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
	        	.putInt("HighestLevel", 1)
	        	.putInt("CurrentLevel", 1)
	        	.putInt("CurrentLevelED", 0) //Enemies Defeated
	        	.putInt("CurrentLevelSP", 0) //Song Points in percent
	        	.putInt("CurrentLevelSL", 0) //Song Level
	        	.putInt("CurrentLevelUPE", 0) //Upgrade Points Earned
	        	.putInt("SongLevel1", 0)  //Upgrade Points for each level
	        	.putInt("SongLevel2", 0)
	        	.putInt("SongLevel3", 0)
	        	.putInt("SongLevel4", 0)
	        	.putInt("SongLevel5", 0)
	        	//.putInt("SongLevel6UP", 0)
	        	//.putInt("SongLevel7UP", 0)
	        	//.putInt("SongLevel8UP", 0)
	        	.putBoolean("SongLevelUp", false)
	        	.putInt("ChallengeLevel", 0)
	        	.putInt("UpgradePoints", 0)
	        	.putInt("TotalEnemies", 0)
	        	.putInt("ULShield", 0)  //Upgrades
	        	.putInt("ULSpikes", 0)
	        	.putBoolean("ULUnlockAll", false)
	        	.putInt("ULDevTip", 0)
	            .putBoolean("firstrun", false)
	            .putBoolean("Challenge11", false) 
	            .putBoolean("Challenge12", false)
	            .putBoolean("Challenge13", false)
	            .putBoolean("Challenge21", false)
	            .putBoolean("Challenge22", false)
	            .putBoolean("Challenge23", false)
	            .putBoolean("Challenge31", false)
	            .putBoolean("Challenge32", false)
	            .putBoolean("Challenge33", false)
	            .putBoolean("Challenge41", false)
	            .putBoolean("Challenge42", false)
	            .putBoolean("Challenge43", false)
	            .putBoolean("Challenge51", false)
	            .putBoolean("Challenge52", false)
	            .putBoolean("Challenge53", false)
	            .commit();
	        }
	        settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
	        Level1Score = settings.getInt("SongLevel1", 0);  
	        Level2Score = settings.getInt("SongLevel2", 0);  
	        Level3Score = settings.getInt("SongLevel3", 0);  
	        Level4Score = settings.getInt("SongLevel4", 0);  
	        Level5Score = settings.getInt("SongLevel5", 0);  
	        ULUnlockAll = settings.getBoolean("ULUnlockAll", false);  
	        settings.edit().putInt("CurrentLevel", 1).commit();
	        
        	//sets the buttons to the proper font     
		    TextView btnHelp = (TextView) findViewById(R.id.btnHelp);
		    btnHelp.setTypeface(font);
		    
		    //actions for the help button
			btnHelp.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent help = new Intent(MenuActivity.this, HelpActivity.class);
					startActivity(help);}
			});
	        	
			
			//actions for the Challenge button
		    TextView btnChallenge = (TextView) findViewById(R.id.btnChallenge);
		    btnChallenge.setTypeface(font);
		    btnChallenge.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("RB", "Challenge clicked!");
					if (unlocked) {
						Intent challenge = new Intent(MenuActivity.this, ChallengeSelectActivity.class);
						startActivity(challenge);
					} else {
						Log.d("RB", "AlertDialog opened");
	 					AlertDialog.Builder alertbox = new AlertDialog.Builder(MenuActivity.this);
						alertbox.setTitle("Secret!");
						alertbox.setIcon(R.drawable.oldicon);
						alertbox.setCancelable(true);
					    alertbox.setMessage("To unlock, complete all levels with song lvl MAX!");
			            alertbox.setPositiveButton("Close",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
									}
								});
			            alertbox.show();    
						}
					
					}
			});
		    unlocked = false;
		    if(Level1Score == 5 && Level2Score == 5 
		    		&& Level3Score == 5 && Level4Score == 5 
		    		&& Level5Score == 5) {
		    	btnChallenge.setText("Challenge Mode");
		    	unlocked = true;
		    }
			if (ULUnlockAll == true){
				btnChallenge.setText("Challenge Mode");
				unlocked = true;
			}
			
			//actions for the exit button
		    TextView btnExit = (TextView) findViewById(R.id.btnExit);
	        btnExit.setTypeface(font);
	        btnExit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showExitDialog();}
			});
	        
	        TextView btnAbout = (TextView) findViewById(R.id.btnAbout);
	        btnAbout.setTypeface(font);
	        btnAbout.setOnClickListener(new OnClickListener() {
				//sets the credits
				@Override
				public void onClick(View v) {

 					AlertDialog.Builder alertbox = new AlertDialog.Builder(MenuActivity.this);
					alertbox.setTitle("CREDITS");
					alertbox.setIcon(R.drawable.oldicon);
					alertbox.setCancelable(false);
				    alertbox.setMessage("" +
		            		"Programming, Art, and Design by: \n" +
		            		"                     Will Shelor  \n\n" +
		            		"                        Music: \n" +
		            		"                  Chris Heidorn \n\n" +
		            		"                Special Thanks: \n" +
		            		"                 Valerie Carmer \n" +
		            		"                 AndEngine.org \n" +
		            		"                 Nikola Trifundic\n" +
		            		"                  Maggie Shelor");
		 
		            alertbox.setPositiveButton("Close",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
								}
							});
					alertbox.setNegativeButton("Rate us!", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							String url = "market://details?id=com.goodeggapps.rhythmbattle";
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(url));
							startActivity(i);
						}
					});
		            alertbox.show();
				}
			});
	        
	        TextView btnStartGame = (TextView) findViewById(R.id.btnStartGame);
	        btnStartGame.setTypeface(font);
	        btnStartGame.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					Intent levelselect = new Intent(MenuActivity.this, LevelSelectActivity.class);
					startActivity(levelselect);
				}
			});
	        

	        TextView btnHighscore = (TextView) findViewById(R.id.btnUpgrades);
	        btnHighscore.setTypeface(font);
	        btnHighscore.setOnClickListener(new OnClickListener() {

	        	
	        	//Runs the upgrade screen.
				@Override
				public void onClick(View v) {
					Intent upgrades = new Intent(MenuActivity.this, UpgradeActivity.class);
					startActivity(upgrades);
		
				}
			});
	        
	    }
	    
	    
	   
	    @Override
		public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {

			if (pKeyCode == KeyEvent.KEYCODE_BACK
					&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
				MenuActivity.this.finish();
				return true;
			}
			
			return super.onKeyDown(pKeyCode, pEvent);
		}
	    
	    
	    //Do you want to exit? Yes/No
	    public void showExitDialog() {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
			builder.setMessage("Are you sure you want to exit?")
					.setCancelable(false)
					.setTitle("EXIT")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									MenuActivity.this.finish();
								}
							})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert = builder.create();
			alert.setIcon(R.drawable.icon);
			alert.show();
	    }

	    public void onResume() {
	    	super.onResume();
		    TextView btnChallenge = (TextView) findViewById(R.id.btnChallenge);
		    settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
	        settings.edit().putInt("CurrentLevel", 1).commit();
	        Level1Score = settings.getInt("SongLevel1", 0);  
	        Level2Score = settings.getInt("SongLevel2", 0);  
	        Level3Score = settings.getInt("SongLevel3", 0);  
	        Level4Score = settings.getInt("SongLevel4", 0);  
	        Level5Score = settings.getInt("SongLevel5", 0);  
	        ULUnlockAll = settings.getBoolean("ULUnlockAll", false);  
	    	final boolean ULUnlockAll = settings.getBoolean("ULUnlockAll", false);  
		    if (Level1Score == 5 && Level2Score == 5 && Level3Score == 5 && Level4Score == 5 && Level5Score == 5) btnChallenge.setText("Challenge Mode");
		    if (ULUnlockAll) btnChallenge.setText("Challenge Mode");
		    btnChallenge.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent help = new Intent(MenuActivity.this, ChallengeSelectActivity.class);
			if (ULUnlockAll == true) startActivity(help);
			if (Level1Score == 5 && Level2Score == 5 && Level3Score == 5 && Level4Score == 5 && Level5Score == 5)startActivity(help);
				}});
	    	
	    }
}