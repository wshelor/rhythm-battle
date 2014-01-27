package com.goodeggapps.rhythmbattle.game.level;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.goodeggapps.rhythmbattle.R;

public class Level {

	private BaseGameActivity activity;
	private ArrayList<LevelScene> scenes;
	private int currLevel;
	
	public Level(BaseGameActivity e, int CurrentLevel){
		activity = e;
		scenes = new ArrayList<LevelScene>();
		SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(activity);
			currLevel = options.getInt("LevelSelect",0);		
			
		initScenes(CurrentLevel);
	}
	
	public void initScenes(int CurrentLevel){
		for(int i=0;i<4;i++){
			LevelScene s = new LevelScene(3);
			s.setBackground(new ColorBackground(0, 0, 0));
			// LOAD LEVEL
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				LevelHandler myXMLHandler = new LevelHandler();
				xr.setContentHandler(myXMLHandler);
				switch(CurrentLevel){
				case 1:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level1)));
					break;
				case 2:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level2)));
					break;
				case 3:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level3)));
					break;
				case 4:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level4)));
					break;
				case 5:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level5)));
					break;
				case 6:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level6)));
					break;
				}
				s.setWaves(LevelHandler.wavesList);
			} catch (Exception e) {
			}
			scenes.add(s);
		}
	}
	
	public int getLevel(){
		return currLevel;
	}
	
	public LevelScene getScene(){
		return scenes.get(currLevel);
	}
}
