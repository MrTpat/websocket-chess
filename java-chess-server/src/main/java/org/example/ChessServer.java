package org.example;

import com.github.bhlangonijr.chesslib.Side;
import com.google.gson.Gson;
import org.example.messages.GameInitializationMessage;
import org.example.messages.GameStateMessage;
import org.example.messages.MoveMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;

public class ChessServer extends WebSocketServer {
    private Map<UUID, WebSocket> playerConns;
    private Map<UUID, ChessGame> games;
    private Queue<UUID> waiting;

    private Gson gson;


    public ChessServer(InetSocketAddress address) {
        super(address);
        playerConns = new HashMap<>();
        games = new HashMap<>();
        waiting = new LinkedList<>();
        gson = new Gson();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        var playerId = UUID.randomUUID();
        playerConns.put(playerId, webSocket);
        var opponentId= waiting.poll();
        if (opponentId == null) {
            waiting.offer(playerId);
        } else {
            // create game between opponents

            // need to communicate who is what side and the game ID
            var black = opponentId;
            var white = playerId;

            var game = new ChessGame(black, white);
            var gameId = UUID.randomUUID();
            games.put(gameId, game);
            var blackSocket = playerConns.get(black);
            var whiteSocket = playerConns.get(white);

            var blackGameInitializationMessage = new GameInitializationMessage(gameId, black, Side.BLACK);
            var whiteGameInitializationMessage = new GameInitializationMessage(gameId, white, Side.WHITE);

            var blackMessageString = gson.toJson(blackGameInitializationMessage);
            var whiteMessageString = gson.toJson(whiteGameInitializationMessage);

            blackSocket.send(blackMessageString);
            whiteSocket.send(whiteMessageString);
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {

    }

    @Override
    // only receive move messages for now
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(s);
        MoveMessage move = gson.fromJson(s, MoveMessage.class);
        var gameId = move.gameId();
        var game = games.get(gameId);
        game.getBoard().doMove(move.getMove().getMove()); // assume the move is valid for now

        //send the game fen to both
        var newFen = game.getBoard().getFen();
        var gameStateMessage = new GameStateMessage(gameId, newFen);

        var blackId = game.getBlackId();
        var whiteId = game.getWhiteId();

        var blackSocket = playerConns.get(blackId);
        var whiteSocket = playerConns.get(whiteId);

        System.out.println("Sending" + gson.toJson(gameStateMessage));
        blackSocket.send(gson.toJson(gameStateMessage));
        whiteSocket.send(gson.toJson(gameStateMessage));
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println(e.toString());

    }
}
