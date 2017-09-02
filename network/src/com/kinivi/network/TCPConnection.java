package com.kinivi.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener tcpConnectionListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPConnection (TCPConnectionListener tcpConnectionListener, int port, String ipAddress) throws IOException {
        this(new Socket(ipAddress,port), tcpConnectionListener);
    }
    public TCPConnection(Socket socket, TCPConnectionListener tcpConnectionListener) throws IOException {
        this.socket = socket;
        this.tcpConnectionListener = tcpConnectionListener;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tcpConnectionListener.onConnectionReady(TCPConnection.this);

                    while (rxThread.isInterrupted()) {
                        tcpConnectionListener.onReceivingMessage(TCPConnection.this, in.readLine());
                    }
                } catch (IOException e) {
//                    e.printStackTrace();
                    tcpConnectionListener.onException(TCPConnection.this, e);
                } finally {
                    try {
                        socket.close();
                        tcpConnectionListener.onDisconnect(TCPConnection.this);
                    } catch (IOException e) {
                        tcpConnectionListener.onException(TCPConnection.this, e);
                    }
                }
            }
        });
        rxThread.start();

    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        tcpConnectionListener.onDisconnect(TCPConnection.this);
    }

    public synchronized void sendString(String msg) {
        try {
            out.write(msg + "\n\r");
        } catch (IOException e) {
            tcpConnectionListener.onException(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnection" + socket.getInetAddress() + ":" + socket.getPort();
    }
}
