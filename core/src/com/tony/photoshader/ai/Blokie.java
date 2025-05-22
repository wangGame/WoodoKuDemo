package com.tony.photoshader.ai;


import static com.tony.photoshader.constant.Constant.LEFT_BITS;
import static com.tony.photoshader.constant.Constant.RIGHT_BITS;
import static com.tony.photoshader.constant.Constant.ROW_0;
import static com.tony.photoshader.constant.Constant.ROW_2;
import static com.tony.photoshader.constant.Constant.TOP_LEFT_CUBE;
import static com.tony.photoshader.constant.Constant.USED_BITS;

import com.tony.photoshader.constant.Constant;

import java.util.*;

public class Blokie {
    private BlokieAPI blokieAPI;
    // precomputed rows, columns, and cubes
    public static final BitBoard[] ROWS = new BitBoard[9];
    public static final BitBoard[] COLS = new BitBoard[9];
    public static final BitBoard[] CUBES = new BitBoard[9];

    public Blokie(){
        this.blokieAPI = new BlokieAPI(this);
        for (int i = 0; i < 9; i++) {
            ROWS[i] = generateRow(i);
            COLS[i] = generateColumn(i);
            CUBES[i] = generateCube(i);
        }
    }

    public static BitBoard row(int r) {
        return ROWS[r];
    }

    public static BitBoard column(int c) {
        return COLS[c];
    }

    public BitBoard cube(int i) {
        return CUBES[i];
    }

    private BitBoard generateRow(int r) {
        int[] result = new int[]{0, 0, 0};
        int m = r % 3;
        result[r / 3] = ROW_0 << (m * 9);
        return new BitBoard(result[0], result[1], result[2]);
    }

    private BitBoard generateColumn(int c) {
        int bits = LEFT_BITS << c;
        return new BitBoard(bits, bits, bits);
    }

    private BitBoard generateCube(int i) {
        int[] result = new int[]{0, 0, 0};
        result[i / 3] = TOP_LEFT_CUBE << ((i % 3) * 3);
        return new BitBoard(result[0], result[1], result[2]);
    }


    public BitBoard shiftLeft(BitBoard bitBoard) {
        return new BitBoard((bitBoard.a & ~LEFT_BITS) >>> 1, (bitBoard.b & ~LEFT_BITS) >>> 1, (bitBoard.c & ~LEFT_BITS) >>> 1);
    }

    public BitBoard shiftRight(BitBoard bitBoard) {
        return new BitBoard((bitBoard.a & ~RIGHT_BITS) << 1, (bitBoard.b & ~RIGHT_BITS) << 1, (bitBoard.c & ~RIGHT_BITS) << 1);
    }

    public BitBoard shiftUp(BitBoard bitBoard) {
        return new BitBoard(
                (bitBoard.a >>> 9) | ((bitBoard.b & ROW_0) << 18),
                (bitBoard.b >>> 9) | ((bitBoard.c & ROW_0) << 18),
                bitBoard.c >>> 9
        );
    }

    public BitBoard shiftDown(BitBoard bitBoard) {
        return new BitBoard(
                (bitBoard.a << 9) & USED_BITS,
                ((bitBoard.b << 9) | ((bitBoard.a & ROW_2) >>> 18)) & USED_BITS,
                ((bitBoard.c << 9) | ((bitBoard.b & ROW_2) >>> 18)) & USED_BITS
        );
    }



