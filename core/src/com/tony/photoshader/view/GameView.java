package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.constant.BConstant;
import com.tony.photoshader.score.BScore;

public class GameView extends Group {
    private int[][] blockData;
    private Group boardGroup;
    private BottomBlockLogic bottomBlockLogic;
    public GameView(){
        this.blockData = new int[8][8];
        setSize(966,966);
        this.boardGroup = new Group();
        this.boardGroup.setSize(getWidth(),getHeight());
        addActor(boardGroup);
        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {
                GameViewBlockGroup actor = new GameViewBlockGroup();
                actor.setPosition(3 + i* BConstant.blockWidth,3 + i1* BConstant.blockWidth);
                actor.updateLabelPosition();
                boardGroup.addActor(actor);
                actor.setName(i+""+i1);
            }
        }
    }

    public boolean checkBlock(BaseBlockActor baseBlockActor) {
        Vector2 vector2 = new Vector2(baseBlockActor.getX(),baseBlockActor.getY());
        baseBlockActor.getParent().localToStageCoordinates(vector2);
        stageToLocalCoordinates(vector2);

        boolean flag = true;
        //开始检测
        if (vector2.x>=-BConstant.blockWidthHalf&&vector2.y>=-BConstant.blockWidthHalf){
            int startX = Math.abs((int) ((vector2.x+ BConstant.blockWidthHalf) / BConstant.blockWidth));
            int startY = Math.abs((int) ((vector2.y+ BConstant.blockWidthHalf) / BConstant.blockWidth));
            flag = check(baseBlockActor, startX,startY);
        }else {
            flag = false;
        }
        return flag;
    }

    private boolean check(BaseBlockActor baseBlockActor, int startX,int startY) {
        boolean flag = true;
        int[][] data = baseBlockActor.getData();
        for (int i = 0; i < data.length; i++) {
            for (int i1 = 0; i1 < data[0].length; i1++) {
                if (startX+i1<8 && startY+i<8&&startX+i1>=0 && startY+i>=0) {
                    if (data[i][i1] == 1) {
                        if (blockData[startX + i1][startY + i] == 1) {
                            flag = false;
                            return flag;
                        }
                    }
                }else {
                    flag = false;
                    return flag;
                }
            }
        }
        return flag;
    }

    public void addTagetBlock(BaseBlockActor targetBlock) {
        float x = targetBlock.getX();
        float y = targetBlock.getY();
        Vector2 vector2 = new Vector2();
        vector2.set(x,y);
        BottomBlockItem bottomBlockItem = (BottomBlockItem) targetBlock.getParent();
        bottomBlockItem.setUsed(true);
        targetBlock.getParent().localToStageCoordinates(vector2);
        stageToLocalCoordinates(vector2);
        int startX = Math.abs((int) ((vector2.x+ BConstant.blockWidthHalf) / BConstant.blockWidth));
        int startY = Math.abs((int) ((vector2.y+ BConstant.blockWidthHalf) / BConstant.blockWidth));
        targetBlock.setPosition(vector2.x,vector2.y);
        resetColor();
        targetBlock.addAction(
                Actions.sequence(
                        Actions.moveTo(startX * BConstant.blockWidth,startY * BConstant.blockWidth,0.06f),
                        Actions.run(()->{
                            int[][] data = targetBlock.getData();
                            for (int i = 0; i < data.length; i++) {
                                for (int i1 = 0; i1 < data[0].length; i1++) {
                                    int endX = startX + i1;
                                    int endY = startY + i;
                                    if (endX<8 && endY<8&&endX>=0 && endY>=0) {
                                        if (data[i][i1] == 1) {
                                            blockData[endX][endY] = 1;
                                            GameViewBlockGroup gameViewBlockGroup = boardGroup.findActor(endX + "" + endY);
                                            gameViewBlockGroup.setLabelValue(1,targetBlock.getBlockColorName());
                                        }
                                    }
                                }
                            }
                            if (bottomBlockLogic.checkStatus()) {
                                bottomBlockLogic.setBlock();
                            }
                             checkBlockActor();
                        })
                        ,
                        Actions.removeActor()
                )
        );
        addActor(targetBlock);
    }

    private void checkBlockActor() {
        //水平
        Array<Integer> shuiping = new Array<>();
        for (int i = 0; i < blockData.length; i++) {
            boolean flag = true;
            for (int i1 = 0; i1 < blockData[0].length; i1++) {
                if (blockData[i][i1] == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                shuiping.add(i);
            }
        }

        Array<Integer> shuizhi = new Array<>();
        for (int i = 0; i < blockData[0].length; i++) {
            boolean flag = true;
            for (int i1 = 0; i1 < blockData.length; i1++) {
                if (blockData[i1][i] == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                shuizhi.add(i);
            }
        }
        addAction(Actions.delay(0.3f,Actions.run(()->{
            int scoreValue = (shuizhi.size + shuiping.size) * BConstant.currentScoreBs;
            BScore.getInstance().updateScore(scoreValue);
        })));
        if (shuiping.size>0 ||shuizhi.size>0){
            BConstant.currentScoreBs += 10;
        }
        resetValue(shuiping,shuizhi);
    }

    private void resetValue(Array<Integer> shuiping, Array<Integer> shuizhi) {
        for (Integer i : shuiping) {
            for (int i1 = 0; i1 < blockData[0].length; i1++) {
                blockData[i][i1] = 0;
                GameViewBlockGroup blockGroup = boardGroup.findActor(i + "" + i1);
                blockGroup.resetLabelValue();
            }
        }

        for (Integer i : shuizhi) {
            for (int i1 = 0; i1 < blockData[0].length; i1++) {
                blockData[i1][i] = 0;
                GameViewBlockGroup blockGroup = boardGroup.findActor(i1 + "" + i);
                blockGroup.resetLabelValue();
            }
        }
    }

    public void setBlockPanel(BottomBlockLogic bottomBlockLogic) {
        this.bottomBlockLogic = bottomBlockLogic;
    }

    public boolean checkBlockAll(Array<BaseBlockActor> allNotUse) {
        for (int i = 0; i < blockData.length; i++) {
            for (int i1 = 0; i1 < blockData[0].length; i1++) {
                if (blockData[i][i1] == 0) {
                    for (BaseBlockActor baseBlockActor : allNotUse) {
                        if (check(baseBlockActor,i,i1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void resetColor() {
        for (int i = 0; i < blockData.length; i++) {
            for (int i1 = 0; i1 < blockData[0].length; i1++) {
                GameViewBlockGroup gameViewBlockGroup = boardGroup.findActor(i + "" + i1);
                gameViewBlockGroup.resetColor();
            }
        }
    }

    public void setCanMove(BaseBlockActor targetBlock) {
        float x = targetBlock.getX();
        float y = targetBlock.getY();
        Vector2 vector2 = new Vector2();
        vector2.set(x, y);

        targetBlock.getParent().localToStageCoordinates(vector2);
        stageToLocalCoordinates(vector2);

        int startX = Math.abs((int) ((vector2.x + BConstant.blockWidthHalf) / BConstant.blockWidth));
        int startY = Math.abs((int) ((vector2.y + BConstant.blockWidthHalf) / BConstant.blockWidth));

        int[][] data = targetBlock.getData();
        for (int i = 0; i < data.length; i++) {
            for (int i1 = 0; i1 < data[0].length; i1++) {
                int endX = startX + i1;
                int endY = startY + i;
                if (endX < 8 && endY < 8 && endX >= 0 && endY >= 0) {
                    if (data[i][i1] == 1) {
                        GameViewBlockGroup gameViewBlockGroup = boardGroup.findActor(endX + "" + endY);
                        gameViewBlockGroup.setCanMoveColor();
                    }
                }
            }
        }
    }


    public boolean checkArr(int[][] block) {
        int boardRows = blockData.length;
        int boardCols = blockData[0].length;
        int blockRows = block.length;
        int blockCols = block[0].length;

        for (int y = 0; y <= boardRows - blockRows; y++) {
            for (int x = 0; x <= boardCols - blockCols; x++) {
                if (canPlaceAt(blockData, block, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private  boolean canPlaceAt(int[][] board, int[][] block, int startX, int startY) {
        //位置x y 是否存在被挡住的
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if (block[i][j] == 1) {
                    int boardY = startY + i;
                    int boardX = startX + j;
                    if (board[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int[][] getBoardCopy() {
        int cpy[][] = new int[blockData.length][blockData[0].length];
        for (int i = 0; i < blockData.length; i++) {
            for (int i1 = 0; i1 < blockData[0].length; i1++) {
                cpy[i][i1] = blockData[i][i1];
            }
        }
        return cpy;
    }
}
