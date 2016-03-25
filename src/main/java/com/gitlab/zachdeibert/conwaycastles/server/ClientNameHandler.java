package com.gitlab.zachdeibert.conwaycastles.server;

import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.Packet;
import com.gitlab.zachdeibert.jnet.NetworkNode;
import com.gitlab.zachdeibert.jnet.PacketHandler;

final class ClientNameHandler extends PacketHandler {
    private final GameModel model;
    
    @Override
    protected final void handle(final com.gitlab.zachdeibert.jnet.Packet p, final NetworkNode sender) {
        if ( p instanceof Packet ) {
            final Packet<?> dp = (Packet<?>) p;
            if ( dp.data instanceof String ) {
                model.nameB = (String) dp.data;
            }
        }
    }
    
    ClientNameHandler(final GameModel model) {
        super(NetworkConstants.PACKET_NAME);
        this.model = model;
    }
}
