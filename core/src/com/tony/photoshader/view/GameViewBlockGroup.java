package com.tony.photoshader.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.tony.photoshader.block.BlockItem;
import com.tony.photoshader.constant.Constant;

public class GameViewBlockGroup extends Group {
    private Label label;
    private BlockItem blockItem;
    private Image kuang;

    public GameViewBlockGroup(){
        setSize(Constant.blockWidth,Constant.blockWidth);

        Image bg = new Image(Asset.getAsset().getTexture("img/white.png"));

        bg.setSize(getWidth(),getWidth());
        bg.setPosition(getWidth()/2f,getWidth()/2f, Align.center);

        kuang = new Image(Asset.getAsset().getTexture("img/kuangxu.png"));
        addActor(kuang);
        kuang.setColor(Color.GRAY);
        kuang.setSize(Constant.blockWidth+30,Constant.blockWidth+30);
        kuang.setPosition(Constant.blockWidthHalf,Constant.blockWidthHalf, Align.center);
        kuang.setColor(Color.RED);

        label = new Label(0+"",new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("font/Krub-Bold_redStroke_52.fnt");
        }});
//        addActor(label);

        blockItem = new BlockItem();
        addActor(blockItem);
        blockItem.setPosition(getWidth()/2f,getHeight()/2,Align.center);
        blockItem.setVisible(false);
    }

    public void updateLabelPosition() {
        label.setPosition(getWidth()/2f,getHeight()/2f, Align.center);
    }

    public void setLabelValue(int i) {
        label.setText(i+"");
        if (i == 1){
            blockItem.setScale(1,1);
            blockItem.setVisible(true);
            resetColor();
        }
    }

    public void resetLabelValue(){
        label.setText("0");
        blockItem.clearActions();
        blockItem.addAction(Actions.scaleTo(0,0,0.1f));
    }

    public void setCanMoveColor() {
        kuang.getColor().a = 1;
    }

    public void resetColor() {
        kuang.getColor().a = 0;
    }
}
