package com.gitlab.zachdeibert.conwaycastles.server;

import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.simulation.BuildFinishedEvent;

final class ServerActionHandler implements BuildFinishedEvent {
    private final Server server;
    
    @Override
    public final void accept(final byte[] data, int x, int y, int width, int height) {
        server.getEngine().addAction(new Action(Side.Server, data, x, y, width, height));
    }
    
    ServerActionHandler(final Server server) {
        this.server = server;
    }
}
