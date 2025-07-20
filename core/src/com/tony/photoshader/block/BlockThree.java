package com.tony.photoshader.block;

public class BlockThree extends BaseBlockActor{
/**
 *   **
 *    *
 *    *
 */

    @Override
    public void initData() {
        blockArrData = new int[3][2];
        blockArrData[0][0] = 1;
        blockArrData[0][1] = 1;
        blockArrData[1][0] = 0;
        blockArrData[1][1] = 1;
        blockArrData[2][0] = 0;
        blockArrData[2][1] = 1;
    }
}
