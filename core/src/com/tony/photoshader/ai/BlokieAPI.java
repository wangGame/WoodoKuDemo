package com.tony.photoshader.ai;


import com.tony.photoshader.constant.Constant;

import java.util.Random;

public class BlokieAPI {
    public Blokie blokie;

    public BlokieAPI(Blokie blokie){
        this.blokie = blokie;
    }

    public GameState getNewGame() {
        return new GameState(Constant.EMPTY, Constant.EMPTY, Constant.EMPTY, false, 0);
    }

    public BitBoard[] getRandomPieceSet(Random rng) {
        BitBoard[] raw = blokie.getRandomPieceSet(rng);
        for (int i = 0; i < raw.length; i++) {
            raw[i] = blokie.centerPiece(raw[i]);
        }
        return raw;
    }

    public GameState[] getAIMove(GameState game, BitBoard[] pieceSet, Random rng) {
        return blokie.aiMakeMove(game, pieceSet, rng);
    }

    public boolean isOver(GameState game) {
        return blokie.isOver(game);
    }
}

