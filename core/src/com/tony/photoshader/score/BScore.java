package com.tony.photoshader.score;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kw.gdx.action.NumAction;
import com.tony.photoshader.pre.BPrefence;

/**
 * Author by tony
 * Date on 2025/7/20.
 */
public class BScore {
    private Label scoreLabel;
    private Label maxLabel;
    private int gameSore;
    private int maxGameScore;
    private int currentScore;

    private BScore(){
        maxGameScore = BPrefence.getbPrefence().getMaxScore();
    }

    private NumAction action;
    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;

//        scoreLabel.addAction(new Action() {
//            private float time = 0;
//            @Override
//            public boolean act(float delta) {
//                time += delta;
//                if (time>0.03333f) {
//                    time = 0;
//                    if (currentScore < gameSore) {
//                        currentScore += 1;
//                        if (scoreLabel != null) {
//                            scoreLabel.setText(currentScore);
//                        }
//                        if (gameSore > maxGameScore) {
//                            maxLabel.setText(currentScore);
//                        }
//                    }
//                }
//                return false;
//            }
//        });

    }

    public void setMaxLabel(Label maxLabel) {
        this.maxLabel = maxLabel;
        maxLabel.setText(maxGameScore);
    }

    private static BScore score;
    public static BScore getInstance(){
        if (score == null){
            score = new BScore();
        }
        return score;
    }

    public void updateScore(int score){
        gameSore += score;
        if (gameSore>maxGameScore){
            BPrefence.getbPrefence().saveMaxScore(gameSore);
        }

        scoreLabel.clearActions();
        action = new NumAction();
        action.setStart(currentScore);
        action.setEnd(gameSore);
        float v = 0.03f * score;
        action.setDuration(v);
        action.setInterpolation(Interpolation.sine);
        scoreLabel.addAction(action);
        action.setUpdateRunnable(new Runnable() {
            @Override
            public void run() {
                currentScore = (int) action.getValue();
                scoreLabel.setText(currentScore );
            }
        });
    }
}
