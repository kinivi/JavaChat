package com.kinivi.clientUI;

import javax.swing.*;

public class ClientWindow extends JFrame{
    private static final String ipAddr="10.10.10.10";
    private static final int port = 8189;
    private static final int WIDTH = 600;
    private static final int HEIGHT =470;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientWindow::new);
    }

    private final JTextArea log = new JTextArea();
    private ClientWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        log.setEditable(false);
        setVisible(true);
    }

}
