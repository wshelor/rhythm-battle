package com.goodeggapps.rhythmbattle.game.enemies;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.LoopModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.shape.modifier.ease.IEaseFunction;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Path;

import com.goodeggapps.rhythmbattle.game.GameActivity;

public class FriendlyShip extends AnimatedSprite implements IEnemy {

	private final Engine mEngine;
	private boolean killed;

	public FriendlyShip(float pX, float pY, TiledTextureRegion pTiledTextureRegion, final Engine engine, int level) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		killed=false;
		GameActivity.friendlies.add(this);
		this.animate(GameActivity.ANIMATION_FRAMELENGTH);
	}
	
	public boolean isKilled(){
		return killed;
	}

	public static FriendlyShip reuse() {
		final FriendlyShip enemy = GameActivity.friendliesToReuse.get(0);
		enemy.killed = false;
		GameActivity.friendlies.add(enemy);
		GameActivity.friendliesToReuse.remove(enemy);
		return enemy;
	}

	public void addToScene() {
		mEngine.getScene().getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		killed=true;
		this.clearShapeModifiers();
		this.addShapeModifier(new SequenceModifier(
				new IShapeModifierListener() {
					@Override
					public void onModifierFinished(
							IShapeModifier pShapeModifier, final IShape enemy) {
						mEngine.runOnUpdateThread(new Runnable() {
							public void run() {
								mEngine.getScene().getBottomLayer().removeEntity(((FriendlyShip) enemy));
								GameActivity.friendliesToReuse.add(0,(FriendlyShip) enemy);
								GameActivity.friendlies.remove((FriendlyShip) enemy);
								((FriendlyShip) enemy).setScale(1);
							}
						});
					}
				}, new ScaleModifier(0.3f, 1, 0)));
	}

	public void follow(Path path, float speed, IEaseFunction enemyModifier) {
		path = new Path(5).to((float)((Math.random() * 400) + 20), (float)(-50)).to((float)((Math.random() * 400) + 20), (float)(Math.random() * 420)).
				to((float) ((Math.random() * 400) + 20), (float)((Math.random() * 400) + 20)).to((float)((Math.random() * 400) + 20), (float)((Math.random() * 400) + 20)).
				//to((float) ((Math.random() * 400) + 20), (float)((Math.random() * 400) + 20)).to((float)((Math.random() * 400) + 20), (float)((Math.random() * 400) + 20)).
				to((float)((Math.random() * 400) + 20), 850);
		this.addShapeModifier(new LoopModifier(new PathModifier(speed, path,
				null, new IPathModifierListener() {
					@Override
					public void onWaypointPassed(
							final PathModifier pPathModifier,
							final IShape pShape, final int pWaypointIndex) {
						if (pWaypointIndex == 4) {
							mEngine.runOnUpdateThread(new Runnable() {
								public void run() {
									((FriendlyShip) pShape).removeFromScene();
								} 
							});
						} 
//						if (pWaypointIndex != 0 && pWaypointIndex != 6 && currentLevel > 3) 
//							((EnemyShip) pShape).fire();


					}}, enemyModifier)));
	}

	public void fire() {
		if (GameActivity.enemyBulletsToReuse.isEmpty()) {
			final EnemyBullet mBullet = new EnemyBullet(this.getX() + 10,
					this.getY() + 30, GameActivity.mEnemyBulletTextureRegion,
					mEngine);
			mBullet.addToScene();
		} else {
			final EnemyBullet mBullet = EnemyBullet.reuse(this.getX() + 10,
					this.getY() + 30);
			mBullet.addToScene();
		}
	}

}
