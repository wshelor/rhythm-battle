package com.goodeggapps.rhythmbattle.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.goodeggapps.rhythmbattle.R;
import com.goodeggapps.rhythmbattle.game.GameActivity;
import com.google.ads.AdView;

public class LevelSelectActivity extends Activity {

    public static boolean ULSecretLevel;
    public static SharedPreferences settings;
    public static Integer level1;
    public static Integer level2;
    public static Integer level3;
    public static Integer level4;
    public static Integer level5;
    public static Integer upgradeLocked;
    public static ImageView levelImage;
    public static ImageAdapter galleryAdapter;
    public static Intent game;
    public static Gallery gallery;
    public static int Level1Score;
    public static int Level2Score;
    public static int Level3Score;
    public static int Level4Score;
    public static int Level5Score;
    public static int CurrentLevel;
    public static int ULDevTip;
    public static boolean ULUnlockAll;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselector);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        boolean LSfirstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstlevel", true);
        	if (LSfirstrun){
 		Intent help = new Intent(LevelSelectActivity.this, HelpActivity.class);
		startActivity(help);
       	getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
    	.putBoolean("firstlevel", false)
        .commit();

        	}
        
        getSettings();
        
        if (ULDevTip > 0) {
            AdView adView = (AdView) findViewById(R.id.adView);
            adView.setVisibility(View.GONE);
            }

        checkLevelImages();
        gallery = (Gallery) findViewById(R.id.gallery);
		galleryAdapter = new ImageAdapter(this);

        gallery.setAdapter(galleryAdapter);
        levelImage = (ImageView) findViewById(R.id.lvlImage);
        
        gallery.setOnItemClickListener(new OnItemClickListener() { 
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	switch(position) {
            	case 0:
			        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("CurrentLevel", 1)
		        	.commit();
					game = new Intent(LevelSelectActivity.this, GameActivity.class);
					startActivity(game);
            		break;
            	case 1:
                    if (Level1Score >= 2 || ULUnlockAll == true) {
            		getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("CurrentLevel", 2)
		        	.commit();
					game = new Intent(LevelSelectActivity.this, GameActivity.class);
					startActivity(game);
            		}
            		break;
            	case 2:
                    if (Level2Score >= 3 || ULUnlockAll == true) {
            		getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("CurrentLevel", 3)
		        	.commit();
					game = new Intent(LevelSelectActivity.this, GameActivity.class);
					startActivity(game);
            	 }
            		break;
            	case 3:
                    if (Level3Score >= 3 || ULUnlockAll == true) {
            		getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("CurrentLevel", 4)
		        	.commit();
					game = new Intent(LevelSelectActivity.this, GameActivity.class);
					startActivity(game);
            	}
            		break;
            	case 4:
                    if (Level1Score > 3 && Level2Score > 3 && Level3Score > 3 && Level4Score > 3 || ULUnlockAll == true) {

            		getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("CurrentLevel", 5)
		        	.commit();
					game = new Intent(LevelSelectActivity.this, GameActivity.class);
					startActivity(game);
            	}
            		break;
           		default:
           			break;
            }
        }});
      //level 1: india level 2: 8bit level 3: Japan level 4: ocean level 5:space
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
            	switch(arg2) {
            	case 0:
            		switch(Level1Score) {
            		case 1:
            			levelImage.setBackgroundResource(R.drawable.songlvl1);
            			break;
            		case 2:
            			levelImage.setBackgroundResource(R.drawable.songlvl2);
            			break;
            		case 3:
            			levelImage.setBackgroundResource(R.drawable.songlvl3);
            			break;
            		case 4:
            			levelImage.setBackgroundResource(R.drawable.songlvl4);
            			break;
            		case 5:
            			levelImage.setBackgroundResource(R.drawable.songlvlmax);
            			break; 
            		default:
            			levelImage.setBackgroundResource(R.drawable.songlvlnew);
            			break;
            		}
            		break;
            	case 1:
                    if (Level1Score < 2 && ULUnlockAll == false) {
            			levelImage.setBackgroundResource(R.drawable.lvl2locked);            			
            		} else {
            		switch(Level2Score) {
            		case 1:
            			levelImage.setBackgroundResource(R.drawable.songlvl1);
            			break;
            		case 2:
            			levelImage.setBackgroundResource(R.drawable.songlvl2);
            			break;
            		case 3:
            			levelImage.setBackgroundResource(R.drawable.songlvl3);
            			break;
            		case 4:
            			levelImage.setBackgroundResource(R.drawable.songlvl4);
            			break;
            		case 5:
            			levelImage.setBackgroundResource(R.drawable.songlvlmax);
            			break;
            		default:
            			levelImage.setBackgroundResource(R.drawable.songlvlnew);
            			break;
            		}}
            		break;
            	case 2:
                    if (Level2Score < 3 && ULUnlockAll == false) {
            			levelImage.setBackgroundResource(R.drawable.lvl3locked);            			
            		} else {
            		switch(Level3Score) {
            		case 1:
            			levelImage.setBackgroundResource(R.drawable.songlvl1);
            			break;
            		case 2:
            			levelImage.setBackgroundResource(R.drawable.songlvl2);
            			break;
            		case 3:
            			levelImage.setBackgroundResource(R.drawable.songlvl3);
            			break;
            		case 4:
            			levelImage.setBackgroundResource(R.drawable.songlvl4);
            			break;
            		case 5:
            			levelImage.setBackgroundResource(R.drawable.songlvlmax);
            			break;
            		default:
            			levelImage.setBackgroundResource(R.drawable.songlvlnew);
            			break;
            		}}
            		break;
            	case 3:
                    if (Level3Score < 3 && ULUnlockAll == false) {
            			levelImage.setBackgroundResource(R.drawable.lvl4locked);            			
            		} else {
            		switch(Level4Score) {
            		case 1:
            			levelImage.setBackgroundResource(R.drawable.songlvl1);
            			break;
            		case 2:
            			levelImage.setBackgroundResource(R.drawable.songlvl2);
            			break;
            		case 3:
            			levelImage.setBackgroundResource(R.drawable.songlvl3);
            			break;
            		case 4:
            			levelImage.setBackgroundResource(R.drawable.songlvl4);
            			break;
            		case 5:
            			levelImage.setBackgroundResource(R.drawable.songlvlmax);
            			break;
            		default:
            			levelImage.setBackgroundResource(R.drawable.songlvlnew);
            			break;
            		}}
            		break;
            	case 4:
                    if (Level1Score > 3 && Level2Score > 3 && Level3Score > 3 && Level4Score > 3 || ULUnlockAll == true) {

                		switch(Level5Score) {
                		case 1:
                			levelImage.setBackgroundResource(R.drawable.songlvl1);
                			break;
                		case 2:
                			levelImage.setBackgroundResource(R.drawable.songlvl2);
                			break;
                		case 3:
                			levelImage.setBackgroundResource(R.drawable.songlvl3);
                			break;
                		case 4:
                			levelImage.setBackgroundResource(R.drawable.songlvl4);
                			break;
                		case 5:
                			levelImage.setBackgroundResource(R.drawable.songlvlmax);
                			break;
                		default:
                			levelImage.setBackgroundResource(R.drawable.songlvlnew);
                			break;
                		}} else {
                			levelImage.setBackgroundResource(R.drawable.lvl5locked);            			

            		}
            		break;
           		default:
           			break;
            }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}	
        	
        });
            
    }
    
    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;
        
        private Integer[] mImageIds = {
                level1,
                level2,
                level3,
                level4,
                level5
        };

        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
            mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0);
            attr.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);

            imageView.setImageResource(mImageIds[position]);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(mGalleryItemBackground);

            return imageView;
        }
    }

    private void checkLevelImages() {
        level1 = R.drawable.piracytitle;
        if (Level1Score < 2 && ULUnlockAll == false) {
        	level2 = R.drawable.levellocked;
        	} else level2 = R.drawable.japantitle;
        if (Level2Score < 3 && ULUnlockAll == false) {
        	level3 = R.drawable.levellocked;
        } else level3 = R.drawable.indiatitle;
        if (Level3Score < 3 && ULUnlockAll == false) {
        	level4 = R.drawable.levellocked;
        	} else level4 = R.drawable.medievaltitle;
        if (Level1Score > 3 && Level2Score > 3 && Level3Score > 3 && Level4Score > 3 || ULUnlockAll == true) {
        	level5 = R.drawable.spacetitle;
        	} else {
        		level5 = R.drawable.levellocked;
        	}

	}
	public void getSettings(){
    	settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        settings.edit().putInt("ChallengeLevel", 0).commit();
        Level1Score = settings.getInt("SongLevel1", 0);  
        Level2Score = settings.getInt("SongLevel2", 0);  
        Level3Score = settings.getInt("SongLevel3", 0);  
        Level4Score = settings.getInt("SongLevel4", 0);  
        Level5Score = settings.getInt("SongLevel5", 0);  
        ULDevTip = settings.getInt("ULDevTip", 0);
        CurrentLevel = settings.getInt("CurrentLevel", 0);
        ULUnlockAll = settings.getBoolean("ULUnlockAll", false);
    }


    public void onResume(){
    	getSettings();
    	gallery.setSelection(CurrentLevel - 1);
    	checkLevelImages();	
    	int currentScore = settings.getInt("SongLevel" + CurrentLevel, 0);
		switch(currentScore) {
		case 1:
			levelImage.setBackgroundResource(R.drawable.songlvl1);
			break;
		case 2:
			levelImage.setBackgroundResource(R.drawable.songlvl2);
			break;
		case 3:
			levelImage.setBackgroundResource(R.drawable.songlvl3);
			break;
		case 4:
			levelImage.setBackgroundResource(R.drawable.songlvl4);
			break;
		case 5:
			levelImage.setBackgroundResource(R.drawable.songlvlmax);
			break;
		default:
			levelImage.setBackgroundResource(R.drawable.songlvlnew);
			break;
		}
		gallery.invalidate();
		gallery.refreshDrawableState();
		galleryAdapter = new ImageAdapter(this);
        gallery.setAdapter(galleryAdapter);
    	gallery.setSelection(CurrentLevel - 1);
    	super.onResume();
    }
}

