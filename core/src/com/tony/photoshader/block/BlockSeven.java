package com.tony.photoshader.block;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class BlockSeven extends BaseBlockActor{
    /**
     *   **
     *    *
     *    **
     *
     */

    @Override
    public void initData() {
        arr = new int[3][3];
        arr[0][0] = 1;arr[0][1] = 1;arr[0][1] = 0;
        arr[1][0] = 0;arr[1][1] = 1;arr[0][1] = 0;
        arr[1][0] = 0;arr[1][1] = 1;arr[0][1] = 1;
    }
}
