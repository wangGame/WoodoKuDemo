package com.tony.photoshader.block;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.constant.BConstant;

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
    protected int index;

    public BaseBlockActor(){
        initData();
    }

    public abstract void initData();

    public void createBlock() {
        setSize(blockArrData[0].length* BConstant.blockWidth, blockArrData.length* BConstant.blockWidth);
        setOrigin(Align.center);
        setScale(0.6f);
        int blockNameIndex = (int) (Math.random() * 6 + 1);
        blockColorName = "block"+blockNameIndex+".png";
        for (int y = 0; y < blockArrData.length; y++) {
            for (int x = 0; x < blockArrData[0].length; x++) {
                if (blockArrData[y][x] == 1) {
                    BlockItem actor = new BlockItem(blockColorName);
                    actor.setSize(BConstant.blockWidth, BConstant.blockWidth);
                    actor.setPosition(x * BConstant.blockWidth,  y* BConstant.blockWidth);
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

    public int getIndex() {
        return index;
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < blockArrData.length; i++) {
            for (int i1 = 0; i1 < blockArrData[0].length; i1++) {
                if (blockArrData[i][i1] == 1){
                    score += 1;
                }
            }
        }
        return score;
    }
}
