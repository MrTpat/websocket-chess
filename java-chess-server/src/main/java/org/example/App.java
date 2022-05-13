package org.example;

import org.java_websocket.server.WebSocketServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ChessServer server = new ChessServer(new InetSocketAddress("127.0.0.1", 8765));
        server.run();
    }
}
