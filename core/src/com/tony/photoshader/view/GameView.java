package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.sun.source.tree.InstanceOfTree;
import com.tony.photoshader.block.BaseBlockActor;

public class GameView extends Group {
    private int[][] blockData;
    private Group boardGroup;
    private BlockPanel blockPanel;
    public GameView(){
        setDebug(true);
        this.blockData = new int[8][8];
        setSize(8*100,8*100);

        this.boardGroup = new Group();
        this.boardGroup.setSize(getWidth(),getHeight());
        addActor(boardGroup);
        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {
                GameViewBlockGroup actor = new GameViewBlockGroup();
                actor.setSize(100,100);
                actor.setPosition(i*100,i1*100);
                actor.setDebug(true);
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
        if (vector2.x>=-50&&vector2.y>=-50){
            int[][] data = baseBlockActor.getData();
            int startX = Math.abs((int) ((vector2.x+50) / 100));
            int startY = Math.abs((int) ((vector2.y+50) / 100));
            for (int i = 0; i < data.length; i++) {
                for (int i1 = 0; i1 < data[0].length; i1++) {
                    if (startX+i<8 && startY+i1<8&&startX+i>=0 && startY+i1>=0) {
                        if (data[i][i1] == 1) {
                            if (blockData[startX + i][startY + i1] == 1) {
                                flag = false;
                            }
                        }
                    }else {
                        flag = false;
                    }
                }
            }
        }else {
            flag = false;
        }
        return flag;
    }

    public void addTagetBlock(BaseBlockActor targetBlock) {
        float x = targetBlock.getX();
        float y = targetBlock.getY();
        Vector2 vector2 = new Vector2();
        vector2.set(x,y);
        BlockGroup blockGroup = (BlockGroup) targetBlock.getParent();
        blockGroup.setUsed(true);
        targetBlock.getParent().localToStageCoordinates(vector2);
        stageToLocalCoordinates(vector2);
        int startX = Math.abs((int) ((vector2.x+50) / 100));
        int startY = Math.abs((int) ((vector2.y+50) / 100));
        targetBlock.setPosition(vector2.x,vector2.y);

        targetBlock.addAction(
                Actions.sequence(
                        Actions.moveTo(startX * 100,startY * 100,0.2f),
                        Actions.run(()->{
                            int[][] data = targetBlock.getData();
                            for (int i = 0; i < data.length; i++) {
                                for (int i1 = 0; i1 < data[0].length; i1++) {
                                    int endX = startX + i;
                                    int endY = startY + i1;
                                    if (endX<8 && endY<8&&endX>=0 && endY>=0) {
                                        if (data[i][i1] == 1) {
                                            blockData[endX][endY] = 1;
                                            GameViewBlockGroup gameViewBlockGroup = boardGroup.findActor(endX + "" + endY);
                                            gameViewBlockGroup.setLabelValue(1);
                                        }
                                    }
                                }
                            }

                            if (blockPanel.checkStatus()) {
                                blockPanel.setBlock();
                            }

                             checkBlockActor();
                        }),
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

    public void setBlockPanel(BlockPanel blockPanel) {
        this.blockPanel = blockPanel;
    }
}
