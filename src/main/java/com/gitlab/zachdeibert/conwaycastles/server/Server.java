package com.gitlab.zachdeibert.conwaycastles.server;

import java.io.IOException;
import javax.swing.JOptionPane;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.options.Options;
import com.gitlab.zachdeibert.conwaycastles.simulation.SimulationWindow;
import com.gitlab.zachdeibert.jnet.NetworkServer;

public final class Server {
    private final NetworkServer    server;
    private final SimulationWindow window;
    private final GameModel        model;
    private final EngineThread     engine;
    private final Thread           waitingThread;
    
    final void onClientConnect() {
        waitingThread.interrupt();
    }
    
    final EngineThread getEngine() {
        return engine;
    }
    
    public Server() {
        server = new NetworkServer();
        window = new SimulationWindow(new ServerActionHandler(this));
        model = new GameModel();
        model.nameA = Options.DEFAULT.name;
        engine = new EngineThread(server, window, model);
        waitingThread = Thread.currentThread();
        new ClientNameHandler(model);
        new ClientActionHandler(engine);
        // Comment out block to run without client
        //*
        try {
            server.onConnect = new ClientConnectHandler(this);
            server.connect(NetworkConstants.PORT);
        } catch ( final IOException ex ) {
            ex.printStackTrace();
        }
        try {
            if ( JOptionPane.showOptionDialog(null, "Waiting for someone to connect to your server", "Conway Castles", JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {
                "Cancel"
            }, "Cancel") == 0 ) {
                System.exit(0);
            }
            Thread.sleep(0);
        } catch ( final InterruptedException ex ) {}
        //-*/
        engine.start();
        window.setVisible(true);
    }
}
