package com.tony.photoshader.ai;

import static com.tony.photoshader.constant.Constant.USED_BITS;



public class BitBoard {
    public final int a, b, c;

    public BitBoard(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public BitBoard copy() {
        return new BitBoard(a, b, c);
    }

    public BitBoard not(BitBoard bb) {
        return new BitBoard(~bb.a & USED_BITS, ~bb.b & USED_BITS, ~bb.c & USED_BITS);
    }

    public BitBoard and(BitBoard x, BitBoard y) {
        return new BitBoard(x.a & y.a, x.b & y.b, x.c & y.c);
    }

    public BitBoard or(BitBoard x, BitBoard y) {
        return new BitBoard(x.a | y.a, x.b | y.b, x.c | y.c);
    }

    public BitBoard xor(BitBoard x, BitBoard y) {
        return and(or(x, y), not(and(x, y)));
    }

    public BitBoard diff(BitBoard x, BitBoard y) {
        return new BitBoard(x.a & ~y.a, x.b & ~y.b, x.c & ~y.c);
    }

    public boolean isEmpty() {
        return a == 0 && b == 0 && c == 0;
    }

    public static boolean equal(BitBoard x, BitBoard y) {
        return x.a == y.a && x.b == y.b && x.c == y.c;
    }

    //有多少被占用
    public int count(BitBoard bb) {
        return popcount(bb.a) + popcount(bb.b) + popcount(bb.c);
    }

    private int popcount(int x) {
        x = x - ((x >>> 1) & 0x55555555);
        x = (x & 0x33333333) + ((x >>> 2) & 0x33333333);
        x = (x + (x >>> 4)) & 0x0F0F0F0F;
        x = x + (x >>> 8);
        x = x + (x >>> 16);
        return x & 0x7F;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                BitBoard mask = and(Blokie.row(r), Blokie.column(c));
                sb.append(and(this, mask).isEmpty() ? "." : "#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}