package com.tony.photoshader.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.tony.photoshader.block.BlockItem;
import com.tony.photoshader.constant.BConstant;

public class GameViewBlockGroup extends Group {
    private Label label;
    private BlockItem blockItem;
    private Image tipKUang;

    public GameViewBlockGroup(){
        setSize(BConstant.blockWidth, BConstant.blockWidth);

        Image bg = new Image(Asset.getAsset().getTexture("img/white.png"));

        bg.setSize(getWidth(),getWidth());
        bg.setPosition(getWidth()/2f,getWidth()/2f, Align.center);

        tipKUang = new Image(Asset.getAsset().getTexture("img/kuangxu.png"));
        addActor(tipKUang);

        tipKUang.setSize(BConstant.blockWidth+30, BConstant.blockWidth+30);
        tipKUang.setPosition(BConstant.blockWidthHalf, BConstant.blockWidthHalf, Align.center);
        tipKUang.setColor(Color.RED);
        tipKUang.getColor().a = 0;
        label = new Label(0+"",new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("font/Krub-Bold_redStroke_52.fnt");
        }});
//        addActor(label);

        blockItem = new BlockItem(null);
        addActor(blockItem);
        blockItem.setPosition(getWidth()/2f,getHeight()/2,Align.center);
        blockItem.setVisible(false);
    }

    public void updateLabelPosition() {
        label.setPosition(getWidth()/2f,getHeight()/2f, Align.center);
    }

    public void setLabelValue(int i, String blockName) {
        label.setText(i+"");
        if (i == 1){
            blockItem.setScale(1,1);
            blockItem.setVisible(true);
            blockItem.changeBlockColor(blockName);
//            resetColor();
        }
        setColor(Color.WHITE);
    }

    public void resetLabelValue(){
        label.setText("0");
        blockItem.clearActions();
        blockItem.setOrigin(Align.center);
        blockItem.addAction(Actions.scaleTo(0,0,0.1f));
    }

    public void setCanMoveColor() {
        tipKUang.getColor().a = 1;
    }

    public void resetColor() {
        tipKUang.getColor().a = 0;
    }
}
