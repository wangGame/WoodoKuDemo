package com.tony.photoshader.block;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.constant.Constant;

/**
 *   ****
 *
 *    *
 *   **
 *   *
 *
 *   *
 *   ***
 *    *
 *   ***
 *
 *    *
 *    *
 *
 *
 *    ***
 *      *
 *      *
 *
 *    ***
 *    ***
 *    ***
 *
 *
 *
 */

public abstract class BaseBlockActor extends Group {
    protected int blockArrData[][];
    protected String blockColorName;

    public BaseBlockActor(){
        initData();
    }

    public abstract void initData();

    public void createBlock() {
        setSize(blockArrData[0].length*Constant.blockWidth, blockArrData.length*Constant.blockWidth);
        setOrigin(Align.center);
        setScale(0.6f);
        int blockNameIndex = (int) (Math.random() * 6 + 1);
        blockColorName = "block"+blockNameIndex+".png";
        for (int y = 0; y < blockArrData.length; y++) {
            for (int x = 0; x < blockArrData[0].length; x++) {
                if (blockArrData[y][x] == 1) {
                    BlockItem actor = new BlockItem(blockColorName);
                    actor.setSize(Constant.blockWidth, Constant.blockWidth);
                    actor.setPosition(x * Constant.blockWidth,  y* Constant.blockWidth);
                    addActor(actor);
                }
            }
        }
    }

    public int[][] getData() {
        return blockArrData;
    }

    public String getBlockColorName() {
        return blockColorName;
    }
}
