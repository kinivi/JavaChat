package com.kinivi.clientUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow extends JFrame implements ActionListener{
    private static final String ipAddr="10.10.10.10";
    private static final int port = 8189;
    private static final int WIDTH = 600;
    private static final int HEIGHT =470;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientWindow::new);
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("Nik");
    private final JTextField fieldInput = new JTextField();
    private ClientWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        log.setEditable(false);
        log.setLineWrap(true);

        add(log, BorderLayout.CENTER);

        add(fieldNickname, BorderLayout.NORTH);
        fieldInput.addActionListener(this);
        add(fieldInput,BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