    public BitBoard rotate(BitBoard bitBoard) {
        BitBoard result = Constant.EMPTY;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                BitBoard mask = bitBoard.and(row(r), column(c));
                if (!bitBoard.and(bitBoard, mask).isEmpty()) {
                    int rotatedR = c;
                    int rotatedC = 8 - r;
                    result = bitBoard.or(result, bitBoard.and(row(rotatedR), column(rotatedC)));
                }
            }
        }
        return result;
    }

    public BitBoard mirror(BitBoard bitBoard) {
        BitBoard result = Constant.EMPTY;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                BitBoard mask = bitBoard.and(row(r), column(c));
                if (!bitBoard.and(bitBoard, mask).isEmpty()) {
                    result = bitBoard.or(result, bitBoard.and(row(r), column(8 - c)));
                }
            }
        }
        return result;
    }

    public List<BitBoard> getAllTransformations(BitBoard bitBoard) {
        List<BitBoard> transformations = new ArrayList<>();
        BitBoard rotated = bitBoard;
        for (int i = 0; i < 4; i++) {
            rotated = rotate(rotated);
            transformations.add(rotated);
            transformations.add(mirror(rotated));
        }
        return transformations;
    }

    public BitBoard performClears(BitBoard board) {
        BitBoard toRemove = Constant.EMPTY;
        for (int i = 0; i < 9; i++) {
            BitBoard row = row(i);
            BitBoard col = column(i);
            BitBoard cb = cube(i);
            if (isSubset(board, row)) toRemove = board.or(toRemove, row);
            if (isSubset(board, col)) toRemove = board.or(toRemove, col);
            if (isSubset(board, cb)) toRemove = board.or(toRemove, cb);
        }
        if (toRemove.isEmpty()) return board;
        return board.diff(board, toRemove);
    }

    public boolean isSubset(BitBoard superset, BitBoard subset) {
        return (subset.a & ~superset.a) == 0 && (subset.b & ~superset.b) == 0 && (subset.c & ~superset.c) == 0;
    }

    public boolean isDisjoint(BitBoard a, BitBoard b) {
        return (a.a & b.a) == 0 && (a.b & b.b) == 0 && (a.c & b.c) == 0;
    }

    public List<BitBoard> getNextBoards(BitBoard board, BitBoard piece) {
        List<BitBoard> result = new ArrayList<>();
        if (piece.isEmpty()) {
            result.add(board);
            return result;
        }

        BitBoard current = piece.copy();
        BitBoard col8 = column(8);
        BitBoard row8 = row(8);
        BitBoard left = current;

        while (true) {
            if (isDisjoint(board, current)) {
                BitBoard placed = board.or(board, current);
                result.add(performClears(placed));
            }
            if (!isDisjoint(current, col8)) {
                if (!isDisjoint(left, row8)) break;
                left = shiftDown(left);
                current = left;
            } else {
                current = shiftRight(current);
            }
        }
        return result;
    }
 
    public int getEval(BitBoard bitBoard) {
        int OCCUPIED_CENTER_SQUARE = 1607;
        int OCCUPIED_SIDE_SQUARE = 2000;
        int OCCUPIED_CORNER_SQUARE = 3067;
        int CENTER_CUBE = 204;
        int SIDE_CUBE = 1358;
        int CORNER_CUBE = 908;

        int result = 0;

        for (int i = 0; i < 9; ++i) {
            BitBoard cb = bitBoard.and(cube(i), bitBoard);
            if (!cb.isEmpty()) {
                int cnt = bitBoard.count(cb);
                if (i == 4) {
                    result += CENTER_CUBE + OCCUPIED_CENTER_SQUARE * cnt;
                } else if (i == 1 || i == 3 || i == 5 || i == 7) {
                    result += SIDE_CUBE + OCCUPIED_SIDE_SQUARE * cnt;
                } else {
                    result += CORNER_CUBE + OCCUPIED_CORNER_SQUARE * cnt;
                }
            }
        }

        // Heuristic simplification: higher count = higher penalty
        // This encourages fewer blocks left on board.
        result += bitBoard.count(bitBoard) * 10;

        return result;
    }


    public BitBoard[] PIECES = {
            new BitBoard(1, 0, 0),
            new BitBoard(3, 0, 0),
            new BitBoard(513, 0, 0),
            new BitBoard(1025, 0, 0),
            new BitBoard(514, 0, 0),
            new BitBoard(7, 0, 0),
            new BitBoard(262657, 0, 0),
            new BitBoard(1049601, 0, 0),
            new BitBoard(263172, 0, 0),
            new BitBoard(515, 0, 0)
            // ... continue adding from the original JS PIECES if needed
    };

//    public BitBoard getRandomPiece(Random rng) {
//        return PIECES[rng.nextInt(PIECES.length)].copy();
//    }

