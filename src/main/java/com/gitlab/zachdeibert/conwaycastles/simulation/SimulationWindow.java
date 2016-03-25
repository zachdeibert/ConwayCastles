package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;

public final class SimulationWindow extends JFrame {
    private static final long serialVersionUID = 4287155338514848643L;
    private final TopBar   top;
    private final SideBar  side;
    private final GamePane game;
    
    public void update(final Side side, final GameModel model, final Options opts) {
        top.update(side, model, opts);
        this.side.update(side, model, opts);
        game.update(side, model, opts);
    }
    
    void enterBuildMode() {
        top.enterBuildMode();
        side.enterBuildMode();
        game.enterBuildMode();
    }
    
    void leaveBuildMode() {
        top.leaveBuildMode();
        side.leaveBuildMode();
        game.leaveBuildMode();
    }
    
    public SimulationWindow(final BuildFinishedEvent handler) {
        top = new TopBar();
        side = new SideBar(this);
        game = new GamePane(handler);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(400, 300));
        setTitle("Conway Castles");
        setLayout(new BorderLayout());
        add(top, BorderLayout.PAGE_START);
        add(side, BorderLayout.LINE_START);
        add(game, BorderLayout.CENTER);
    }
}
