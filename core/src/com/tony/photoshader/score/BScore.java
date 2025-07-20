package com.tony.photoshader.score;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Author by tony
 * Date on 2025/7/20.
 */
public class BScore {
    private Label scoreLabel;
    private Label maxLabel;
    private int gameSore;
    private int maxGameScore;

    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void setMaxLabel(Label maxLabel) {
        this.maxLabel = maxLabel;
    }

    private static BScore score;
    public static BScore getInstance(){
        if (score == null){
            score = new BScore();
        }
        return score;
    }

    public void updateScore(int score){
        
        if (scoreLabel!=null){

        }
    }
}
