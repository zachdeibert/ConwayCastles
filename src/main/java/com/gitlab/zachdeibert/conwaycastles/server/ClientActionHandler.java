package com.gitlab.zachdeibert.conwaycastles.server;

import com.gitlab.zachdeibert.conwaycastles.ActionData;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.Packet;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.jnet.NetworkNode;
import com.gitlab.zachdeibert.jnet.PacketHandler;

final class ClientActionHandler extends PacketHandler {
    private final EngineThread engine;
    
    @Override
    protected final void handle(final com.gitlab.zachdeibert.jnet.Packet p, final NetworkNode sender) {
        if ( p instanceof Packet ) {
            final Packet<?> dp = (Packet<?>) p;
            if ( dp.data instanceof ActionData ) {
                final ActionData act = (ActionData) dp.data;
                engine.addAction(new Action(Side.Client, act.data, act.x, act.y, act.width, act.height));
            }
        }
    }
    
    ClientActionHandler(final EngineThread engine) {
        super(NetworkConstants.PACKET_ACTION);
        this.engine = engine;
    }
}
