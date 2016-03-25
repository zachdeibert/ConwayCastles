package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

final class BuildButtonListener implements ActionListener {
    private final JButton button;
    private final SimulationWindow window;
    private boolean inBuildMode;
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ( inBuildMode = !inBuildMode ) {
            window.enterBuildMode();
            button.setText("Deploy");
        } else {
            window.leaveBuildMode();
            button.setText("Build");
        }
    }
    
    BuildButtonListener(final JButton button, final SimulationWindow win) {
        this.button = button;
        window = win;
        inBuildMode = false;
    }
}
