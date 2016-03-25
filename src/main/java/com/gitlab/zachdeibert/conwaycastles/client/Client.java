package com.gitlab.zachdeibert.conwaycastles.client;

import java.io.IOException;
import javax.swing.JOptionPane;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.Packet;
import com.gitlab.zachdeibert.conwaycastles.options.Options;
import com.gitlab.zachdeibert.conwaycastles.simulation.SimulationWindow;
import com.gitlab.zachdeibert.jnet.NetworkClient;

public final class Client {
    private final NetworkClient client;
    private final SimulationWindow window;
    
    public Client() {
        client = new NetworkClient();
        window = new SimulationWindow(new ActionHandler(client));
        new ModelHandler(window);
        final String IP = JOptionPane.showInputDialog(null, "Please enter the server IP to connect to", "Conway Castles", JOptionPane.QUESTION_MESSAGE);
        try {
            client.connect(IP, NetworkConstants.PORT);
            client.sendPacket(new Packet<String>(NetworkConstants.PACKET_NAME, Options.DEFAULT.name));
        } catch ( final IOException ex ) {
            ex.printStackTrace();
            System.exit(1);
        }
        window.setVisible(true);
    }
}
