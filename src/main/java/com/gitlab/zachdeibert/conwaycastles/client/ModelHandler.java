package com.gitlab.zachdeibert.conwaycastles.client;

import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.Packet;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;
import com.gitlab.zachdeibert.conwaycastles.simulation.SimulationWindow;
import com.gitlab.zachdeibert.jnet.NetworkNode;
import com.gitlab.zachdeibert.jnet.PacketHandler;

final class ModelHandler extends PacketHandler {
    private final SimulationWindow window;
    
    @Override
    protected final void handle(final com.gitlab.zachdeibert.jnet.Packet p, final NetworkNode sender) {
        if ( p instanceof Packet ) {
            final Packet<?> dp = (Packet<?>) p;
            if ( dp.data instanceof GameModel ) {
                window.update(Side.Client, (GameModel) dp.data, Options.DEFAULT);
            }
        }
    }
    
    ModelHandler(final SimulationWindow win) {
        super(NetworkConstants.PACKET_MODEL);
        window = win;
    }
}
