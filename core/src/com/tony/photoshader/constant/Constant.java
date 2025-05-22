package com.tony.photoshader.constant;

import com.tony.photoshader.ai.BitBoard;

public class Constant {
    public static final int USED_BITS = 0x7FFFFFF;
    public static final BitBoard EMPTY = new BitBoard(0, 0, 0);
    public static final BitBoard FULL = new BitBoard(USED_BITS, USED_BITS, USED_BITS);
    public static final int ROW_0 = 0x1FF;
    public static final int ROW_2 = ROW_0 << 18;
    public static final int LEFT_BITS = 1 | (1 << 9) | (1 << 18);
    public static final int RIGHT_BITS = LEFT_BITS << 8;
    public static final int TOP_LEFT_CUBE = 0x7 | (0x7 << 9) | (0x7 << 18);
    public static final int INF_SCORE = 9999999;
}
