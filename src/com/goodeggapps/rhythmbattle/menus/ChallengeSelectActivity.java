package com.goodeggapps.rhythmbattle.menus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.goodeggapps.rhythmbattle.R;
import com.goodeggapps.rhythmbattle.game.GameActivity;
import com.google.ads.AdView;

public class ChallengeSelectActivity extends Activity {

    public static boolean ULSecretLevel;
    public static SharedPreferences settings;
    public static Integer level1;
    public static Integer level2;
    public static Integer level3;
    public static Integer level4;
    public static Integer level5;
    public static Integer upgradeLocked;
    public static Integer Challenge;
    public static Intent game;
    public static int Level1Score;
    public static int Level2Score;
    public static int Level3Score;
    public static int Level4Score;
    public static int Level5Score;
    public static int CurrentLevel;
    public static int ULDevTip;
    public static int LevelSelected;
	public static boolean ULUnlockAll;
	public static boolean Challenge11;
	public static boolean Challenge12;
	public static boolean Challenge13;
	public static boolean Challenge21;
	public static boolean Challenge22;
	public static boolean Challenge23;
	public static boolean Challenge31;
	public static boolean Challenge32;
	public static boolean Challenge33;
	public static boolean Challenge41;
	public static boolean Challenge42;
	public static boolean Challenge43;
	public static boolean Challenge51;
	public static boolean Challenge52;
	public static boolean Challenge53;
	public static File file;
    public static Gallery gallery;
    public static Button bionicButton;
    public static Button sonicButton;
    public static Button harmonicButton;
    public static Button ringtoneButton; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challengeselector);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("FirstChallenge", true);
        if (firstrun){
        	getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
        	.putBoolean("FirstChallenge", false)
            .commit();
			game = new Intent(ChallengeSelectActivity.this, ChallengeHelpActivity.class);
			startActivity(game);
        	
        }

        getSettings();
        
        if (ULDevTip > 0) {
            AdView adView = (AdView) findViewById(R.id.adView);
            adView.setVisibility(View.INVISIBLE);
            }

        level1 = R.drawable.piracysmall;
        level2 = R.drawable.japansmall;
        level3 = R.drawable.indiasmall;
        level4 = R.drawable.medievalsmall;
        level5 = R.drawable.spacesmall;
        LevelSelected = 1;
        
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        ringtoneButton = (Button) findViewById(R.id.ringtoneButton);
    	ringtoneButton.setBackgroundResource(R.drawable.ringtonelocked);
    	if (Challenge11 == true && Challenge12 == true && Challenge13 == true) {
    		ringtoneButton.setBackgroundResource(R.drawable.piracyunlocked);
    		ringtoneButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SaveRingtone();
			}
		});

    	}
        bionicButton = (Button) findViewById(R.id.bionicButton);
        if (Challenge11 == false) {
        	bionicButton.setBackgroundResource(R.drawable.bionic);
        } else {
        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
        }

        sonicButton = (Button) findViewById(R.id.sonicButton);
        if (Challenge12 == false) {
        	sonicButton.setBackgroundResource(R.drawable.sonic);
        } else {
        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
        }
        
        harmonicButton = (Button) findViewById(R.id.harmonicButton);
        if (Challenge13 == false) {
        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
        } else {
        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
        }

        
        Typeface font = Typeface.createFromAsset(getAssets(),"font/pf_tempesta_five.ttf");
        bionicButton.setTypeface(font);
		bionicButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
			        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("ChallengeLevel", 1)
		        	.commit();
					game = new Intent(ChallengeSelectActivity.this, GameActivity.class);
					startActivity(game);
			}
		});
	                	
        sonicButton.setTypeface(font);
		sonicButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
			        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("ChallengeLevel", 2)
		        	.commit();
					game = new Intent(ChallengeSelectActivity.this, GameActivity.class);
					startActivity(game);
			}
		});
        	
        harmonicButton.setTypeface(font);
		harmonicButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
			        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("ChallengeLevel", 3)
		        	.commit();
					game = new Intent(ChallengeSelectActivity.this, GameActivity.class);
					startActivity(game);
			}
		});

        
        
        
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
            	switch(arg2) {
            	case 0:
                    LevelSelected = 1;
			        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		            .edit()
		        	.putInt("CurrentLevel", 1)
		        	.commit();
			        if (Challenge11 == false) {
			        	bionicButton.setBackgroundResource(R.drawable.bionic);
			        } else {
			        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
			        }

			        if (Challenge12 == false) {
			        	sonicButton.setBackgroundResource(R.drawable.sonic);
			        } else {
			        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
			        }
			        
			        if (Challenge13 == false) {
			        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
			        } else {
			        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
			        }

    			    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("CurrentLevel", 1).commit();
			    	if (Challenge11 == true && Challenge12 == true && Challenge13 == true || ULUnlockAll == true) {
			    		ringtoneButton.setBackgroundResource(R.drawable.piracyunlocked);
			    		ringtoneButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								SaveRingtone();
						}
					});}

			        break;

            	case 1:
                    LevelSelected = 2;
			        if (Challenge21 == false) {
			        	bionicButton.setBackgroundResource(R.drawable.bionic);
			        } else {
			        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
			        }

			        if (Challenge22 == false) {
			        	sonicButton.setBackgroundResource(R.drawable.sonic);
			        } else {
			        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
			        }
			        
			        if (Challenge23 == false) {
			        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
			        } else {
			        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
			        }
    			    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
    		        .edit()
    		      	.putInt("CurrentLevel", 2)
    		       	.commit();
			    	if (Challenge21 == true && Challenge22 == true && Challenge23 == true || ULUnlockAll == true) {
			    		ringtoneButton.setBackgroundResource(R.drawable.japanunlocked);
			    		ringtoneButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								SaveRingtone();
						}
					});} else {
						ringtoneButton.setBackgroundResource(R.drawable.ringtonelocked);
			    		ringtoneButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
						}
					});}

    			    break;
            	case 2:
                    LevelSelected = 3;
    			    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
    		        .edit()
    		       	.putInt("CurrentLevel", 3)
    		       	.commit();
			        if (Challenge31 == false) {
			        	bionicButton.setBackgroundResource(R.drawable.bionic);
			        } else {
			        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
			        }

			        if (Challenge32 == false) {
			        	sonicButton.setBackgroundResource(R.drawable.sonic);
			        } else {
			        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
			        }
			        
			        if (Challenge33 == false) {
			        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
			        } else {
			        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
			        }
			    	if (Challenge31 == true && Challenge32 == true && Challenge33 == true || ULUnlockAll == true) { 
			    		ringtoneButton.setBackgroundResource(R.drawable.indiaunlocked);
			    		ringtoneButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								SaveRingtone();
						}
						});} else {
							ringtoneButton.setBackgroundResource(R.drawable.ringtonelocked);
				    		ringtoneButton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
							}
						});}
			        break;
            	case 3:
                    LevelSelected = 4;
    			    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
    		        .edit()
    		       	.putInt("CurrentLevel", 4)
    		       	.commit();
			        if (Challenge41 == false) {
			        	bionicButton.setBackgroundResource(R.drawable.bionic);
			        } else {
			        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
			        }

			        if (Challenge42 == false) {
			        	sonicButton.setBackgroundResource(R.drawable.sonic);
			        } else {
			        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
			        }
			        
			        if (Challenge43 == false) {
			        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
			        } else {
			        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
			        }
			    	if (Challenge41 == true && Challenge42 == true && Challenge43 == true || ULUnlockAll == true) {
			    		ringtoneButton.setBackgroundResource(R.drawable.rainshineunlocked);
			    		ringtoneButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								SaveRingtone();
						}
						});} else {
							ringtoneButton.setBackgroundResource(R.drawable.ringtonelocked);
				    		ringtoneButton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
							}
						});}
			        break;
            	case 4:
                    LevelSelected = 5;
    			    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
    		        .edit()
    		       	.putInt("CurrentLevel", 5)
    		       	.commit();
			        if (Challenge51 == false) {
			        	bionicButton.setBackgroundResource(R.drawable.bionic);
			        } else {
			        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
			        }

			        if (Challenge52 == false) {
			        	sonicButton.setBackgroundResource(R.drawable.sonic);
			        } else {
			        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
			        }
			        
			        if (Challenge53 == false) {
			        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
			        } else {
			        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
			        }
			    	if (Challenge51 == true && Challenge52 == true && Challenge53 == true || ULUnlockAll == true) {
			    		ringtoneButton.setBackgroundResource(R.drawable.spaceunlocked);
			    		ringtoneButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								SaveRingtone();
						}
						});} else {
							ringtoneButton.setBackgroundResource(R.drawable.ringtonelocked);
				    		ringtoneButton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
							}
						});}
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

    private void SaveRingtone() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

		File path = Environment.getExternalStoragePublicDirectory(
			    Environment.DIRECTORY_RINGTONES);
		
			path.mkdirs(); // Ensure the directory exists
			switch (LevelSelected) {
				case 1:
					copyAssets(R.raw.oceanringtone);
					break;
				case 2:
					copyAssets(R.raw.japanringtone);
					break;
				case 3:
					copyAssets(R.raw.indiaringtone);
					break;
				case 4:
					copyAssets(R.raw.rainbitlevelringtone);
					break;
				case 5:
					copyAssets(R.raw.spaceringtone);
					break;
				default:
					copyAssets(R.raw.oceanringtone);
					break;
			}
		
        } else {
        	Toast.makeText(getApplicationContext(), "Installation Failed!  Installing ringtones requires SD card!", Toast.LENGTH_LONG).show();
        }
    }


    

    
    public boolean copyAssets(int ressound){  
    	 byte[] buffer=null;  
    	 InputStream fIn = getBaseContext().getResources().openRawResource(ressound);  
    	 int size=0;  
    	 String songTitle = "Indian Special";
    	 String fileName = "indianspecial";
    	 
    	 try {  
    	  size = fIn.available();  
    	  buffer = new byte[size];  
    	  fIn.read(buffer);  
    	  fIn.close();  
    	 } catch (IOException e) {  
    	  return false;  
    	 }  
    	  
    	 String path="/sdcard/media/audio/ringtones/";  
    	 switch (LevelSelected) {
    		 case 1:
    	    	 songTitle = "Piracy";
    	    	 fileName = "piracyringtone";
    			 break;
    		 case 2:
    	    	 songTitle = "Arashi Garden";
    	    	 fileName = "japanringtone";
    			 break;
    		 case 3:
    	    	 songTitle = "Mandira";
    	    	 fileName = "indianspecial";
    			 break;
    		 case 4:
    	    	 songTitle = "RAINSHINE";
    	    	 fileName = "rainshine";
    			 break;
    		 case 5:
    	    	 songTitle = "Hostile Void";
    	    	 fileName = "hostilevoid";
    			 break;
    	 }
    	 String filename= fileName +".mp3";  
    	 boolean exists = (new File(path)).exists();  
    	 if (!exists){new File(path).mkdirs();}  
    	  
    	 FileOutputStream save;  
    	 try {  
    	  save = new FileOutputStream(path+filename);  
    	  save.write(buffer);  
    	  save.flush();  
    	  save.close();  
     	  Toast.makeText(getApplicationContext(), "" + songTitle + " Ringtone Installed!  You can find it via the Settings application!", Toast.LENGTH_LONG).show();
    	 } catch (FileNotFoundException e) {  
    	  return false;  
    	 } catch (IOException e) {  
    	  return false;  
    	 }      
    	  
    	 sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+path+filename)));  
    	  
    	 File k = new File(path, filename);  
    	  
    	 ContentValues values = new ContentValues();  
    	 values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());  
    	 values.put(MediaStore.MediaColumns.TITLE, songTitle);  
    	 values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");  
    	 values.put(MediaStore.Audio.Media.ARTIST, "Rhythm Battle");  
    	 values.put(MediaStore.Audio.Media.IS_RINGTONE, true);  
    	 values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);  
    	 values.put(MediaStore.Audio.Media.IS_ALARM, true);  
    	 values.put(MediaStore.Audio.Media.IS_MUSIC, false);  
    	  
    	 //Insert it into the database
    	 
    	 Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
    	 getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + k.getAbsolutePath() + "\"", null);
    	 
    	 
    	 this.getContentResolver().insert(MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath()), values);  
    	  
    	 return true;  
    	}  


    

    public void onResume() {
        super.onResume();
        getSettings();
    	gallery.setSelection(CurrentLevel - 1);
    	setBackground();
    	
    }
    
    public void setBackground() {
    	boolean challengeX1 = settings.getBoolean("Challenge" + CurrentLevel + "1", false);
    	boolean challengeX2 = settings.getBoolean("Challenge" + CurrentLevel + "2", false);
    	boolean challengeX3 = settings.getBoolean("Challenge" + CurrentLevel + "3", false);
        if (challengeX1 == false) {
        	bionicButton.setBackgroundResource(R.drawable.bionic);
        } else {
        	bionicButton.setBackgroundResource(R.drawable.bioniccomplete);
        }

        if (challengeX2 == false) {
        	sonicButton.setBackgroundResource(R.drawable.sonic);
        } else {
        	sonicButton.setBackgroundResource(R.drawable.soniccomplete);
        }
        
        if (challengeX3 == false) {
        	harmonicButton.setBackgroundResource(R.drawable.harmonic);
        } else {
        	harmonicButton.setBackgroundResource(R.drawable.harmoniccomplete);
        }
        ringtoneButton = (Button) findViewById(R.id.ringtoneButton);
    	if (challengeX1 == true && challengeX2 == true && challengeX3 == true || ULUnlockAll == true) {
    		ringtoneButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SaveRingtone();
			}
			});

    		
    		switch (CurrentLevel) {
    		case 1:
    			ringtoneButton.setBackgroundResource(R.drawable.piracyunlocked);
    			break;
    		case 2:
    			ringtoneButton.setBackgroundResource(R.drawable.japanunlocked);
    			break;
    		case 3: 
    			ringtoneButton.setBackgroundResource(R.drawable.indiaunlocked);
    			break;
    		case 4:
    			ringtoneButton.setBackgroundResource(R.drawable.rainshineunlocked);
    			break;
    		case 5:
    			ringtoneButton.setBackgroundResource(R.drawable.spaceunlocked);
    			break;
    		default:

        		break;
    		}
    	} else {
				ringtoneButton.setBackgroundResource(R.drawable.ringtonelocked);
	    		ringtoneButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
				}
			});
    		}
    }    
    
    public void getSettings(){
        settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        Challenge11 = settings.getBoolean("Challenge11", false);  
        Challenge12 = settings.getBoolean("Challenge12", false);
        Challenge13 = settings.getBoolean("Challenge13", false);
        Challenge21 = settings.getBoolean("Challenge21", false);
        Challenge22 = settings.getBoolean("Challenge22", false);
        Challenge23 = settings.getBoolean("Challenge23", false);
        Challenge31 = settings.getBoolean("Challenge31", false);
        Challenge32 = settings.getBoolean("Challenge32", false);
        Challenge33 = settings.getBoolean("Challenge33", false);
        Challenge41 = settings.getBoolean("Challenge41", false);
        Challenge42 = settings.getBoolean("Challenge42", false);
        Challenge43 = settings.getBoolean("Challenge43", false);
        Challenge51 = settings.getBoolean("Challenge51", false);
        Challenge52 = settings.getBoolean("Challenge52", false);
        Challenge53 = settings.getBoolean("Challenge53", false);
        ULDevTip = settings.getInt("ULDevTip", 0);
        CurrentLevel = settings.getInt("CurrentLevel", 0);
        ULUnlockAll = settings.getBoolean("ULUnlockAll", false);
    }

    
}

        

