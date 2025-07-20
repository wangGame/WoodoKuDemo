package com.tony.photoshader.block;

public class BlockTwo extends BaseBlockActor{
    /**
     *  **
     *   **
     *
     */
    @Override
    public void initData() {
        index = 2;
        blockArrData = new int[2][3];
        blockArrData[0][0] = 1;
        blockArrData[0][1] = 1;
        blockArrData[0][2] = 0;
        blockArrData[1][0] = 0;
        blockArrData[1][1] = 1;
        blockArrData[1][2] = 1;
    }
}
