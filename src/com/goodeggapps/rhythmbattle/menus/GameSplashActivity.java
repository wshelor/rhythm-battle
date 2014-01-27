package com.goodeggapps.rhythmbattle.menus;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.AlphaModifier;
import org.anddev.andengine.entity.shape.modifier.DelayModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.source.ITextureSource;
import org.anddev.andengine.ui.activity.BaseSplashActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;

public class GameSplashActivity extends BaseSplashActivity {

        private static final int CAMERA_WIDTH = 480;
        private static final int CAMERA_HEIGHT = 800;
        
        private Camera camera;
        private Texture texture;
        private TextureRegion goodEggApps;
        
        @Override
        public Engine onLoadEngine() {
        	setVolumeControlStream(AudioManager.STREAM_MUSIC);
    		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    		final EngineOptions engineOptions = new EngineOptions(true,
    				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
    						CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
    		return new Engine(engineOptions);
        }

        @Override
        public void onLoadResources() {
                texture = new Texture(512, 256, TextureOptions.BILINEAR);
                TextureRegionFactory.setAssetBasePath("img/");
                goodEggApps = TextureRegionFactory.createFromAsset(this.texture, this, "goodeggapps.png", 0, 0);
                mEngine.getTextureManager().loadTexture(this.texture);
        }

        @Override
        public Scene onLoadScene() {
        	
                final Scene scene = new Scene(1);
                scene.setBackground(new ColorBackground(0, 0, 0));

                final int x = (CAMERA_WIDTH - this.goodEggApps.getWidth()) / 2;
                final int y = (CAMERA_HEIGHT - this.goodEggApps.getHeight()) / 2;

                final Sprite picture = new Sprite(x, y, this.goodEggApps);
                picture.setAlpha(0);
                scene.getTopLayer().addEntity(picture);
                
                picture.addShapeModifier(new SequenceModifier(
                		new IShapeModifierListener() {

							@Override
							public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
								
								texture.clearTextureSources();
					            TextureRegionFactory.createFromAsset(texture, GameSplashActivity.this, "andengine.png", 0, 0);

								picture.addShapeModifier(new SequenceModifier(
				                		new IShapeModifierListener() {

											@Override
											public void onModifierFinished(
													IShapeModifier pShapeModifier,
													IShape pShape) {
												
												Intent menu = new Intent(GameSplashActivity.this, MenuActivity.class);
												startActivity(menu);
												finish();
											}
										},
				                		new DelayModifier(1),
				                		new AlphaModifier(0.5f, 0, 1),
				                		new DelayModifier(2),
				                		new AlphaModifier(0.5f, 1, 0),
										new DelayModifier(0.5f)
				                ));
							}
						},
						new DelayModifier(1),
                		new AlphaModifier(0.5f, 0, 1),
                		new DelayModifier(2),
                		new AlphaModifier(0.5f, 1, 0)
                ));

                return scene;
        }
        
        @Override
        public void onLoadComplete() {}

		@Override
		protected ScreenOrientation getScreenOrientation() {
			return null;
		}

		@Override
		protected ITextureSource onGetSplashTextureSource() {
			return null;
		}

		@Override
		protected float getSplashDuration() {
			return 0;
		}

		@Override
		protected Class<? extends Activity> getFollowUpActivity() {
			return null;
		}
}