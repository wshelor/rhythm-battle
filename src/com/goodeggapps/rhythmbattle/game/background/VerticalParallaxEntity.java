package com.goodeggapps.rhythmbattle.game.background;
import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.shape.Shape;
 
public class VerticalParallaxEntity extends ParallaxEntity {
    
    protected float mParallaxFactor;
    protected Shape mShape;

    public void onDraw(GL10 pGL, float pParallaxValue, Camera pCamera) {
            pGL.glPushMatrix();
            {
                    final float cameraHeight = pCamera.getHeight();
                    final float shapeHeightScaled = this.mShape.getHeightScaled()-1;
                    //minus 1 creates overlap to remove texture seams (caused by Bilinear filtering)
                    float baseOffset = (pParallaxValue * this.mParallaxFactor) % shapeHeightScaled;

                    while(baseOffset > 0) {
                            baseOffset -= shapeHeightScaled;
                    }
                    pGL.glTranslatef(0, baseOffset, 0);
                    float currentMaxY = baseOffset;
do {
                            this.mShape.onDraw(pGL, pCamera);
                            pGL.glTranslatef(0, shapeHeightScaled, 0);
                            currentMaxY += shapeHeightScaled;
                    } while(currentMaxY < cameraHeight);
            }
            pGL.glPopMatrix();
    }

    public VerticalParallaxEntity(float pParallaxFactor, Shape pShape) {
            super(pParallaxFactor, pShape);
            this.mParallaxFactor = pParallaxFactor;
            this.mShape = pShape;
    }

}