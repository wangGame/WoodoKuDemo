package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tony.photoshader.block.BaseBlockActor;

public class BlockPanel extends Group {
    private BlockGroup blockOne;
    private BlockGroup blockTwo;
    private BlockGroup blockThree;
    private Vector2 tempV2 = new Vector2();

    public BlockPanel(){
        setSize(1080,300);
        setDebug(true);

        blockOne = new BlockGroup();
        blockOne.setPosition(180,150, Align.center);
        blockOne.setDebug(true);
        addActor(blockOne);

        blockTwo = new BlockGroup();
        blockTwo.setPosition(360+180,150, Align.center);
        blockTwo.setDebug(true);
        addActor(blockTwo);

        blockThree = new BlockGroup();
        blockThree.setPosition(720+180,150, Align.center);
        blockThree.setDebug(true);
        addActor(blockThree);

    }

    public void setBlock(){
        blockOne.genBlock();
        blockTwo.genBlock();
        blockThree.genBlock();
    }

    public BlockGroup checkTouch(Vector2 touchDownV2) {
        {
            tempV2.set(touchDownV2.x,touchDownV2.y);
            blockOne.stageToLocalCoordinates(tempV2);
            Actor hit = blockOne.hit(tempV2.x, tempV2.y, true);
            if (hit!=null){
                return blockOne;
            }
        }
        {
            tempV2.set(touchDownV2.x,touchDownV2.y);
            blockTwo.stageToLocalCoordinates(tempV2);
            Actor hit = blockTwo.hit(tempV2.x, tempV2.y, true);
            if (hit!=null){
                return blockTwo;
            }
        }
        {
            tempV2.set(touchDownV2.x,touchDownV2.y);
            blockThree.stageToLocalCoordinates(tempV2);
            Actor hit = blockThree.hit(tempV2.x, tempV2.y, true);
            if (hit!=null){
                return blockThree;
            }
        }

        return null;
    }

    public boolean checkStatus() {
        if (!blockOne.checkUsed()) {
            return false;
        }
        if (!blockTwo.checkUsed()) {
            return false;
        }
        if (!blockThree.checkUsed()) {
            return false;
        }
        return true;
    }

    public Array<BaseBlockActor> getAllNotUse(){
        Array<BaseBlockActor> actors = new Array<>();
        if (!blockOne.checkUsed()) {
            actors.add(blockOne.getBlock());
        }
        if (!blockTwo.checkUsed()) {
            actors.add(blockTwo.getBlock());
        }
        if (!blockThree.checkUsed()) {
            actors.add(blockThree.getBlock());
        }
        return actors;
    }
}
