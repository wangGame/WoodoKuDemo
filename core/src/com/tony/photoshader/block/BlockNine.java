package com.tony.photoshader.block;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class BlockNine extends BaseBlockActor {
    /**
     * **
     * *
     */

    @Override
    public void initData() {
        blockArrData = new int[2][2];
        blockArrData[0][0] = 1;
        blockArrData[0][1] = 1;
        blockArrData[1][0] = 1;
        blockArrData[1][1] = 0;
    }
}
