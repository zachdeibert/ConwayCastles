package com.gitlab.zachdeibert.conwaycastles.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class AbstractButtonListener implements ActionListener {
    protected final MenuWindow window;
    
    protected abstract void onClick();
    
    public final void actionPerformed(final ActionEvent e) {
        window.setVisible(false);
        onClick();
    }
    
    AbstractButtonListener(final MenuWindow win) {
        window = win;
    }
}
