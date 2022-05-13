package org.example;

import com.github.bhlangonijr.chesslib.Board;

import java.util.UUID;

public class ChessGame {

    private Board board;
    private UUID blackId;
    private UUID whiteId;
    public Board getBoard() {
        return board;
    }

    public UUID getBlackId() {
        return blackId;
    }

    public UUID getWhiteId() {
        return whiteId;
    }

    public ChessGame (UUID blackId, UUID whiteId) {
        board = new Board();
        this.blackId = blackId;
        this.whiteId = whiteId;
    }
}
