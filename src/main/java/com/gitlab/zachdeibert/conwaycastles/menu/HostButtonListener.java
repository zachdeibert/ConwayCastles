package com.gitlab.zachdeibert.conwaycastles.menu;

import com.gitlab.zachdeibert.conwaycastles.server.Server;

final class HostButtonListener extends AbstractButtonListener {
    @Override
    protected void onClick() {
        new Server();
    }
    
    HostButtonListener(final MenuWindow win) {
        super(win);
    }
}
