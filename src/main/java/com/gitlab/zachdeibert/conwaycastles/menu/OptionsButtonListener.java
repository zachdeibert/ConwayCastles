package com.gitlab.zachdeibert.conwaycastles.menu;

import com.gitlab.zachdeibert.conwaycastles.options.Options;
import com.gitlab.zachdeibert.conwaycastles.options.OptionsWindow;

final class OptionsButtonListener extends AbstractButtonListener {
    @Override
    protected final void onClick() {
        final OptionsWindow win = new OptionsWindow(Options.DEFAULT);
        win.setVisible(true);
    }
    
    public OptionsButtonListener(final MenuWindow win) {
        super(win);
    }
}
