package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;

final class GamePane extends AbstractDisplayItem {
    private static final long serialVersionUID = 7578375290453749968L;
    private final JScrollPane pane;
    private final GameField   field;
    
    @Override
    void update(final Side side, final GameModel model, final Options opts) {
        field.update(side, model, opts);
    }
    
    @Override
    void enterBuildMode() {
        field.enterBuildMode();
    }

    @Override
    void leaveBuildMode() {
        field.leaveBuildMode();
    }
    
    GamePane(final BuildFinishedEvent handler) {
        pane = new JScrollPane();
        field = new GameField(handler);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setLayout(new BorderLayout());
        add(pane, BorderLayout.CENTER);
        pane.setViewportView(field);
    }
}
