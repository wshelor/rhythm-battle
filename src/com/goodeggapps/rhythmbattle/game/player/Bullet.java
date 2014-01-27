package com.goodeggapps.rhythmbattle.game.player;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.goodeggapps.rhythmbattle.game.GameActivity;

public class Bullet extends Sprite {

	private final Engine mEngine;

	public Bullet(float pX, float pY, TextureRegion pTiledTextureRegion, Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		if (GameActivity.LaserPenetrate) this.setColor(1, 1, 0);
		this.setVelocity(0, -900);
		GameActivity.bullets.add(this);
	}
	public static Bullet reuse(float posx, float posy) {
		final Bullet bullet = GameActivity.bulletsToReuse.get(0);
		GameActivity.bullets.add(bullet);
		GameActivity.bulletsToReuse.remove(bullet);
		if (GameActivity.LaserPenetrate) bullet.setColor(1, 1, 0);
		bullet.setVisible(true);
		bullet.setPosition(posx, posy);
		bullet.setVelocity(0, -900);
		return bullet;
	}
	
	public void addToScene() {
		mEngine.getScene().getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		this.setVelocity(0,0);
		this.setVisible(false);
		GameActivity.bulletsToReuse.add(this);
		GameActivity.bullets.remove(this);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		final Bullet bullet = this;
		if(bullet.isVisible())
			if (this.getY() < -50) {
				mEngine.runOnUpdateThread(new Runnable() {
					public void run() {
							bullet.removeFromScene();
					}
				});
			}

		super.onManagedUpdate(pSecondsElapsed);
	}
}
