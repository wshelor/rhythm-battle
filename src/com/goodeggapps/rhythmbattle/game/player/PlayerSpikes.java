package com.goodeggapps.rhythmbattle.game.player;


import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class PlayerSpikes extends Sprite {
	
	private final PlayerShip s;
	private int spinAmt;

	public PlayerSpikes(PlayerShip ship, TextureRegion mSpikeTextureRegion) {
		super(0, 0, mSpikeTextureRegion);
		this.setVisible(false);
		s=ship;
		spinAmt = 0;
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
			if (this.isVisible())this.setPosition((s.getX() - (this.getWidth() / 2) + (s.getWidth() / 2)), 
					(s.getY() - (this.getHeight() / 2) + (s.getHeight() / 2)));
			super.onManagedUpdate(pSecondsElapsed);
			spinAmt++;
			this.setRotation(spinAmt);
	}

	
	
	public void makeVisible() {
			this.setVisible(true);
	}

	public void makeInvisible() {
		this.setVisible(false);
}

}
