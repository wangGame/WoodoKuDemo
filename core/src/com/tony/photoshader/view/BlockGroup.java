package com.tony.photoshader.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockActor1;

public class BlockGroup extends Group {
    private BlockActor1 blockActor1;
    public BlockGroup(){
        setSize(300,300);
        blockActor1 = new BlockActor1();
        blockActor1.createBlock();
        addActor(blockActor1);
        blockActor1.setPosition(150,150, Align.center);
    }

    public BaseBlockActor getBlockActor1() {
        return blockActor1;
    }
}
