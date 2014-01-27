package com.goodeggapps.rhythmbattle.game.player;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;

import com.goodeggapps.rhythmbattle.game.GameActivity;

public class PlayerShip extends AnimatedSprite {

	private final Engine mEngine;
	private int health;
	private boolean killed=false;
	private PlayerShipShadow shadow;
	private PlayerSpikes spikes;
	
	public boolean isKilled(){
		return killed;
	}
	
	public void kill(){
		killed=true;
	}

	public PlayerShip(float pX, float pY,
			TiledTextureRegion pTiledTextureRegion, Engine engine, TextureRegion mSpikeTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		this.animate(200);
		shadow = new PlayerShipShadow(this);
		spikes = new PlayerSpikes(this, mSpikeTextureRegion);

	}
	

	public void addToScene(Scene scene) {
		scene.getBottomLayer().addEntity(shadow);
		scene.getBottomLayer().addEntity(spikes);
		scene.getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		mEngine.getScene().getBottomLayer().removeEntity(shadow);
		mEngine.getScene().getBottomLayer().removeEntity(spikes);
		mEngine.getScene().getBottomLayer().removeEntity(this);
	}

	public void addSpikes() {
		spikes.makeVisible();
	}

	public void removeSpikes() {
		spikes.makeInvisible();
	}
	
	public int getHealth() {
		return health;
	}

	public void fire() {
		
							
			if (GameActivity.bulletsToReuse.size() == 0) {
				final Bullet bullet = new Bullet(this.getX()+10, this.getY(), GameActivity.mBulletTextureRegion, mEngine);
				bullet.addToScene();
			} else {
				Bullet.reuse(this.getX()+10, this.getY());
			}
	}

	public void move(AccelerometerData accelerometer) {
		if (GameActivity.isGameOver == false) {
			if (this.getX() > 20 && this.getX() < mEngine.getCamera().getWidth()-20 - this.getWidth()) {
				this.setVelocityX(-accelerometer.getX() * 50);
			} else {
				this.setVelocityX(0);

				if (this.getX() <= 20 && accelerometer.getX() < 0)
					this.setVelocityX(-accelerometer.getX() * 50);

				if (this.getX() >= mEngine.getCamera().getWidth()-20 - this.getWidth()
						&& accelerometer.getX() > 0)
					this.setVelocityX(-accelerometer.getX() * 50);
			}

			if (this.getY() > mEngine.getCamera().getHeight()-200 && this.getY() < mEngine.getCamera().getHeight()-20 - this.getHeight()) {
				this.setVelocityY(accelerometer.getY() * 50);
			} else {
				this.setVelocityY(0);

				if (this.getY() <= mEngine.getCamera().getHeight()-200 && accelerometer.getY() > 0)
					this.setVelocityY(accelerometer.getY() * 50);

				if (this.getY() >= mEngine.getCamera().getHeight()-20 - this.getHeight()
						&& accelerometer.getY() < 0)
					this.setVelocityY(accelerometer.getY() * 50);
			}
		}
	}

}
