package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockActor1;

public class GameView extends Group {
    private int[][] blockData;
    public GameView(){
        setDebug(true);
        this.blockData = new int[8][8];
        setSize(8*100,8*100);

        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {
                Actor actor = new Actor();
                actor.setSize(100,100);
                actor.setPosition(i*100,i1*100);
                actor.setDebug(true);
                addActor(actor);
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
        if (vector2.x>=-50&&vector2.y>=-500){
            int[][] data = baseBlockActor.getData();
            int startX = Math.abs((int) ((vector2.x+50) / 100));
            int startY = Math.abs((int) ((vector2.y+50) / 100));
            for (int i = 0; i < data.length; i++) {
                for (int i1 = 0; i1 < data[0].length; i1++) {
                    if (startX+i<8 && startY+i1<8&&startX+i>=0 && startY+i1>=0) {
                        if (blockData[startX + i][startY + i1] == 1) {
                            flag = false;
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
        targetBlock.getParent().localToStageCoordinates(vector2);
        stageToLocalCoordinates(vector2);
        int startX = Math.abs((int) ((vector2.x+50) / 100));
        int startY = Math.abs((int) ((vector2.y+50) / 100));
        targetBlock.setPosition(vector2.x,vector2.y);
        targetBlock.addAction(Actions.moveTo(startX * 100,startY * 100,0.2f));
        addActor(targetBlock);
    }
}
