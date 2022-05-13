package org.example.messages;

import java.util.Objects;
import java.util.UUID;

public final class GameStateMessage extends Message {
    private final UUID gameId;
    private final String fen;

    public GameStateMessage(UUID gameId, String fen) {
        super("gameState");
        this.gameId = gameId;
        this.fen = fen;
    }

    public UUID gameId() {
        return gameId;
    }

    public String fen() {
        return fen;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GameStateMessage) obj;
        return Objects.equals(this.gameId, that.gameId) &&
                Objects.equals(this.fen, that.fen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, fen);
    }

    @Override
    public String toString() {
        return "GameStateMessage[" +
                "gameId=" + gameId + ", " +
                "fen=" + fen + ']';
    }

}
