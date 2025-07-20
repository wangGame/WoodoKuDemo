package com.tony.photoshader.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.ImageUtils;
import com.tony.photoshader.constant.BConstant;

public class BlockItem extends Group {
    private Image itemColorImg;
    public BlockItem(String blockName){
        setSize(BConstant.blockWidth, BConstant.blockWidth);
        if (blockName == null){
            blockName = "block1.png";
        }
        itemColorImg = new Image(Asset.getAsset().getTexture("block/"+blockName));
        addActor(itemColorImg);
        itemColorImg.setColor(Color.GOLD);
        itemColorImg.setSize(BConstant.blockWidth, BConstant.blockWidth);
        itemColorImg.setPosition(BConstant.blockWidth/2f, BConstant.blockWidth/2f, Align.center);
    }

    public void changeBlockColor(String blockMName){
        ImageUtils.changeImageDraw(itemColorImg,Asset.getAsset().getTexture("block/"+blockMName));
        itemColorImg.setSize(BConstant.blockWidth, BConstant.blockWidth);
        itemColorImg.setPosition(getWidth()/2f,getHeight()/2f,Align.center);
    }
}
