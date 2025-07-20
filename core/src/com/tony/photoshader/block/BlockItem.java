package com.tony.photoshader.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.ImageUtils;
import com.tony.photoshader.constant.Constant;

public class BlockItem extends Group {
    private Image kuang;
    public BlockItem(String blockName){
        setSize(Constant.blockWidth,Constant.blockWidth);
        if (blockName == null){
            blockName = "block1.png";
        }
        kuang = new Image(Asset.getAsset().getTexture("block/"+blockName));
        addActor(kuang);
        kuang.setColor(Color.GOLD);
        kuang.setSize(Constant.blockWidth,Constant.blockWidth);
        kuang.setPosition(Constant.blockWidth/2f,Constant.blockWidth/2f, Align.center);
    }

    public void changeBlockColor(String blockMName){
        ImageUtils.changeImageDraw(kuang,Asset.getAsset().getTexture("block/"+blockMName));
        kuang.setSize(Constant.blockWidth,Constant.blockWidth);
        kuang.setPosition(getWidth()/2f,getHeight()/2f,Align.center);
    }
}
