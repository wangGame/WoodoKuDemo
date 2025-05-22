package com.tony.photoshader.ai;

public class GameState {
    private BitBoard board;
    private BitBoard previousPiecePlacement;
    private BitBoard previousPiece;
    private boolean previousMoveWasClear;
    private int score;

    public GameState(BitBoard board, BitBoard placement, BitBoard piece, boolean clear, int score) {
        this.board = board;
        this.previousPiecePlacement = placement;
        this.previousPiece = piece;
        this.previousMoveWasClear = clear;
        this.score = score;
    }

    public BitBoard getBoard() {
        return board;
    }

    public BitBoard getPreviousPiece() {
        return previousPiece;
    }

    public BitBoard getPreviousPiecePlacement() {
        return previousPiecePlacement;
    }

    public boolean isPreviousMoveWasClear() {
        return previousMoveWasClear;
    }

    public int getScore() {
        return score;
    }
}