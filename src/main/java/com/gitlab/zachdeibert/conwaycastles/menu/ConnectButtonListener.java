package com.gitlab.zachdeibert.conwaycastles.menu;

import com.gitlab.zachdeibert.conwaycastles.client.Client;

final class ConnectButtonListener extends AbstractButtonListener {
    @Override
    protected void onClick() {
        new Client();
    }

    public ConnectButtonListener(final MenuWindow win) {
        super(win);
    }
}
