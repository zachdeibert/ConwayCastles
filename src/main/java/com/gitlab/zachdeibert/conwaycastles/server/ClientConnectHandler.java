package com.gitlab.zachdeibert.conwaycastles.server;

import com.gitlab.zachdeibert.jnet.ConnectEvent;
import com.gitlab.zachdeibert.jnet.LocalNetworkNode;
import com.gitlab.zachdeibert.jnet.NetworkNode;
import com.gitlab.zachdeibert.jnet.RemoteClient;

final class ClientConnectHandler implements ConnectEvent {
    private final Server server;
    
    @Override
    public void onConnect(final LocalNetworkNode connected, final NetworkNode connector) {
        if ( connector instanceof RemoteClient ) {
            server.onClientConnect();
        }
    }
    
    ClientConnectHandler(final Server server) {
        this.server = server;
    }
}
