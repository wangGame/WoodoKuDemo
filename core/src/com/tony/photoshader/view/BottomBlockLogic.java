package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tony.photoshader.block.BaseBlockActor;

public class BottomBlockLogic {
    private BottomBlockItem blockOne;
    private BottomBlockItem blockTwo;
    private BottomBlockItem blockThree;
    private Vector2 tempV2 = new Vector2();

    public BottomBlockLogic(GameView gameView, Group rootView) {
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
        blockOne.genBlock();
        blockTwo.genBlock();
        blockThree.genBlock();
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
