package com.kw.gdx.particle;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;

/** See <a href="http://www.badlogicgames.com/wordpress/?p=1255">http://www.badlogicgames.com/wordpress/?p=1255</a>
 * @author mzechner */
public class ParticleEffect implements Disposable {
    private final Array<ParticleEmitter> emitters;
    private BoundingBox bounds;
    private boolean ownsTexture;
    protected float xSizeScale = 1f;
    protected float ySizeScale = 1f;
    protected float motionScale = 1f;

    public ParticleEffect () {
        emitters = new Array(8);
    }

    public ParticleEffect (ParticleEffect effect) {
        emitters = new Array(true, effect.getEmitters().size);
        for (int i = 0, n = effect.getEmitters().size; i < n; i++)
            emitters.add(newEmitter(effect.getEmitters().get(i)));
    }

    public void start () {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).start();
    }

    /** Resets the com.kw.gdx.animation.effect so it can be started again like a new com.kw.gdx.animation.effect. Any changes to
     * scale are reverted. See {@link #reset(boolean)}.*/
    public void reset () {
        reset(true);
    }

    /** Resets the com.kw.gdx.animation.effect so it can be started again like a new com.kw.gdx.animation.effect.
     * @param resetScaling Whether to restore the original size and motion parameters if they were scaled. Repeated scaling
     * and resetting may introduce error. */
    public void reset (boolean resetScaling){
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).reset();
        if (resetScaling && (xSizeScale != 1f || ySizeScale != 1f || motionScale != 1f)){
            scaleEffect(1f / xSizeScale, 1f / ySizeScale, 1f / motionScale);
            xSizeScale = ySizeScale = motionScale = 1f;
        }
    }

    public void update (float delta) {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).update(delta);
    }

    public void draw (Batch spriteBatch) {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).draw(spriteBatch);
    }

    public void draw (Batch spriteBatch, float delta,float pa) {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).draw(spriteBatch, delta,pa);
    }


    public void allowCompletion () {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).allowCompletion();
    }

    public boolean isComplete () {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (!emitter.isComplete()) return false;
        }
        return true;
    }

    public void setDuration (int duration) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            emitter.setContinuous(false);
            emitter.duration = duration;
            emitter.durationTimer = 0;
        }
    }

    public void setPosition (float x, float y) {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).setPosition(x, y);
    }

    public void flipY () {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).flipY();
    }

    public void flipX () {
        for (int i = 0, n = emitters.size; i < n; i++)
            emitters.get(i).flipX();
    }

    public Array<ParticleEmitter> getEmitters () {
        return emitters;
    }

    /** Returns the emitter with the specified name, or null. */
    public ParticleEmitter findEmitter (String name) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (emitter.getName().equals(name)) return emitter;
        }
        return null;
    }

    public void save (Writer output) throws IOException {
        int index = 0;
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (index++ > 0) output.write("\n");
            emitter.save(output);
        }
    }

    public void load (FileHandle effectFile, FileHandle imagesDir) {
        loadEmitters(effectFile);
        loadEmitterImages(imagesDir);
    }

    public void load (FileHandle effectFile, TextureAtlas atlas) {
        load(effectFile, atlas, null);
    }

    public void load (FileHandle effectFile, TextureAtlas atlas, String atlasPrefix) {
        loadEmitters(effectFile);
        loadEmitterImages(atlas, atlasPrefix);
    }

    public void loadEmitters (FileHandle effectFile) {
        InputStream input = effectFile.read();
        emitters.clear();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(input), 512);
            while (true) {
                ParticleEmitter emitter = newEmitter(reader);
                emitters.add(emitter);
                if (reader.readLine() == null) break;
            }
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error loading com.kw.gdx.animation.effect: " + effectFile, ex);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }

    public void loadEmitterImages (TextureAtlas atlas) {
        loadEmitterImages(atlas, null);
    }

    public void loadEmitterImages (TextureAtlas atlas, String atlasPrefix) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (emitter.getImagePaths().size == 0) continue;
            Array<Sprite> sprites = new Array<Sprite>();
            for (String imagePath : emitter.getImagePaths()) {
                String imageName = new File(imagePath.replace('\\', '/')).getName();
                int lastDotIndex = imageName.lastIndexOf('.');
                if (lastDotIndex != -1) imageName = imageName.substring(0, lastDotIndex);
                if (atlasPrefix != null) imageName = atlasPrefix + imageName;
                Sprite sprite = atlas.createSprite(imageName);
                if (sprite == null) throw new IllegalArgumentException("SpriteSheet missing image: " + imageName);
                sprites.add(sprite);
            }
            emitter.setSprites(sprites);
        }
    }

    public void loadEmitterImages (FileHandle imagesDir) {
        ownsTexture = true;
        HashMap<String, Sprite> loadedSprites = new HashMap<String, Sprite>(emitters.size);
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            if (emitter.getImagePaths().size == 0) continue;
            Array<Sprite> sprites = new Array<Sprite>();
            for (String imagePath : emitter.getImagePaths()) {
                String imageName = new File(imagePath.replace('\\', '/')).getName();
                Sprite sprite = loadedSprites.get(imageName);
                if (sprite == null) {
                    sprite = new Sprite(loadTexture(imagesDir.child(imageName)));
                    loadedSprites.put(imageName, sprite);
                }
                sprites.add(sprite);
            }
            emitter.setSprites(sprites);
        }
    }

    protected ParticleEmitter newEmitter (BufferedReader reader) throws IOException {
        return new ParticleEmitter(reader);
    }

    protected ParticleEmitter newEmitter (ParticleEmitter emitter) {
        return new ParticleEmitter(emitter);
    }

    protected Texture loadTexture (FileHandle file) {
        return new Texture(file, false);
    }

    /** Disposes the texture for each sprite for each ParticleEmitter. */
    public void dispose () {
        if (!ownsTexture) return;
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            for (Sprite sprite : emitter.getSprites()) {
                sprite.getTexture().dispose();
            }
        }
    }

    /** Returns the bounding box for all active particles. z axis will always be zero. */
    public BoundingBox getBoundingBox () {
        if (bounds == null) bounds = new BoundingBox();

        BoundingBox bounds = this.bounds;
        bounds.inf();
        for (ParticleEmitter emitter : this.emitters)
            bounds.ext(emitter.getBoundingBox());
        return bounds;
    }

    /** Permanently scales all the size and motion parameters of all the emitters in this com.kw.gdx.animation.effect. If this com.kw.gdx.animation.effect originated from a
     * {@link ParticleEffectPool}, the scale will be reset when it is returned to the pool. */
    public void scaleEffect (float scaleFactor) {
        scaleEffect(scaleFactor, scaleFactor, scaleFactor);
    }

    /** Permanently scales all the size and motion parameters of all the emitters in this com.kw.gdx.animation.effect. If this com.kw.gdx.animation.effect originated from a
     * {@link ParticleEffectPool}, the scale will be reset when it is returned to the pool. */
    public void scaleEffect (float scaleFactor, float motionScaleFactor) {
        scaleEffect(scaleFactor, scaleFactor, motionScaleFactor);
    }

    /** Permanently scales all the size and motion parameters of all the emitters in this com.kw.gdx.animation.effect. If this com.kw.gdx.animation.effect originated from a
     * {@link ParticleEffectPool}, the scale will be reset when it is returned to the pool. */
    public void scaleEffect (float xSizeScaleFactor, float ySizeScaleFactor, float motionScaleFactor) {
        xSizeScale *= xSizeScaleFactor;
        ySizeScale *= ySizeScaleFactor;
        motionScale *= motionScaleFactor;
        for (ParticleEmitter particleEmitter : emitters) {
            particleEmitter.scaleSize(xSizeScaleFactor, ySizeScaleFactor);
            particleEmitter.scaleMotion(motionScaleFactor);
        }
    }

    /** Sets the {@link ParticleEmitter#setCleansUpBlendFunction(boolean) cleansUpBlendFunction}
     * parameter on all {@link ParticleEmitter ParticleEmitters} currently in this ParticleEffect.
     * <p>
     * IMPORTANT: If set to false and if the next object to use this Batch expects alpha blending, you are responsible for setting
     * the Batch's blend function to (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA) before that next object is drawn.
     * @param cleanUpBlendFunction */
    public void setEmittersCleanUpBlendFunction (boolean cleanUpBlendFunction) {
        for (int i = 0, n = emitters.size; i < n; i++) {
            emitters.get(i).setCleansUpBlendFunction(cleanUpBlendFunction);
        }
    }

    public void noAdd() {

    }
}
