package com.tony.photoshader.block;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class BlockEightTeen extends BaseBlockActor{
    /**
     *
     *   **
     *    **
     *
     *
     *
     */

    @Override
    public void initData() {
        index = 18;
        blockArrData = new int[2][3];
        blockArrData[0][0] = 1;
        blockArrData[0][1] = 1;
        blockArrData[0][2] = 0;
        blockArrData[1][0] = 0;
        blockArrData[1][1] = 1;
        blockArrData[1][2] = 1;

    }
}