//    public BitBoard[] getRandomPieceSet(Random rng) {
//        return new BitBoard[]{ getRandomPiece(rng), getRandomPiece(rng), getRandomPiece(rng) };
//    }

    public GameState[] aiMakeMove(GameState game, BitBoard[] pieceSet, Random rng) {
        int bestEval = Integer.MAX_VALUE;
        GameState[] bestStates = null;

        for (BitBoard bitBoard : pieceSet) {
            for (BitBoard b0 : getNextBoards(game.getBoard(), bitBoard)) {
                int s0 = game.getScore() + b0.count(b0.diff(b0, game.getBoard()));
                for (BitBoard p1 : pieceSet) {
                    if (p1 == bitBoard) continue;
                    for (BitBoard b1 : getNextBoards(b0, p1)) {
                        int s1 = s0 + b0.count(b0.diff(b1, b0));
                        for (BitBoard p2 : pieceSet) {
                            if (p2 == bitBoard || p2 == p1) continue;
                            for (BitBoard b2 : getNextBoards(b1, p2)) {
                                int s2 = s1 + bitBoard.count(bitBoard.diff(b2, b1));
                                int eval = getEval(b2);
                                if (eval < bestEval) {
                                    bestEval = eval;
                                    bestStates = new GameState[]{
                                            new GameState(b0, bitBoard, bitBoard, false, s0),
                                            new GameState(b1, p1, p1, false, s1),
                                            new GameState(b2, p2, p2, false, s2)
                                    };
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestStates;
    }

    public GameState getNewGame() {
        return new GameState(Constant.EMPTY, Constant.EMPTY, Constant.EMPTY, false, 0);
    }
    
    public BitBoard getRandomPiece(Random rng) {
        return PIECES[rng.nextInt(PIECES.length)].copy();
    }

    public BitBoard[] getRandomPieceSet(Random rng) {
        return new BitBoard[]{ getRandomPiece(rng), getRandomPiece(rng), getRandomPiece(rng) };
    }

    public BitBoard centerPiece(BitBoard bitBoard) {
        int height = 0;
        int width = 0;
        for (int i = 0; i < 9; i++) {
            if (!bitBoard.and(bitBoard, row(i)).isEmpty()) height = i + 1;
            if (!bitBoard.and(bitBoard, column(i)).isEmpty()) width = i + 1;
        }
        for (int i = 0; i < (5 - width) / 2; i++) {
            bitBoard = shiftRight(bitBoard);
        }
        for (int i = 0; i < (5 - height) / 2; i++) {
            bitBoard = shiftDown(bitBoard);
        }
        return bitBoard;
    }

    public BitBoard leftTopJustifyPiece(BitBoard bitBoard) {
        while (bitBoard.and(bitBoard, row(0)).isEmpty()) {
            bitBoard = shiftUp(bitBoard);
        }
        while (bitBoard.and(bitBoard, column(0)).isEmpty()) {
            bitBoard = shiftLeft(bitBoard);
        }
        return bitBoard;
    }

    public int getMoveScore(boolean previousWasClear, BitBoard prev, BitBoard placement, BitBoard after) {
        int placedCount = prev.count(prev.diff(after, prev));
        BitBoard cleared = prev.diff(prev.or(prev, placement), after);
        int combo = getComboMagnitude(cleared);

        int score = placedCount;
        if (combo == 0) return score;
        if (previousWasClear) score += 9;

        if (combo <= 2) score += 18 * combo;
        else if (combo <= 4) score += 36 * combo;
        else if (combo <= 7) score += 54 * combo;
        else score += 72 * combo;

        return score;
    }

    public int getComboMagnitude(BitBoard bitBoard) {
        int result = 0;
        for (int i = 0; i < 9; i++) {
            if (bitBoard.equal(row(i), bitBoard.and(row(i), bitBoard))) result++;
            if (bitBoard.equal(column(i), bitBoard.and(column(i), bitBoard))) result++;
            if (bitBoard.equal(cube(i), bitBoard.and(cube(i), bitBoard))) result++;
        }
        return result;
    }

    public boolean isOver(GameState game) {
        return BitBoard.equal(game.getBoard(), Constant.FULL);
    }

    // === TEST HARNESS ===
    public void runBasicTests() {
        Random rng = new Random(42);

        GameState game = blokieAPI.getNewGame();
        int totalMoves = 0;

        while (!blokieAPI.isOver(game) && totalMoves < 100) {
            BitBoard[] pieceSet = blokieAPI.getRandomPieceSet(rng);
            GameState[] nextStates = blokieAPI.getAIMove(game, pieceSet, rng);

            if (nextStates == null || nextStates.length < 3) {
                System.out.println("AI failed to find valid moves.");
                break;
            }

            game = nextStates[2];
            totalMoves++;
            System.out.println("Move " + totalMoves + " | Score: " + game.getScore());
            System.out.println(game.getBoard());
        }

        System.out.println("Final Score: " + game.getScore());
        System.out.println("Game Over: " + blokieAPI.isOver(game));
    }

    // Add more methods here to support: shiftLeft, shiftRight, shiftUp, shiftDown,
    // game logic, random piece generation, AI, and scoring as in the original JS code.
}
