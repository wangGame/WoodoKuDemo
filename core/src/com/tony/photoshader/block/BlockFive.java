package com.tony.photoshader.block;

public class BlockFive extends BaseBlockActor{
/**
 *  *
 *   *
 */

    @Override
    public void initData() {
        blockArrData = new int[2][2];
        blockArrData[0][0] = 1;
        blockArrData[0][1] = 0;
        blockArrData[1][0] = 0;
        blockArrData[1][1] = 1;
    }
}
