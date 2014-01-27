package com.goodeggapps.rhythmbattle.game;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.io.IOException;
import java.util.ArrayList;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.collision.BaseCollisionChecker;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.AlphaModifier;
import org.anddev.andengine.entity.shape.modifier.DelayModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.shape.modifier.ease.EaseBounceInOut;
import org.anddev.andengine.entity.shape.modifier.ease.EaseElasticInOut;
import org.anddev.andengine.entity.shape.modifier.ease.EaseExponentialInOut;
import org.anddev.andengine.entity.shape.modifier.ease.EaseLinear;
import org.anddev.andengine.entity.shape.modifier.ease.EaseSineInOut;
import org.anddev.andengine.entity.shape.modifier.ease.EaseStrongIn;
import org.anddev.andengine.entity.shape.modifier.ease.EaseStrongInOut;
import org.anddev.andengine.entity.shape.modifier.ease.IEaseFunction;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.builder.BlackPawnTextureBuilder;
import org.anddev.andengine.opengl.texture.builder.ITextureBuilder.TextureSourcePackingException;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.Path;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.goodeggapps.rhythmbattle.R;
import com.goodeggapps.rhythmbattle.game.background.VerticalParallaxEntity;
import com.goodeggapps.rhythmbattle.game.effects.EscapedNote;
import com.goodeggapps.rhythmbattle.game.effects.Explosion;
import com.goodeggapps.rhythmbattle.game.enemies.EnemyBullet;
import com.goodeggapps.rhythmbattle.game.enemies.EnemyShip;
import com.goodeggapps.rhythmbattle.game.enemies.FriendlyShip;
import com.goodeggapps.rhythmbattle.game.level.Level;
import com.goodeggapps.rhythmbattle.game.options.Options;
import com.goodeggapps.rhythmbattle.game.player.Bullet;
import com.goodeggapps.rhythmbattle.game.player.PlayerShip;
import com.goodeggapps.rhythmbattle.game.player.Upgrade;

public class GameActivity extends BaseGameActivity {

	// CONSTANTS
	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 800;
	public static final int ANIMATION_FRAMELENGTH = 100;

	// CAMERA
	private Camera camera;

	// TEXTURES AND FONTS
	private BuildableTexture mBuildableTexture;
	private static TiledTextureRegion mShipTextureRegion;
	private static TiledTextureRegion mEnemyTextureRegion;
	private static TiledTextureRegion mFriendlyTextureRegion;
	public static TextureRegion mBulletTextureRegion;
	public static TextureRegion mSpikeTextureRegion;
	public static TextureRegion mEnemyBulletTextureRegion;
	private static TiledTextureRegion mExplosionTextureRegion;
	public static TextureRegion mEscapedNoteTextureRegion;
	private Texture font_texture;
	private Font font;

	// SOUNDS
	private Music music1;
	private Music music2;
	private Music music3;
	private Music music4;
	private Music music5;

	// OBJECTS
	private PlayerShip ship;
	
	public static ArrayList<EnemyShip> enemies;
	public static ArrayList<EnemyShip> enemiesToReuse;
	public static ArrayList<FriendlyShip> friendlies;
	public static ArrayList<FriendlyShip> friendliesToReuse;
	public static ArrayList<Explosion> explosions;
	public static ArrayList<Explosion> explosionsToReuse;
	public static ArrayList<EscapedNote> escapedNotes;
	public static ArrayList<EscapedNote> escapedNotesToReuse;
	public static ArrayList<Upgrade> upgrades;
	public static ArrayList<Upgrade> upgradesToReuse;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Bullet> bulletsToReuse;
	public static ArrayList<EnemyBullet> enemyBullets;
	public static ArrayList<EnemyBullet> enemyBulletsToReuse;
    public static SharedPreferences settings;

	// PATHS
	public static final Path[] paths = new Path[5];

	// Initlaize Upgrade and Song Level Related Variables 
	public static int songPoints;
	public static int pointsForNextLevel;
	public static int songLevel;
	public static int startingLevel;
	public static int totalNotesMissed;
	public static int HighestSongLevel;
	public static int LaserCountUp;
	public static int UpgradeCountUp;
	public static int DamageNumber;
	public static float BeatFrequency;
	public static int PointsToNextLevel;
	public static int PointsPerLevel;
	public static int PointsForLastLevel;
	public static int CurrentLevel;
	public static int ChallengeLevel;
	public static int ShieldUnlock;
	public static int SpikeUnlock;
	public static int TotalEnemies;
	public static boolean LaserPenetrate;
	public static boolean noteMissed;
	public static int SpikyUpgrade;
	public static int LaserFrequency;
	public static boolean isGameOver;
	public static boolean isGameReady;
	public static boolean isBossFight;
	public static int PreviousSongLevel;
	public static float EnemySpeed;
	public static int CurrentUpgrade;
	public static int UpgradeCount;
	public static int LaserAttackPowerUp;
	public static IEaseFunction enemyModifier;
	
	// SHARED PREFERENCES
	public static Options options;
	public static Options CurrentSongPoints;
		
	// Health Bar
	private static TextView scoreView;
	private static Rectangle healthbar;
	private static Rectangle currentProgress;
	
