/******************************************************************************
 * Spine Runtimes License Agreement
 * Last updated September 24, 2021. Replaces all prior versions.
 *
 * Copyright (c) 2013-2021, Esoteric Software LLC
 *
 * Integration of the Spine Runtimes into software or otherwise creating
 * derivative works of the Spine Runtimes is permitted under the terms and
 * conditions of Section 2 of the Spine Editor License Agreement:
 * http://esotericsoftware.com/spine-editor-license
 *
 * Otherwise, it is permitted to integrate the Spine Runtimes into software
 * or otherwise create derivative works of the Spine Runtimes (collectively,
 * "Products"), provided that each user of the Products must obtain their own
 * Spine Editor license and redistribution of the Products in any form must
 * include this license and copyright notice.
 *
 * THE SPINE RUNTIMES ARE PROVIDED BY ESOTERIC SOFTWARE LLC "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ESOTERIC SOFTWARE LLC BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES,
 * BUSINESS INTERRUPTION, OR LOSS OF USE, DATA, OR PROFITS) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THE SPINE RUNTIMES, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package com.esotericsoftware.spine.attachments;

import static com.esotericsoftware.spine.utils.SpineUtils.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Null;

import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Slot;

/** An attachment that displays a textured quadrilateral.
 * <p>
 * See <a href="http://esotericsoftware.com/spine-regions">Region attachments</a> in the Spine User Guide. */
public class RegionAttachment extends Attachment implements HasTextureRegion {
	static public final int BLX = 0, BLY = 1;
	static public final int ULX = 2, ULY = 3;
	static public final int URX = 4, URY = 5;
	static public final int BRX = 6, BRY = 7;

	private TextureRegion region;
	private String path;
	private float x, y, scaleX = 1, scaleY = 1, rotation, width, height;
	private final float[] uvs = new float[8];
	private final float[] offset = new float[8];
	private final Color color = new Color(1, 1, 1, 1);
	private @Null Sequence sequence;

	public RegionAttachment (String name) {
		super(name);
	}

	/** Copy constructor. */
	protected RegionAttachment (RegionAttachment other) {
		super(other);
		region = other.region;
		path = other.path;
		x = other.x;
		y = other.y;
		scaleX = other.scaleX;
		scaleY = other.scaleY;
		rotation = other.rotation;
		width = other.width;
		height = other.height;
		arraycopy(other.uvs, 0, uvs, 0, 8);
		arraycopy(other.offset, 0, offset, 0, 8);
		color.set(other.color);
		sequence = other.sequence != null ? new Sequence(other.sequence) : null;
	}

	/** Calculates the {@link #offset} and {@link #uvs} using the region and the attachment's transform. Must be called if the
	 * region, the region's properties, or the transform are changed. */
	public void updateRegion () {
		float width = getWidth();
		float height = getHeight();
		float localX2 = width / 2;
		float localY2 = height / 2;
		float localX = -localX2;
		float localY = -localY2;
		boolean rotated = false;
		if (region instanceof AtlasRegion) {
			AtlasRegion region = (AtlasRegion)this.region;
			localX += region.offsetX / region.originalWidth * width;
			localY += region.offsetY / region.originalHeight * height;
            if (region.rotate) {
				rotated = true;
				localX2 -= (region.originalWidth - region.offsetX - region.packedHeight) / region.originalWidth * width;
				localY2 -= (region.originalHeight - region.offsetY - region.packedWidth) / region.originalHeight * height;
			} else {
				localX2 -= (region.originalWidth - region.offsetX - region.packedWidth) / region.originalWidth * width;
				localY2 -= (region.originalHeight - region.offsetY - region.packedHeight) / region.originalHeight * height;
			}
		}
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		localX *= scaleX;
		localY *= scaleY;
		localX2 *= scaleX;
		localY2 *= scaleY;
		float rotation = getRotation();
		float cos = (float)Math.cos(degRad * rotation);
		float sin = (float)Math.sin(degRad * rotation);
		float x = getX();
		float y = getY();
		float localXCos = localX * cos + x;
		float localXSin = localX * sin;
		float localYCos = localY * cos + y;
		float localYSin = localY * sin;
		float localX2Cos = localX2 * cos + x;
		float localX2Sin = localX2 * sin;
		float localY2Cos = localY2 * cos + y;
		float localY2Sin = localY2 * sin;
		float[] offset = this.offset;
		offset[BLX] = localXCos - localYSin;
		offset[BLY] = localYCos + localXSin;
		offset[ULX] = localXCos - localY2Sin;
		offset[ULY] = localY2Cos + localXSin;
		offset[URX] = localX2Cos - localY2Sin;
		offset[URY] = localY2Cos + localX2Sin;
		offset[BRX] = localX2Cos - localYSin;
		offset[BRY] = localYCos + localX2Sin;

		float[] uvs = this.uvs;
		if (rotated) {
			uvs[URX] = region.getU();
			uvs[URY] = region.getV2();
			uvs[BRX] = region.getU();
			uvs[BRY] = region.getV();
			uvs[BLX] = region.getU2();
			uvs[BLY] = region.getV();
			uvs[ULX] = region.getU2();
			uvs[ULY] = region.getV2();
		} else {
			uvs[ULX] = region.getU();
			uvs[ULY] = region.getV2();
			uvs[URX] = region.getU();
			uvs[URY] = region.getV();
			uvs[BRX] = region.getU2();
			uvs[BRY] = region.getV();
			uvs[BLX] = region.getU2();
			uvs[BLY] = region.getV2();
		}
	}

