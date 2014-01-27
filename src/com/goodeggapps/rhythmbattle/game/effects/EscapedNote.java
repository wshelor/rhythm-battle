package com.goodeggapps.rhythmbattle.game.effects;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.LoopModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.shape.modifier.ease.EaseSineInOut;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.Path;

import com.goodeggapps.rhythmbattle.game.GameActivity;

public class EscapedNote extends Sprite {

	private final Engine mEngine;
	
	public EscapedNote(float pX, float pY, TextureRegion mEscapedNoteTextureRegion, final Engine engine) {
		super(pX, pY, mEscapedNoteTextureRegion);
		mEngine = engine;
		GameActivity.escapedNotes.add(this);
		addToScene();
		moveToBar(pX, pY);
	}

	public static EscapedNote reuse(float posx, float posy) {
		final EscapedNote explosion = GameActivity.escapedNotesToReuse.get(0);
		GameActivity.escapedNotes.add(explosion);
		GameActivity.escapedNotesToReuse.remove(explosion);
		explosion.moveToBar(posx, posy);
		explosion.setVisible(true);
		return explosion;
	}

	public void addToScene() {
		mEngine.getScene().getTopLayer().addEntity(this);
	}

	public void moveToBar(float posx, float posy) {
		Path path = new Path(3).to(posx, posy).to(360, 20).to(360, 20);
		this.addShapeModifier(new LoopModifier(new PathModifier(1, path,
				null, new IPathModifierListener() {
					@Override
					public void onWaypointPassed(
							final PathModifier pPathModifier,
							final IShape pShape, final int pWaypointIndex) {
						if (pWaypointIndex == 2) {
							mEngine.runOnUpdateThread(new Runnable() {
								public void run() {
									((EscapedNote) pShape).removeFromScene();
								}
							});
						}

					}
			}, EaseSineInOut.getInstance())));
	}
public void removeFromScene() {
	this.clearShapeModifiers();
	this.addShapeModifier(new SequenceModifier(
			new IShapeModifierListener() {
				@Override
				public void onModifierFinished(
						IShapeModifier pShapeModifier, final IShape enemy) {
					mEngine.runOnUpdateThread(new Runnable() {
						public void run() {
							mEngine.getScene().getBottomLayer().removeEntity(((EscapedNote) enemy));
							GameActivity.escapedNotesToReuse.add(0,(EscapedNote) enemy);
							GameActivity.escapedNotes.remove((EscapedNote) enemy);
							GameActivity.setProgressBar();
							((EscapedNote) enemy).setScale(1);
						}
					});
				}
			}, new ScaleModifier(0.3f, 1, 0)));
}

}