	// LEVEL
	private Level level;
	
	// AutoParallaxBackground
	private static Texture mAutoParallaxBackgroundTexture;
	private static Texture mAutoParallaxBackgroundTextureBack;
	private static TextureRegion mParallaxLayerBack;
	private static TextureRegion mParallaxLayerClouds;
	public static AutoParallaxBackground autoParallaxBackground;
	public static VerticalParallaxEntity mParallaxBack;
	public static VerticalParallaxEntity mParallaxFront;
	private static TiledTextureRegion mUpgradeTextureRegion;

	
	// FADE
	private Rectangle fade;
	
	@Override
	protected void onSetContentView() {
		
		final RelativeLayout relativeLayout = new RelativeLayout(this);
		final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(FILL_PARENT, FILL_PARENT);
		
		scoreView = new TextView(getApplicationContext());
		Typeface font = Typeface.createFromAsset(getAssets(),"font/pf_tempesta_five.ttf");
		scoreView.setTypeface(font);
		
		scoreView.setPadding(30, 82, 0, 0);
		
		scoreView.setGravity(Gravity.CENTER);
		
		this.mRenderSurfaceView = new RenderSurfaceView(this, this.mEngine);
		this.mRenderSurfaceView.applyRenderer();

		final LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(super.createSurfaceViewLayoutParams());
		surfaceViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
		relativeLayout.addView(scoreView, this.createAdViewLayoutParams());
		
		this.setContentView(relativeLayout, relativeLayoutLayoutParams);
	}

	private LayoutParams createAdViewLayoutParams() {
		final LayoutParams adViewLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		adViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adViewLayoutParams.addRule(RelativeLayout.ALIGN_LEFT);
		return adViewLayoutParams;
	}

	@Override
	public Engine onLoadEngine() {
		//sets up the engine
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);

			engineOptions.setNeedsMusic(true);