	public void setRegion (TextureRegion region) {
		if (region == null) throw new IllegalArgumentException("region cannot be null.");
		this.region = region;
	}

	public @Null TextureRegion getRegion () {
		return region;
	}

	/** Transforms the attachment's four vertices to world coordinates. If the attachment has a {@link #sequence}, the region may
	 * be changed.
	 * <p>
	 * See <a href="http://esotericsoftware.com/spine-runtime-skeletons#World-transforms">World transforms</a> in the Spine
	 * Runtimes Guide.
	 * @param worldVertices The output world vertices. Must have a length >= <code>offset</code> + 8.
	 * @param offset The <code>worldVertices</code> index to begin writing values.
	 * @param stride The number of <code>worldVertices</code> entries between the value pairs written. */
	public void computeWorldVertices (Slot slot, float[] worldVertices, int offset, int stride) {
		if (sequence != null) sequence.apply(slot, this);

		float[] vertexOffset = this.offset;
		Bone bone = slot.getBone();
		float x = bone.getWorldX(), y = bone.getWorldY();
		float a = bone.getA(), b = bone.getB(), c = bone.getC(), d = bone.getD();
		float offsetX, offsetY;

		offsetX = vertexOffset[BRX];
		offsetY = vertexOffset[BRY];
		worldVertices[offset] = offsetX * a + offsetY * b + x; // br
		worldVertices[offset + 1] = offsetX * c + offsetY * d + y;
		offset += stride;

		offsetX = vertexOffset[BLX];
		offsetY = vertexOffset[BLY];
		worldVertices[offset] = offsetX * a + offsetY * b + x; // bl
		worldVertices[offset + 1] = offsetX * c + offsetY * d + y;
		offset += stride;

		offsetX = vertexOffset[ULX];
		offsetY = vertexOffset[ULY];
		worldVertices[offset] = offsetX * a + offsetY * b + x; // ul
		worldVertices[offset + 1] = offsetX * c + offsetY * d + y;
		offset += stride;

		offsetX = vertexOffset[URX];
		offsetY = vertexOffset[URY];
		worldVertices[offset] = offsetX * a + offsetY * b + x; // ur
		worldVertices[offset + 1] = offsetX * c + offsetY * d + y;
	}

	/** For each of the 4 vertices, a pair of <code>x,y</code> values that is the local position of the vertex.
	 * <p>
	 * See {@link #updateRegion()}. */
	public float[] getOffset () {
		return offset;
	}

	public float[] getUVs () {
		return uvs;
	}

	/** The local x translation. */
	public float getX () {
		return x;
	}

	public void setX (float x) {
		this.x = x;
	}

	/** The local y translation. */
	public float getY () {
		return y;
	}

	public void setY (float y) {
		this.y = y;
	}

	/** The local scaleX. */
	public float getScaleX () {
		return scaleX;
	}

	public void setScaleX (float scaleX) {
		this.scaleX = scaleX;
	}

	/** The local scaleY. */
	public float getScaleY () {
		return scaleY;
	}

	public void setScaleY (float scaleY) {
		this.scaleY = scaleY;
	}

	/** The local rotation. */
	public float getRotation () {
		return rotation;
	}

	public void setRotation (float rotation) {
		this.rotation = rotation;
	}

	/** The width of the region attachment in Spine. */
	public float getWidth () {
		return width;
	}

	public void setWidth (float width) {
		this.width = width;
	}

	/** The height of the region attachment in Spine. */
	public float getHeight () {
		return height;
	}

	public void setHeight (float height) {
		this.height = height;
	}

	public Color getColor () {
		return color;
	}

	public String getPath () {
		return path;
	}

	public void setPath (String path) {
		this.path = path;
	}

	public @Null Sequence getSequence () {
		return sequence;
	}

	public void setSequence (@Null Sequence sequence) {
		this.sequence = sequence;
	}

	public RegionAttachment copy () {
		return new RegionAttachment(this);
	}
}
