package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockManager;

import java.util.Comparator;

public class BottomBlockLogic {
    private BottomBlockItem blockOne;
    private BottomBlockItem blockTwo;
    private BottomBlockItem blockThree;
    private Vector2 tempV2 = new Vector2();
    private GameView gameView;

    public BottomBlockLogic(GameView gameView, Group rootView) {
        this.gameView = gameView;
        Group blockPanel = rootView.findActor("blockPanel");
        Group blockOnePanel = blockPanel.findActor("blockOne");
        blockOne = new BottomBlockItem(gameView);
        blockOne.setPosition(100, 100, Align.center);
        blockOnePanel.addActor(blockOne);

        Group blockTwoPanel = blockPanel.findActor("blockTwo");
        blockTwo = new BottomBlockItem(gameView);
        blockTwo.setPosition(100, 100, Align.center);
        blockTwoPanel.addActor(blockTwo);

        Group blockThreePanel = blockPanel.findActor("blockThree");
        blockThree = new BottomBlockItem(gameView);
        blockThree.setPosition(100, 100, Align.center);
        blockThreePanel.addActor(blockThree);

    }

    public void setBlock() {
        Array<Array<BaseBlockActor>> arrays = tryFindThreeCanUp();
        if (arrays.size>0){
            Array<BaseBlockActor> baseBlockActors = arrays.get(0);
            blockOne.setBlock(baseBlockActors.get(0));
            blockTwo.setBlock(baseBlockActors.get(1));
            blockThree.setBlock(baseBlockActors.get(2));
        }else {
            blockOne.genBlock();
            blockTwo.genBlock();
            blockThree.genBlock();
        }
    }

    private Array<Array<BaseBlockActor>> tryFindThreeCanUp() {
        // 尝试所有组合，找出一个能依次放下的三个块组合
        Array<Array<BaseBlockActor>> array = new Array<>();
//        先随机
        {
            System.out.println("yuanl de ");
            int[][] board = gameView.getBoardCopy();
            for (int i = 0; i < board.length; i++) {
                for (int i1 = 0; i1 < board[0].length; i1++) {
                    System.out.print(board[i1][board.length-1-i]+"     ");
                }
                System.out.println();
            }
            System.out.println("======================");
        }

        for (int i = 0; i < 5; i++) {
            for (int i1 = 0; i1 < 5; i1++) {
                for (int i2 = 0; i2 < 5; i2++) {
                    int[][] board = gameView.getBoardCopy();
                    int num1 = BlockManager.randomNum();
                    BaseBlockActor baseBlockActorOne = BlockManager.getBaseBlockActor(num1);
                    PlacementResult r1 = tryPlaceAndClear(board,baseBlockActorOne.getData());
                    if (!r1.success) continue;
                    int num2 = BlockManager.randomNum();
                    while (num2==num1){
                        num2 = BlockManager.randomNum();
                    }
                    BaseBlockActor baseBlockActorTwo = BlockManager.getBaseBlockActor(num2);
                    PlacementResult r2 = tryPlaceAndClear(r1.newBoard,baseBlockActorTwo.getData());
                    if (!r2.success) continue;

                    int num3 = BlockManager.randomNum();
                    while (num3==num1||num2==num3){
                        num3 = BlockManager.randomNum();
                    }

                    BaseBlockActor baseBlockActorThree = BlockManager.getBaseBlockActor(num3);
                    PlacementResult r3 = tryPlaceAndClear(r2.newBoard,baseBlockActorThree.getData());
                    if (!r3.success) continue;
                    Array<BaseBlockActor> arrayTemp = new Array<>();
                    array.add(arrayTemp);
                    arrayTemp.add(baseBlockActorOne);
                    arrayTemp.add(baseBlockActorTwo);
                    arrayTemp.add(baseBlockActorThree);
                    array.add(arrayTemp);

                    System.out.println("block from 1");

                    return array;
                }
            }
        }


//        在使用遍历
        for (int i = 1; i < 18; i++) {
            for (int j = 1; j < 18; j++) {
                for (int k = 1; k < 18; k++) {
                    if (i == j || j == k || i == k) continue; // 避免完全重复模板
                    int[][] board = gameView.getBoardCopy();
                    PlacementResult r1 = tryPlaceAndClear(board, BlockManager.getBaseBlockActor(i).getData());
                    Array<BaseBlockActor> arrayTemp = new Array<>();
                    array.add(arrayTemp);
                    if (!r1.success) continue;
                    arrayTemp.add(BlockManager.getBaseBlockActor(i));
                    PlacementResult r2 = tryPlaceAndClear(r1.newBoard,BlockManager.getBaseBlockActor(j).getData());
                    if (!r2.success) continue;
                    arrayTemp.add(BlockManager.getBaseBlockActor(i));
                    PlacementResult r3 = tryPlaceAndClear(r2.newBoard,BlockManager.getBaseBlockActor(k).getData());
                    if (!r3.success) continue;
                    arrayTemp.add(BlockManager.getBaseBlockActor(i));
                    System.out.println("block item 2");
                    return array;
                }
            }
        }
        array.sort(new Comparator<Array<BaseBlockActor>>() {
            @Override
            public int compare(Array<BaseBlockActor> o1, Array<BaseBlockActor> o2) {
                return o1.size - o2.size;
            }
        });

        Array<BaseBlockActor> baseBlockActors = array.get(0);
        if (baseBlockActors.size==0){
            baseBlockActors.add(BlockManager.getBaseBlockActor());
            baseBlockActors.add(BlockManager.getBaseBlockActor());
            baseBlockActors.add(BlockManager.getBaseBlockActor());
        }else if (baseBlockActors.size == 1){
            baseBlockActors.add(BlockManager.getBaseBlockActor());
            baseBlockActors.add(BlockManager.getBaseBlockActor());
        }else if (baseBlockActors.size == 2){
            baseBlockActors.add(BlockManager.getBaseBlockActor());
        }

        System.out.println("block from 3");
        return array;
    }

