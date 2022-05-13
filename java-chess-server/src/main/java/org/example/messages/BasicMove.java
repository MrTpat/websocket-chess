package org.example.messages;

import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public final class BasicMove {
    private String from;
    private String to;

    public Move getMove() {
        return new Move(Square.fromValue(from), Square.fromValue(to));
    }
}
