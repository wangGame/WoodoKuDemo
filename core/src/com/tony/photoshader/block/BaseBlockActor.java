package com.tony.photoshader.block;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
        setSize(arr.length*100,arr[0].length*100);
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = 0; i1 < arr[0].length; i1++) {
                if (arr[i][i1] == 1) {
                    BlockItem actor = new BlockItem();
                    actor.setSize(100, 100);
                    actor.setPosition(i * 100, i1 * 100);
                    actor.setDebug(true);
                    addActor(actor);
                }
            }
        }
    }

    public int[][] getData() {
        return arr;
    }
}
