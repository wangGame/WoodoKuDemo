package com.tony.photoshader.block;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.tony.photoshader.constant.Constant;

/**
 *   ****
 *
 *    *
 *   **
 *   *
 *
 *   *
 *   ***
 *    *
 *   ***
 *
 *    *
 *    *
 *
 *
 *    ***
 *      *
 *      *
 *
 *    ***
 *    ***
 *    ***
 *
 *
 *
 */

public abstract class BaseBlockActor extends Group {
    protected int arr[][];

    public BaseBlockActor(){
        initData();
    }

    public abstract void initData();

    public void createBlock() {
//        Label label = new Label(getClass().getSimpleName()+"", new Label.LabelStyle(){{
//            font = Asset.getAsset().loadBitFont("font/Krub-Bold_redStroke_52.fnt");
//        }});
//        addActor(label);
        setSize(arr[0].length*Constant.blockWidth,arr.length*Constant.blockWidth);
        setDebug(true);
        setOrigin(Align.center);
        setScale(0.8f);
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = 0; i1 < arr[0].length; i1++) {
                if (arr[i][i1] == 1) {
                    BlockItem actor = new BlockItem();
                    actor.setSize(Constant.blockWidth, Constant.blockWidth);
                    actor.setPosition(i1 * Constant.blockWidth,  i* Constant.blockWidth);
                    actor.setDebug(true);
                    addActor(actor);
                }
            }
        }
    }

    public int[][] getData() {
        return arr;
    }

    public int getArrWidth(){
        return arr[0].length/2-1;
    }

    public int getArrHeight(){
        return arr.length/2-1;
    }

}
