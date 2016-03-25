package com.gitlab.zachdeibert.conwaycastles.client;

import java.io.IOException;
import com.gitlab.zachdeibert.conwaycastles.ActionData;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.Packet;
import com.gitlab.zachdeibert.conwaycastles.simulation.BuildFinishedEvent;
import com.gitlab.zachdeibert.jnet.NetworkClient;

final class ActionHandler implements BuildFinishedEvent {
    private final NetworkClient client;
    
    @Override
    public final void accept(final byte[] data, int x, int y, int width, int height) {
        try {
            client.sendPacket(new Packet<ActionData>(NetworkConstants.PACKET_ACTION, new ActionData(data, x, y, width, height)));
        } catch ( final IOException ex ) {
            ex.printStackTrace();
            try {
                client.disconnect();
            } catch ( final IOException ex2 ) {}
            System.exit(1);
        }
    }
    
    ActionHandler(final NetworkClient client) {
        this.client = client;
    }
}
