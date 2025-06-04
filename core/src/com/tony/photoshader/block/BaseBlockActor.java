package com.tony.photoshader.block;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

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
        setSize(arr.length*100-10,arr[0].length*100-10);
        setDebug(true);
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = 0; i1 < arr[0].length; i1++) {
                Actor actor = new Actor();
                actor.setSize(100,100);
                actor.setPosition(i*100,i1*100);
                actor.setDebug(true);
                addActor(actor);
            }
        }
    }

    public int[][] getData() {
        return arr;
    }
}
