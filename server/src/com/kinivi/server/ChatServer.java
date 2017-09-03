package com.kinivi.server;

import com.kinivi.network.TCPConnection;
import com.kinivi.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer implements TCPConnectionListener{
    private final Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer(){
        Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (!serverSocket.isClosed()) {
                try {
                    new TCPConnection(serverSocket.accept(), this);
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "New Exception!!! Stacktrace: " +
                            Arrays.toString(e.getStackTrace()));
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
    }

    @Override
    public synchronized void onReceivingMessage(TCPConnection tcpConnection, String msg) {

    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections(tcpConnection.toString()+" disconnected");
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        LOGGER.log(Level.SEVERE, "TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String msg) {
        System.out.println(msg);
        connections.forEach(tcpConnection -> tcpConnection.sendString(msg));
    }
}
