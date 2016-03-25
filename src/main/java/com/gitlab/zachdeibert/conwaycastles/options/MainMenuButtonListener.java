package com.gitlab.zachdeibert.conwaycastles.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.gitlab.zachdeibert.conwaycastles.menu.MenuWindow;

final class MainMenuButtonListener implements ActionListener {
    private final OptionsWindow window;
    private final Options opts;
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        window.setVisible(false);
        new MenuWindow().setVisible(true);
        Options.save(opts);
    }
    
    MainMenuButtonListener(final OptionsWindow win, final Options opts) {
        window = win;
        this.opts = opts;
    }
}