    public PlacementResult tryPlaceAndClear(int[][] board, int[][] block) {
        int rows = board.length;
        int cols = board[0].length;
        int br = block.length;
        int bc = block[0].length;

        for (int y = 0; y <= rows - bc; y++) {
            for (int x = 0; x <= cols - br; x++) {
                if (canPlaceAt(board, block, x, y)) {
                    int[][] placed =  deepCopy(board);
                    for (int i = 0; i < bc; i++) {
                        for (int j = 0; j < br; j++) {
                            if (block[j][i] == 1) {
                                placed[y + i][x + j] = 1;
                            }
                        }
                    }
                    placed = clearFullLines(placed);

                    {
                        System.out.println("=======jjjj de ");
                        for (int i = 0; i < board.length; i++) {
                            for (int i1 = 0; i1 < board[0].length; i1++) {
                                System.out.print(placed[i1][board.length-1-i]+"     ");
                            }
                            System.out.println();
                        }
                        System.out.println("======================");
                    }

                    PlacementResult result = new PlacementResult();
                    result.success = true;
                    result.newBoard = placed;
                    return result;
                }
            }
        }
        PlacementResult result = new PlacementResult();
        result.success = false;
        result.newBoard = board;
        return result;
    }

    private static int[][] clearFullLines(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        boolean[] fullRow = new boolean[rows];
        boolean[] fullCol = new boolean[cols];

        for (int i = 0; i < rows; i++) {
            boolean full = true;
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) fullRow[i] = true;
        }

        for (int j = 0; j < cols; j++) {
            boolean full = true;
            for (int i = 0; i < rows; i++) {
                if (board[i][j] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) fullCol[j] = true;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (fullRow[i] || fullCol[j]) {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[0].length);
        }
        return copy;
    }

    public static class PlacementResult {
        public boolean success;
        public int[][] newBoard;
    }


    private static boolean canPlaceAt(int[][] board, int[][] block, int startX, int startY) {
        for (int i = 0; i < block[0].length; i++) {
            for (int j = 0; j < block.length; j++) {
                if (block[j][i] == 1) {
                    if (board[startY + i][startX + j] != 0) return false;
                }
            }
        }
        return true;
    }


    public BottomBlockItem checkTouch(Vector2 touchDownV2) {
        {
            tempV2.set(touchDownV2.x, touchDownV2.y);
            blockOne.stageToLocalCoordinates(tempV2);
            Actor hit = blockOne.hit(tempV2.x, tempV2.y, true);
            if (hit != null) {
                return blockOne;
            }
        }
        {
            tempV2.set(touchDownV2.x, touchDownV2.y);
            blockTwo.stageToLocalCoordinates(tempV2);
            Actor hit = blockTwo.hit(tempV2.x, tempV2.y, true);
            if (hit != null) {
                return blockTwo;
            }
        }
        {
            tempV2.set(touchDownV2.x, touchDownV2.y);
            blockThree.stageToLocalCoordinates(tempV2);
            Actor hit = blockThree.hit(tempV2.x, tempV2.y, true);
            if (hit != null) {
                return blockThree;
            }
        }
        return null;
    }

    public boolean checkStatus() {
        return getAllNotUse().size<=0;
    }

    public Array<BaseBlockActor> getAllNotUse() {
        Array<BaseBlockActor> actorsTemp = new Array<>();
        if (!blockOne.checkUsed()) {
            actorsTemp.add(blockOne.getBlock());
        }
        if (!blockTwo.checkUsed()) {
            actorsTemp.add(blockTwo.getBlock());
        }
        if (!blockThree.checkUsed()) {
            actorsTemp.add(blockThree.getBlock());
        }
        return actorsTemp;
    }
}
