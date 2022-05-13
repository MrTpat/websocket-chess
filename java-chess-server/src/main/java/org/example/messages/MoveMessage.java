package org.example.messages;

import java.util.Objects;
import java.util.UUID;

public final class MoveMessage extends Message {
    private final UUID gameId;
    private final UUID playerId;
    private final BasicMove move;



    public MoveMessage(UUID gameId, UUID playerId, BasicMove move) {
        super("move");
        this.gameId = gameId;
        this.playerId = playerId;
        this.move = move;
    }

    public UUID gameId() {
        return gameId;
    }

    public UUID playerId() {
        return playerId;
    }

    public BasicMove getMove() {
        return move;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MoveMessage) obj;
        return Objects.equals(this.gameId, that.gameId) &&
                Objects.equals(this.playerId, that.playerId) &&
                Objects.equals(this.move, that.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId, move);
    }

    @Override
    public String toString() {
        return "MoveMessage[" +
                "gameId=" + gameId + ", " +
                "playerId=" + playerId + ", " +
                "move=" + move + ']';
    }

}
