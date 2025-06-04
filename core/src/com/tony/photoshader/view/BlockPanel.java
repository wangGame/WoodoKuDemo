package com.tony.photoshader.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.constant.Constant;
import com.tony.photoshader.block.BaseBlockActor;

public class BlockPanel extends Group {
    private BlockGroup blockOne;
    private BlockGroup blockTwo;
    private BlockGroup blockThree;

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


    private Vector2 vv = new Vector2();
    public BlockGroup checkTouch(Vector2 touchDownV2) {
        {
            vv.set(touchDownV2.x,touchDownV2.y);
            blockOne.stageToLocalCoordinates(vv);
            Actor hit = blockOne.hit(vv.x, vv.y, true);
            if (hit!=null){
                return blockOne;
            }
        }
        {
            vv.set(touchDownV2.x,touchDownV2.y);
            blockTwo.stageToLocalCoordinates(vv);
            Actor hit = blockTwo.hit(vv.x, vv.y, true);
            if (hit!=null){
                return blockTwo;
            }
        }
        {
            vv.set(touchDownV2.x,touchDownV2.y);
            blockThree.stageToLocalCoordinates(vv);
            Actor hit = blockThree.hit(vv.x, vv.y, true);
            if (hit!=null){
                return blockThree;
            }
        }

        return null;
    }
}