		return new Engine(engineOptions);
     	
	}

	@Override
	public void onLoadResources() {

		//initialize everything here
		settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        CurrentLevel = settings.getInt("CurrentLevel", 1);
        ChallengeLevel = settings.getInt("ChallengeLevel", 0);
    	ShieldUnlock = settings.getInt("ULShield", 0);
    	SpikeUnlock = settings.getInt("ULSpikes", 0);
		settings.edit().putBoolean("SongLevelUp", false).commit();
		LaserFrequency = 8;
		noteMissed = false;
		DamageNumber = 5;

		if (CurrentLevel < 5) {
			scoreView.setTextColor(Color.BLACK);
		} else {
			scoreView.setTextColor(Color.WHITE);
		}
		DamageNumber = DamageNumber - ShieldUnlock;
		SpikyUpgrade = SpikeUnlock;
		
		TotalEnemies = 0;
		songPoints = 0;
		totalNotesMissed = 0;
		LaserCountUp = 0;
		UpgradeCountUp = 0;
		PreviousSongLevel = 0;
		PointsForLastLevel = 0;
		EnemySpeed = 5;
		LaserPenetrate = false;
		LaserAttackPowerUp = 0;
		CurrentUpgrade = (int) (Math.random() * 3) + 1;
		UpgradeCount = (int) (Math.random() * 100) + 80;

		
		//sets up textures
		this.mBuildableTexture = new BuildableTexture(512, 512, TextureOptions.DEFAULT);
		TextureRegionFactory.setAssetBasePath("img/");
		GameActivity.mShipTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "penguinship.png", 4, 1);
		GameActivity.mEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "enemy2.png", 4, 1);
		if (ChallengeLevel == 3) GameActivity.mFriendlyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "enemy1.png", 4, 1);
		GameActivity.mExplosionTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "explosion2.png", 4, 2);
		GameActivity.mBulletTextureRegion = TextureRegionFactory.createFromAsset(this.mBuildableTexture, this, "meci.png");
		GameActivity.mSpikeTextureRegion = TextureRegionFactory.createFromAsset(this.mBuildableTexture, this, "spikeset.png");
		GameActivity.mEscapedNoteTextureRegion = TextureRegionFactory.createFromAsset(this.mBuildableTexture, this, "escapednote.png");
		GameActivity.mAutoParallaxBackgroundTexture = new Texture(512, 1024, TextureOptions.DEFAULT);
		GameActivity.mAutoParallaxBackgroundTextureBack = new Texture(512, 1024, TextureOptions.DEFAULT);
		autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);

        // level 1: ocean level 2: japan level 3: india level 4: 8bit level 5:space
		if (CurrentLevel == 1) {  //Pirate Level
			BeatFrequency = 0.325f;
			PointsPerLevel = 6;
			EnemySpeed = 9;
			mParallaxLayerBack = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTextureBack, this, "seabackground.png", 0, 0);
			mParallaxLayerClouds = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTexture, this, "cloudbackground.png", 0, 0);
			PreviousSongLevel = settings.getInt("SongLevel1", 0);
	    	enemyModifier = EaseLinear.getInstance();
		} else if (CurrentLevel == 2) { //Japanese Level
			BeatFrequency = 0.30f;
			PointsPerLevel = 9;
			EnemySpeed = 8;
			mParallaxLayerBack = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTextureBack, this, "japanbackground.png", 0, 0);
			mParallaxLayerClouds = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTexture, this, "cloudbackground.png", 0, 0);
			PreviousSongLevel = settings.getInt("SongLevel2", 0);
	    	enemyModifier = EaseExponentialInOut.getInstance();
		} else if (CurrentLevel == 3) {
			BeatFrequency = 0.45f;
			PointsPerLevel = 8;
			EnemySpeed = 10;
			mParallaxLayerBack = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTextureBack, this, "indiabackground.png", 0, 0);
			mParallaxLayerClouds = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTexture, this, "cloudbackground.png", 0, 0);
			PreviousSongLevel = settings.getInt("SongLevel3", 0);
	    	enemyModifier = EaseStrongInOut.getInstance();
		} else if (CurrentLevel == 4) {
			BeatFrequency = 0.37f;
			PointsPerLevel = 11;
			EnemySpeed = 13;
			PreviousSongLevel = settings.getInt("SongLevel4", 0);
			mParallaxLayerBack = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTextureBack, this, "medievalbackground.png", 0, 0);
			mParallaxLayerClouds = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTexture, this, "cloudbackground.png", 0, 0);
			enemyModifier = EaseElasticInOut.getInstance();
		} else if (CurrentLevel == 5) {
			BeatFrequency = 0.40f;
			PointsPerLevel = 8;
			EnemySpeed = 8;
			mParallaxLayerBack = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTextureBack, this, "spacebackground.png", 0, 0);
			mParallaxLayerClouds = TextureRegionFactory.createFromAsset(GameActivity.mAutoParallaxBackgroundTexture, this, "spaceforeground.png", 0, 0);
			PreviousSongLevel = settings.getInt("SongLevel5", 0);
	    	enemyModifier = EaseBounceInOut.getInstance();
		}
		
		songLevel = startingLevel;
		PointsToNextLevel = (startingLevel * PointsPerLevel) + PointsPerLevel;
		LaserFrequency = LaserFrequency - PreviousSongLevel;
		if (LaserFrequency <= 4){
			LaserFrequency = 4;
		}

		switch (CurrentUpgrade){
		case 1:
			GameActivity.mUpgradeTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "upgradespeed.png", 4, 1);
			break;
		case 2:
			GameActivity.mUpgradeTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "upgradepiercing.png", 4, 1);
			break;
		case 3:
			GameActivity.mUpgradeTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "upgradespikes.png", 4, 1);
			break;
		default:
			GameActivity.mUpgradeTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "upgradespeed.png", 4, 1);
			CurrentUpgrade = 1;
			break;
		}
		try {
			this.mBuildableTexture.build(new BlackPawnTextureBuilder());
		} catch (final TextureSourcePackingException e) {
			Debug.e(e);
		}
		
		if (ChallengeLevel == 1) {
			LaserFrequency = 4;
			SpikyUpgrade = 0;
			LaserPenetrate = true;
			EnemySpeed = (float) (EnemySpeed * 0.7);
			UpgradeCount = 9999;
		}
		if (ChallengeLevel == 2) {
			LaserFrequency = 9999;
			SpikyUpgrade = 2;
			UpgradeCount = 9999;
		}
		if (ChallengeLevel == 3) {
			LaserFrequency = 4;
			UpgradeCount = 9999;
		}

		
		this.mEngine.getTextureManager().loadTextures(GameActivity.mAutoParallaxBackgroundTexture);
		this.mEngine.getTextureManager().loadTextures(GameActivity.mAutoParallaxBackgroundTextureBack);
		mParallaxBack = new VerticalParallaxEntity(6f, new Sprite(0, 0, GameActivity.mParallaxLayerBack));
		mParallaxFront = new VerticalParallaxEntity(15f, new Sprite(0, 0, GameActivity.mParallaxLayerClouds));
		FontFactory.setAssetBasePath("font/");
		font_texture = new Texture(256, 256, TextureOptions.BILINEAR);
		font = FontFactory.createFromAsset(font_texture, this,"pf_tempesta_five.ttf", 60, true, Color.WHITE);
				
		this.mEngine.getTextureManager().loadTextures(mBuildableTexture, font_texture, font_texture);
		this.mEngine.getFontManager().loadFont(font);

			MusicFactory.setAssetBasePath("snd/");

	        // level 1: ocean level 2: japan level 3: india level 4: 8bit level 5:space
			try {
				if (CurrentLevel == 1) {
					this.music1 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "ocean1.ogg");
					this.music2 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "ocean2.ogg");
					this.music3 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "ocean3.ogg");
					this.music4 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "ocean4.ogg");
					this.music5 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "ocean5.ogg");
					this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
				}
				else if (CurrentLevel == 2) {
					this.music1 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "japan1.ogg");
					this.music2 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "japan2.ogg");
					this.music3 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "japan3.ogg");
					this.music4 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "japan4.ogg");
					this.music5 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "japan5.ogg");
					this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
				}
				else if (CurrentLevel == 3) {
					this.music1 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "india1.ogg");
					this.music2 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "india2.ogg");
					this.music3 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "india3.ogg");
					this.music4 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "india4.ogg");
					this.music5 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "india5.ogg");
					this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
				}
				else if (CurrentLevel == 4) {
					this.music1 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "rainshine1.ogg");
					this.music2 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "rainshine2.ogg");
					this.music3 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "rainshine3.ogg");
					this.music4 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "rainshine4.ogg");
					this.music5 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "rainshine5.ogg");
					this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
				}
				else if (CurrentLevel == 5) {
					this.music1 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "space1.ogg");
					this.music2 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "space2.ogg");
					this.music3 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "space3.ogg");
					this.music4 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "space4.ogg");
					this.music5 = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "space5.ogg");
					this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
				}

				
			} catch (final IOException e) {
				Debug.e("Error", e);
			}
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		mEngine.stop();
		gameInit();

		loadScene();
        
		level.getScene().setOnSceneTouchListener(new IOnSceneTouchListener() { 
			@Override 
			public boolean onSceneTouchEvent( 
					final Scene pScene, TouchEvent pSceneTouchEvent) {
					int eventaction = pSceneTouchEvent.getAction(); 
		        switch (eventaction) { 
		        	case MotionEvent.ACTION_DOWN: break;
		        	case MotionEvent.ACTION_MOVE:   
		        		if (isGameReady == true){
		        	        ship.setPosition((float) ((pSceneTouchEvent.getX())- ship.getWidth()/2), (float) ((pSceneTouchEvent.getY()) - ship.getWidth()/2));

		        	        break;
		        		}
		        	case MotionEvent.ACTION_UP: break; 
		        } 
		        return true; 
		}
		});
		return level.getScene();
	}
	
	private void LevelFinished(){
		fade.addShapeModifier(new SequenceModifier(new IShapeModifierListener() {
			
			@Override
			public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
				
				
				mEngine.clearUpdateHandlers();
				level.getScene().clearUpdateHandlers();
				level.getScene().clearChildScene();
				level.getScene().clearTouchAreas();
				
				if (ChallengeLevel == 3) friendliesToReuse.clear();
				bulletsToReuse.clear();
				enemiesToReuse.clear();
				enemyBulletsToReuse.clear();
				explosionsToReuse.clear();
				if (ChallengeLevel == 0){
				if (songLevel > PreviousSongLevel)
				{
					settings.edit().putInt("SongLevel" + CurrentLevel, songLevel)
					.putBoolean("SongLevelUp", true).commit();
				}
				}
				settings.edit()
				.putInt("CurrentLevelED", TotalEnemies - totalNotesMissed) //Enemies Defeated
	        	.putInt("CurrentLevelSL", songLevel) //Song Level
	        	.putInt("TotalEnemies", TotalEnemies)
	        	.putBoolean("NoteMissed", noteMissed)
	        	.commit();
				
				Intent results = new Intent(GameActivity.this, ResultsActivity.class);
				startActivity(results);
				finish();
				
			}
		},new AlphaModifier(0.5f, 0, 1)));
	} //LevelFinished
	
	public void loadScene(){
		isGameOver = false;
		isGameReady = false;
		autoParallaxBackground.addParallaxEntity(mParallaxBack);
		autoParallaxBackground.addParallaxEntity(mParallaxFront);
		level.getScene().setBackground(autoParallaxBackground);
		level.getScene().getTopLayer().addEntity(currentProgress);
		level.getScene().getTopLayer().addEntity(healthbar);
		level.getScene().getTopLayer().addEntity(fade);
		level.getScene().registerUpdateHandler(new TimerHandler(((20-CurrentLevel)/5), new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				
				if (isGameReady == true){
					if(level.getScene().getWaves().getCurrentWave()==null){
						if(enemies.size()==0){
							gameLevelCleared();
							isGameReady = false;
						}
					}else{
						for(int i=0;i<level.getScene().getWaves().getCurrentWave().size();i++){
							if(level.getScene().getWaves().getCurrentWave().getType(i).equals("ship")){
								for(int j=0;j<level.getScene().getWaves().getCurrentWave().getCount(i);j++){
									createEnemyShip(paths[j]);
									TotalEnemies++;
									if (noteMissed == true) {
										if (ChallengeLevel == 1) gameChallengeFailed();
										noteMissed = false;
									}
									if (ChallengeLevel == 1) {
										createEnemyShip(paths[j]);
									}
									if (ChallengeLevel == 3) {
										
										if ((int)(Math.random() * 10) == 7){
											createFriendlyShip(paths[j]);
										}
									}
								}
							}
							if(0 == level.getScene().getWaves().getCurrentWave().getCount(i)){
							}
						}
						level.getScene().getWaves().next();
					}
					
				}
			}
		}));

		level.getScene().registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				checkCollisions();
			}
		});

		ship.setPosition(CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth() / 2, CAMERA_HEIGHT + 300);
		ship.addToScene(level.getScene());
		//fires the laser and spawns some of the enemies based on upgrades

		level.getScene().registerUpdateHandler(new TimerHandler((BeatFrequency / 2), new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				if (isGameReady == true)
				{
					if (LaserCountUp >= LaserFrequency) {
						LaserCountUp = 0;
						ship.fire();				
					}							

					if (LaserAttackPowerUp > 0) {
						if (LaserCountUp >= (LaserFrequency / 2)) {
							LaserCountUp = 0;
							LaserAttackPowerUp--;
							ship.fire();
						}							
					}
					LaserCountUp++;
					UpgradeCountUp++;
					if (UpgradeCountUp == UpgradeCount) ReleaseUpgrade();
					if (songLevel >= 1)	music2.setVolume(1);
					if (songLevel >= 2)	music3.setVolume(1);
					if (songLevel >= 3)	music4.setVolume(1);
					if (songLevel >= 4)	music5.setVolume(1);
				}
			}

		}));
	}  //LoadScene

	@Override
	public void onLoadComplete() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				scoreView.setText("Tracks Rescued: 0");	
			}
		});

		music1.play();
		music1.setVolume(0);
		music2.play();
		music2.setVolume(0);
		music3.play();
		music3.setVolume(0);
		music4.play();
		music4.setVolume(0);
		music5.play();
		music5.setVolume(0);
		mEngine.start();

		fade.addShapeModifier(new SequenceModifier(new IShapeModifierListener() {
			@Override
			public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
				gameGetReady();
			}
		},new DelayModifier(1), new AlphaModifier(0.5f, 1, 0)));				

	}  //onLoadComplete

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (isGameReady == true) {
			
				if (pKeyCode == KeyEvent.KEYCODE_DPAD_LEFT
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(-100, 0);
					return true;
				} else if (pKeyCode == KeyEvent.KEYCODE_DPAD_RIGHT
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(100, 0);
					return true;
				} else if (pKeyCode == KeyEvent.KEYCODE_DPAD_UP
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(0, -100);
					return true;
				} else if (pKeyCode == KeyEvent.KEYCODE_DPAD_DOWN
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(0, 100);
					return true;
				}
		}

		if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			showExitDialog();
			return true;
		}
		
		return super.onKeyDown(pKeyCode, pEvent);
	}  //onKeyDown -brings up exit menu

	public void gameInit() {
		
		enemies = new ArrayList<EnemyShip>();
		enemiesToReuse = new ArrayList<EnemyShip>();

		if (ChallengeLevel == 3) {
			friendlies = new ArrayList<FriendlyShip>();
			friendliesToReuse = new ArrayList<FriendlyShip>();
		}
		
		bullets = new ArrayList<Bullet>();
		bulletsToReuse = new ArrayList<Bullet>();

		enemyBullets = new ArrayList<EnemyBullet>();
		enemyBulletsToReuse = new ArrayList<EnemyBullet>();

		escapedNotes = new ArrayList<EscapedNote>();
		escapedNotesToReuse = new ArrayList<EscapedNote>();

		upgrades = new ArrayList<Upgrade>();
		upgradesToReuse = new ArrayList<Upgrade>();
		
		explosions = new ArrayList<Explosion>();
		explosionsToReuse = new ArrayList<Explosion>();

		paths[0] = new Path(7).to(50, -60).to(50, 150).to(400, 150).to(300, 250).to(400, 500).to(150, 400).to(400, 850);
		paths[1] = new Path(7).to(100, -60).to(250, 100).to(100, 400).to(300, 300).to(400, 400).to(40, 100).to(300, 850);
		paths[2] = new Path(7).to(300, -60).to(400, 120).to(300, 100).to(200, 400).to(200, 320).to(150, 300).to(10, 850);
		paths[3] = new Path(7).to(400, -60).to(200, 50).to(50, 300).to(300, 400).to(150, 400).to(300, 200).to(100, 850);
		paths[4] = new Path(7).to(400, -60).to(400, 100).to(400, 500).to(300, 500).to(150, 500).to(30, 500).to(300, 850);

		level = new Level(this, CurrentLevel);
		fade = new Rectangle(0, 0, 480, 800);
		fade.setColor(0, 0, 0);
		fade.setAlpha(1);
		
		
		currentProgress = new Rectangle(310, 30, 140, 18);
		currentProgress.setColor(0.1f, 0.1f, 0.1f);
		
		healthbar = new Rectangle(314, 34, 132, 10);
		healthbar.setColor(0, 0, 1);
		healthbar.setWidth(0);
		if (ChallengeLevel > 0) {
			healthbar.setVisible(false);
			currentProgress.setVisible(false);
			scoreView.setVisibility(View.INVISIBLE);
		}
		ship = new PlayerShip(CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth() / 2, CAMERA_HEIGHT + 300, mShipTextureRegion, getEngine(), mSpikeTextureRegion);
		if (SpikyUpgrade>0)ship.addSpikes();

	} //gameInit

	public void gameGetReady() {
		ship.addShapeModifier(new SequenceModifier(
				new IShapeModifierListener() {
					@Override
					public void onModifierFinished(
						IShapeModifier pShapeModifier, final IShape enemy) {
						runOnUpdateThread(new Runnable() {
							public void run() {
								final Text textCenter = new Text(CAMERA_WIDTH / 2 - 150, CAMERA_HEIGHT / 2 - 60, font,
								"Get\nReady", HorizontalAlign.CENTER);
								if (CurrentLevel < 5) textCenter.setColor(0, 0, 0);
								mEngine.getScene().getTopLayer().addEntity(textCenter);
								textCenter.addShapeModifier(new SequenceModifier(
								new IShapeModifierListener() {
									@Override
									public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
										GameActivity.isGameReady = true;
										music1.seekTo(0);
										music1.setVolume(1);
										music2.seekTo(0);
										music3.seekTo(0);
										music4.seekTo(0);
										music5.seekTo(0);
										
										if (ChallengeLevel > 0 ){
											music2.setVolume(1);
											music3.setVolume(1);
											music4.setVolume(1);
											music5.setVolume(1);
										}
									}
								}, new ScaleModifier(0.5f, 0, 1), new DelayModifier(1), new ScaleModifier(0.2f, 1, 0)));
							}
						});
					}
				}, new PathModifier(2, new Path(2).to(CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth() / 2, CAMERA_HEIGHT + 100).to(CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth() / 2, CAMERA_HEIGHT - 160), EaseSineInOut.getInstance())));
	} //gameGetReady
	
	public void gameLevelCleared() {
		final Text textCenter = new Text(
				CAMERA_WIDTH / 2 - 160,
				CAMERA_HEIGHT / 2 - 60, font,
				"Level\nCleared!", HorizontalAlign.CENTER);
		if (CurrentLevel < 5) textCenter.setColor(0, 0, 0);
		mEngine.getScene().getTopLayer()
				.addEntity(textCenter);
		textCenter
				.addShapeModifier(new SequenceModifier(
						new IShapeModifierListener() {
							@Override
							public void onModifierFinished(
									IShapeModifier pShapeModifier,
									IShape pShape) {
								GameActivity.isGameReady = false;
								ship.addShapeModifier(new SequenceModifier(
										new IShapeModifierListener() {
											@Override
											public void onModifierFinished(
													IShapeModifier pShapeModifier, final IShape enemy) {
												runOnUpdateThread(new Runnable() {
													public void run() {
														music1.release();
														music2.release();
														music3.release();
														music4.release();
														music5.release();
														LevelFinished();
													}
												});
											}
										}, new PathModifier(2, new Path(2).to(ship.getX(),ship.getY()).to(
												ship.getX(), -100), EaseStrongIn.getInstance())));
							}
						}, new ScaleModifier(1, 0, 1),
						new DelayModifier(1),
						new ScaleModifier(1, 1, 0)));
	}  //gameLevelCleared
	
	public void createEnemyShip(Path path) {

		if (enemiesToReuse.isEmpty()) {
			final EnemyShip enemy = new EnemyShip(CAMERA_WIDTH / 2
					- mShipTextureRegion.getTileWidth() / 2, -50,
					mEnemyTextureRegion, getEngine(), CurrentLevel);
			enemy.addToScene();
			enemy.follow(path, EnemySpeed, enemyModifier, ChallengeLevel);
		} else {
			final EnemyShip enemy = EnemyShip.reuse();
			enemy.addToScene();
			enemy.follow(path, EnemySpeed + 1, enemyModifier, ChallengeLevel);
		}
	} //createEnemyShip
	
	public void createFriendlyShip(Path path) {

		if (friendliesToReuse.isEmpty()) {
			final FriendlyShip enemy = new FriendlyShip(CAMERA_WIDTH / 2
					- mShipTextureRegion.getTileWidth() / 2, -50,
					mFriendlyTextureRegion, getEngine(), CurrentLevel);
			enemy.addToScene();
			enemy.follow(path, EnemySpeed, enemyModifier);
		} else {
			final FriendlyShip enemy = FriendlyShip.reuse();
			enemy.addToScene();
			enemy.follow(path, EnemySpeed + 1, enemyModifier);
		}
	} //createFriendlyShip

	public void checkCollisions() {
		
		// Check collisions between enemy bullets and ship
		/*if (enemyBullets.size() > 0) {
			for (int i = 0; i < enemyBullets.size(); i++) {
				final EnemyBullet bullet = enemyBullets.get(i);
				if (BaseCollisionChecker.checkAxisAlignedRectangleCollision(
						bullet.getX(), bullet.getY(),
						bullet.getX() + bullet.getWidth(), bullet.getY()
								+ bullet.getHeight(), ship.getX(), ship.getY(),
						ship.getX() + ship.getWidth(),
						ship.getY() + ship.getHeight()) == true) {
					runOnUpdateThread(new Runnable() {
						public void run() {
							bullet.removeFromScene();
							songPoints = songPoints - 3;
							refreshScore();
							setProgressBar();
							vibrate();
						}
					});
				}
			}
		}*/

		
		// Checks for Enemy bullet Collisions
		if (bullets.size() > 0) {
				for (int i = 0; i < bullets.size(); i++) {
					final Bullet bullet = bullets.get(i);
					if (enemies.size() > 0) {
						for (int j = 0; j < enemies.size(); j++) {
							final EnemyShip enemy = enemies.get(j);
							if (enemy.getY() > 10){ 
							if (BaseCollisionChecker
									.checkAxisAlignedRectangleCollision(
											bullet.getX(), bullet.getY(),
											bullet.getX() + bullet.getWidth(),
											bullet.getY() + bullet.getHeight(),
											enemy.getX(), enemy.getY(),
											enemy.getX() + enemy.getWidth(),
											enemy.getY() + enemy.getHeight()) == true
									&& !enemy.isKilled()) {
								runOnUpdateThread(new Runnable() {
									public void run() {
										refreshScore();
										songPoints ++;
										if (ChallengeLevel == 0) {
											if (songPoints >= PointsToNextLevel)
										{
											songLevel++;
											PointsForLastLevel = songPoints - 1;
											PointsToNextLevel = PointsToNextLevel + (PointsPerLevel * songLevel);
											if ((8 - songLevel) <  LaserFrequency) LaserFrequency = (8 - songLevel);
											if (LaserFrequency == 3) LaserFrequency= 4;
											if (songLevel == 6) songLevel = 5;
										}}
										if (LaserPenetrate == false) bullet.removeFromScene();
										vibrate();
										makeExplosion(enemy.getX(), enemy.getY());
										makeEscapedNote(enemy.getX(), enemy.getY());
										enemy.removeFromScene();
									}
								});
							}}
						}
					}
				}
		}

		// Checks for Enemy Player Collisions  
			if (enemies.size() > 0) {
				for (int j = 0; j < enemies.size(); j++) {
					final EnemyShip enemy = enemies.get(j);
					if (BaseCollisionChecker.checkAxisAlignedRectangleCollision(
								ship.getX(), ship.getY(),
								ship.getX() + ship.getWidth(),
								ship.getY() + ship.getHeight(),
								enemy.getX(), enemy.getY(),
								enemy.getX() + enemy.getWidth(),
								enemy.getY() + enemy.getHeight()) == true
						&& !enemy.isKilled()) {
					runOnUpdateThread(new Runnable() {
						public void run() {
							if (SpikyUpgrade <= 0) {
								totalNotesMissed++;
							if ((songPoints - PointsForLastLevel) > DamageNumber) {
								songPoints = songPoints - DamageNumber;
							} else songPoints = PointsForLastLevel;
							refreshScore();
							setProgressBar();
							vibrate();
							enemy.removeFromScene();
							if (ChallengeLevel > 0 ) gameChallengeFailed();
							} else {
								refreshScore();
								songPoints ++;
								if (ChallengeLevel == 0) {
								if (songPoints >= PointsToNextLevel)
								{
									songLevel++;
									PointsForLastLevel = songPoints - 1;
									PointsToNextLevel = PointsToNextLevel + (PointsPerLevel * songLevel);
									if ((8 - songLevel) <  LaserFrequency) LaserFrequency = (8 - songLevel);
									if (LaserFrequency == 3) LaserFrequency= 4;
									if (songLevel == 6) songLevel = 5;
								}
								}
								vibrate();
								makeEscapedNote(enemy.getX(), enemy.getY());
								enemy.removeFromScene();
								SpikyUpgrade--;
								if (SpikyUpgrade <= 0){
									ship.removeSpikes();
								}
							}
							}
						});
					}
				}

			}

			
			// Checks for Challenge level 3 Collisions  
			if (ChallengeLevel == 3) {
				if (bullets.size() > 0) {
					for (int i = 0; i < bullets.size(); i++) {
						final Bullet bullet = bullets.get(i);
						if (friendlies.size() > 0) {
							for (int j = 0; j < friendlies.size(); j++) {
								final FriendlyShip friendly = friendlies.get(j);
								if (friendly.getY() > 20){ 
								if (BaseCollisionChecker
										.checkAxisAlignedRectangleCollision(
												bullet.getX(), bullet.getY(),
												bullet.getX() + bullet.getWidth(),
												bullet.getY() + bullet.getHeight(),
												friendly.getX(), friendly.getY(),
												friendly.getX() + friendly.getWidth(),
												friendly.getY() + friendly.getHeight()) == true
										&& !friendly.isKilled()) {
									runOnUpdateThread(new Runnable() {
										public void run() {
											gameChallengeFailed();
										}
									});
								}
								}
							}
						}
					}
				}
			if (friendlies.size() > 0) {
				for (int j = 0; j < friendlies.size(); j++) {
					final FriendlyShip enemy = friendlies.get(j);
					if (BaseCollisionChecker.checkAxisAlignedRectangleCollision(
								ship.getX(), ship.getY(),
								ship.getX() + ship.getWidth(),
								ship.getY() + ship.getHeight(),
								enemy.getX(), enemy.getY(),
								enemy.getX() + enemy.getWidth(),
								enemy.getY() + enemy.getHeight()) == true
						&& !enemy.isKilled()) {
					runOnUpdateThread(new Runnable() {
						public void run() {
							gameChallengeFailed();
							}
						});
					}
				}
			}

		}
			
		if (upgrades.size() > 0) {
			for (int j = 0; j < upgrades.size(); j++) {
				final Upgrade upgrade= upgrades.get(j);
				if (BaseCollisionChecker.checkAxisAlignedRectangleCollision(
							ship.getX(), ship.getY(),
							ship.getX() + ship.getWidth(),
							ship.getY() + ship.getHeight(),
							upgrade.getX(), upgrade.getY(),
							upgrade.getX() + upgrade.getWidth(),
							upgrade.getY() + upgrade.getHeight()) == true
					&& !upgrade.isKilled()) {
				runOnUpdateThread(new Runnable() {
					public void run() {

						switch (CurrentUpgrade){
						case 1:
							LaserAttackPowerUp = (300 / LaserFrequency);
							break;
						case 2:
							LaserPenetrate = true;
							break;
						case 3:
							SpikyUpgrade = 10;
							ship.addSpikes();
							break;
						}

						refreshScore();
						setProgressBar();
						
						upgrade.removeFromScene();
						}
					});
				}
			}
		}
}  //checkCollisions
	
	public void refreshScore(){
		runOnUiThread(new Runnable() {	
			@Override
			public void run() {
				GameActivity.scoreView.setText("Tracks Rescued: " + songLevel);
			}
		});
	} //refreshScore

	public void makeExplosion(float posx, float posy) {
		if (explosionsToReuse.isEmpty())
			new Explosion(posx, posy, mExplosionTextureRegion, getEngine());
		else Explosion.reuse(posx, posy);
	}  //makeExplosion
	
	public void makeEscapedNote(float posx, float posy) {
		if (ChallengeLevel == 0){
			if (escapedNotesToReuse.isEmpty())
				new EscapedNote(posx, posy, mEscapedNoteTextureRegion, getEngine());
			else EscapedNote.reuse(posx, posy);
		}
	}  //makeEscapedNote
	
	public void vibrate() {
		final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(10);
	}  //vibrate

	public void showExitDialog() {
			music1.pause();
			music2.pause();
			music3.pause();
			music4.pause();
			music5.pause();
			mEngine.stop();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Would you like to exit?").setCancelable(false)
				.setTitle("PAUSE")
				.setIcon(R.drawable.oldicon)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							music1.release();
							music2.release();
							music3.release();
							music4.release();
							music5.release();
							isGameReady = false;
							finish();
						}
					})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						music1.play();
						music2.play();
						music3.play();
						music4.play();
						music5.play();
						mEngine.start();
					}
				});
		AlertDialog alert = builder.create();
