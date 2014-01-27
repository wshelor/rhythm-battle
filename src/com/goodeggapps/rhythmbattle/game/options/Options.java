package com.goodeggapps.rhythmbattle.game.options;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Options {
	
	SharedPreferences options;
	
	public Options(Activity activity){
		options = PreferenceManager.getDefaultSharedPreferences(activity);
	}
	
	public boolean getSoundEffects(){
		return options.getBoolean("chkSound",false);
	}
	
	public boolean getMusic(){
		return options.getBoolean("chkMusic",false);
	}
	
	public boolean getVibration(){
		return options.getBoolean("chkVibration",false);
	}
	
	public boolean getAutoFire(){
		return options.getBoolean("chkAutoFire",false);
	}
	
	public String getUsername(){
		return options.getString("edUsername",null);
	}
	
	public String getLevel(){
		return options.getString("LevelSelect","-1");
	}
	
	public String getDifficulty(){
		return options.getString("lstDifficulty","-1");
	}
	
	public String getResolution(){
		return options.getString("lstResolution","-1");
	}

}
