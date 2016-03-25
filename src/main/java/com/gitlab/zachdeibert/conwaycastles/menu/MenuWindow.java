package com.gitlab.zachdeibert.conwaycastles.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class MenuWindow extends JFrame {
    private static final long serialVersionUID = -1163555534758212344L;
    private final JLabel  title;
    private final JButton clientButton;
    private final JButton serverButton;
    private final JButton optionsButton;
    
    public MenuWindow() {
        title = new JLabel();
        clientButton = new JButton();
        serverButton = new JButton();
        optionsButton = new JButton();
        title.setText("Conway Castles");
        title.setFont(title.getFont().deriveFont(72.0f));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        clientButton.setText("Join Game");
        serverButton.setText("Host Game");
        optionsButton.setText("Options");
        clientButton.addActionListener(new ConnectButtonListener(this));
        serverButton.addActionListener(new HostButtonListener(this));
        optionsButton.addActionListener(new OptionsButtonListener(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(400, 300));
        setTitle("Conway Castles");
        setLayout(new BorderLayout());
        add(title, BorderLayout.CENTER);
        add(clientButton, BorderLayout.LINE_START);
        add(serverButton, BorderLayout.LINE_END);
        add(optionsButton, BorderLayout.PAGE_END);
    }
}