//		alert.setIcon(R.drawable.icon);
		alert.show();
	} //showExitDialog
	
	public void ReleaseUpgrade() {
		if (upgradesToReuse.isEmpty()) {
			final Upgrade upgrade = new Upgrade(CAMERA_WIDTH / 2
					- mShipTextureRegion.getTileWidth() / 2, -50,
					mUpgradeTextureRegion, getEngine(), CurrentLevel);
			upgrade.addToScene();
			upgrade.launch(EaseLinear.getInstance());
		} else {
			final Upgrade upgrade = Upgrade.reuse();
			upgrade.addToScene();
			upgrade.launch(EaseLinear.getInstance());
		}
	}  //ReleaseUpgrade
	
/*	public void ReleaseMeteor() {
		if (meteorsToReuse.isEmpty()) {
			final Meteor upgrade = new Meteor(CAMERA_WIDTH / 2
					- mShipTextureRegion.getTileWidth() / 2, -50,
					mUpgradeTextureRegion, getEngine(), CurrentLevel);
			upgrade.addToScene();
			upgrade.launch(EaseLinear.getInstance());
		} else {
			final Meteor upgrade = Meteor.reuse();
			upgrade.addToScene();
			upgrade.launch(EaseLinear.getInstance());
		}
	}  //Launch a meteor attack*/

    public static void setProgressBar() {
		{
			float progressPercent = (float)(songPoints - PointsForLastLevel) / (float)(PointsToNextLevel - PointsForLastLevel);
			healthbar.setWidth(progressPercent*132);
			switch (songLevel){
				case 0:
					healthbar.setColor(0, 0, 1);
					break;
				case 1:
					healthbar.setColor(0, 1, 0);
					break;
				case 2: 
					healthbar.setColor(0, 1, 1);
					break;
				case 3: 
					healthbar.setColor(1, 0, 1);
					break;
				case 4: 
					healthbar.setColor(1, 0, 0);
					break;
				case 5: 
					healthbar.setColor(1, 0, 0);
					healthbar.setWidth(132);
					break;
				default:
					healthbar.setColor(1, 0, 0);
					healthbar.setWidth(132);
					break;
			}

		}
	}  //setProgressBar
    
	public void gameChallengeFailed() {
		if (isGameReady == true){
			isGameReady = false;
		final Text textCenter = new Text(
				CAMERA_WIDTH / 2 - 160,
				CAMERA_HEIGHT / 2 - 60, font,
				"Failed", HorizontalAlign.CENTER);
		if (CurrentLevel < 5) textCenter.setColor(0, 0, 0);
		mEngine.getScene().getTopLayer()
				.addEntity(textCenter);
		textCenter
				.addShapeModifier(new SequenceModifier(
						new IShapeModifierListener() {
							@Override
							public void onModifierFinished(
									IShapeModifier pShapeModifier,
									IShape pShape) {
								music1.release();
								music2.release();
								music3.release();
								music4.release();
								music5.release();
								finish();
							}
						}, new ScaleModifier(1, 0, 1),
						new DelayModifier(1),
						new ScaleModifier(1, 1, 0)));
		}
	}  //gameLevelCleared

	public void onPause() {
		super.onPause();
		if (isGameReady == true) {
			showExitDialog();
		}
	}
	
	public void onResume() {
		super.onResume();
		if (isGameReady == true) {
			mEngine.stop();
		}
	}


}