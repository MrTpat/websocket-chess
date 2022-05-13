package org.example.messages;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.util.Objects;
import java.util.UUID;

// message at the start that tells a player their game id, player id, and color
public final class GameInitializationMessage extends Message {
    private final UUID gameId;
    private final UUID playerId;
    private final Side side;

    public GameInitializationMessage(UUID gameId, UUID playerId, Side side) {
        super("gameInit");
        this.gameId = gameId;
        this.playerId = playerId;
        this.side = side;
    }

    public UUID gameId() {
        return gameId;
    }

    public UUID playerId() {
        return playerId;
    }

    public Side side() {
        return side;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GameInitializationMessage) obj;
        return Objects.equals(this.gameId, that.gameId) &&
                Objects.equals(this.playerId, that.playerId) &&
                Objects.equals(this.side, that.side);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId, side);
    }

    @Override
    public String toString() {
        return "GameInitializationMessage[" +
                "gameId=" + gameId + ", " +
                "playerId=" + playerId + ", " +
                "side=" + side + ']';
    }

}
