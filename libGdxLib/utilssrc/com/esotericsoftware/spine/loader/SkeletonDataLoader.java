package com.esotericsoftware.spine.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

import com.esotericsoftware.spine.SkeletonBinary;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader;
import com.esotericsoftware.spine.attachments.AttachmentLoader;

/** An asset loader to create and load skeleton data. The data file is assumed to be binary if it ends with <code>.skel</code>,
 * otherwise JSON is assumed. The {@link SkeletonDataParameter} can provide a texture atlas name or an {@link AttachmentLoader}.
 * If neither is provided, a texture atlas name based on the skeleton file name with an <code>.atlas</code> extension is used.
 * When a texture atlas name is used, the texture atlas is loaded by the asset manager as a dependency.
 * <p>
 * Example:
 *
 * <pre>
 * // Load skeleton.json and skeleton.atlas:
 * assetManager.load("skeleton.json", SkeletonData.class);
 * // Or specify the atlas/AttachmentLoader and scale:
 * assetManager.setLoader(SkeletonData.class, new SkeletonDataLoader(new InternalFileHandleResolver()));
 * SkeletonDataParameter parameter = new SkeletonDataParameter("skeleton2x.atlas", 2);
 * assetManager.load("skeleton.json", SkeletonData.class, parameter);
 * </pre>
 */
public class SkeletonDataLoader extends AsynchronousAssetLoader<SkeletonData, SkeletonDataLoader.SkeletonDataParameter> {
    private SkeletonData skeletonData;

    public SkeletonDataLoader (FileHandleResolver resolver) {
        super(resolver);
    }

    public void loadAsync (AssetManager manager, String fileName, FileHandle file, @Null SkeletonDataParameter parameter) {
        float scale = 1;
        AttachmentLoader attachmentLoader = null;
        if (parameter != null) {
            scale = parameter.scale;
            if (parameter.attachmentLoader != null)
                attachmentLoader = parameter.attachmentLoader;
            else if (parameter.atlasName != null)
                attachmentLoader = new AtlasAttachmentLoader(manager.get(parameter.atlasName, TextureAtlas.class));
        }
        if (attachmentLoader == null)
            attachmentLoader = new AtlasAttachmentLoader(manager.get(file.pathWithoutExtension() + ".atlas", TextureAtlas.class));

        if (file.extension().equalsIgnoreCase("skel")) {
            SkeletonBinary skeletonBinary = new SkeletonBinary(attachmentLoader);
            skeletonBinary.setScale(scale);
            skeletonData = skeletonBinary.readSkeletonData(file);
        } else {
            SkeletonJson skeletonJson = new SkeletonJson(attachmentLoader);
            skeletonJson.setScale(scale);
            skeletonData = skeletonJson.readSkeletonData(file);
        }
    }

    public SkeletonData loadSync (AssetManager manager, String fileName, FileHandle file, @Null SkeletonDataParameter parameter) {
        SkeletonData skeletonData = this.skeletonData;
        this.skeletonData = null;
        return skeletonData;
    }

    public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, @Null SkeletonDataParameter parameter) {
        Array<AssetDescriptor> dependencies = new Array<AssetDescriptor>();
        String atlasFileName = file.pathWithoutExtension() + ".atlas";
        if (parameter != null) {
            if (parameter.atlasName != null && parameter.atlasName.length() > 0)
                atlasFileName = parameter.atlasName;
        }
        if (!Gdx.files.internal(atlasFileName).exists()) {
            return dependencies;
        }
        dependencies.add(new AssetDescriptor<TextureAtlas>(atlasFileName, TextureAtlas.class, null));
        return dependencies;
    }

    static public class SkeletonDataParameter extends AssetLoaderParameters<SkeletonData> {
        public String atlasName;
        public AttachmentLoader attachmentLoader;
        public float scale = 1;

        public SkeletonDataParameter () {
        }

        public SkeletonDataParameter (String atlasName) {
            this.atlasName = atlasName;
        }

        public SkeletonDataParameter (String atlasName, float scale) {
            this.atlasName = atlasName;
            this.scale = scale;
        }

        public SkeletonDataParameter (AttachmentLoader attachmentLoader) {
            this.attachmentLoader = attachmentLoader;
        }

        public SkeletonDataParameter (AttachmentLoader attachmentLoader, float scale) {
            this.attachmentLoader = attachmentLoader;
            this.scale = scale;
        }
    }
}
