package com.tony.photoshader.block;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class BlockEight extends BaseBlockActor{
    /**
     *   *
     *    *
     *     *
     *
     */

    @Override
    public void initData() {
        blockArrData = new int[3][3];
        blockArrData[0][0] = 1;
        blockArrData[0][1] = 0;
        blockArrData[0][2] = 0;
        blockArrData[1][0] = 0;
        blockArrData[1][1] = 1;
        blockArrData[1][2] = 0;
        blockArrData[2][0] = 0;
        blockArrData[2][1] = 0;
        blockArrData[2][2] = 1;
    }
}
