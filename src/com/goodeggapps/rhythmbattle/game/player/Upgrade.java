package com.goodeggapps.rhythmbattle.game.player;


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
import com.goodeggapps.rhythmbattle.game.enemies.IEnemy;

public class Upgrade extends AnimatedSprite implements IEnemy {

	private final Engine mEngine;
	private boolean killed;

	public Upgrade(float pX, float pY, TiledTextureRegion pTiledTextureRegion, final Engine engine, int level) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		killed=false;
		GameActivity.upgrades.add(this);
		this.animate(GameActivity.ANIMATION_FRAMELENGTH);
	}
	
	public boolean isKilled(){
		return killed;
	}

	public static Upgrade reuse() {
		final Upgrade upgrade = GameActivity.upgradesToReuse.get(0);
		upgrade.killed = false;
		GameActivity.upgrades.add(upgrade);
		GameActivity.upgradesToReuse.remove(upgrade);
		return upgrade;
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
								mEngine.getScene().getBottomLayer().removeEntity(((Upgrade) enemy));
								GameActivity.upgradesToReuse.add(0,(Upgrade) enemy);
								GameActivity.upgrades.remove((Upgrade) enemy);
								((Upgrade) enemy).setScale(1);
							}
						});
					}
				}, new ScaleModifier(0.3f, 1, 0)));
	}

	public void launch(IEaseFunction enemyModifier) {
		Path path = new Path(2).to((float)((Math.random() * 400) + 20), (float)(-50)).
				to((float)((Math.random() * 400) + 20), 850);
		this.addShapeModifier(new LoopModifier(new PathModifier(5, path,
				null, new IPathModifierListener() {
					@Override
					public void onWaypointPassed(
							final PathModifier pPathModifier, final IShape pShape, final int pWaypointIndex) {
								if (pWaypointIndex == 1) {
									mEngine.runOnUpdateThread(new Runnable() {
										public void run() {
											((Upgrade) pShape).removeFromScene();
										} 
									});
								} 
					}}, enemyModifier)));
	}
}
