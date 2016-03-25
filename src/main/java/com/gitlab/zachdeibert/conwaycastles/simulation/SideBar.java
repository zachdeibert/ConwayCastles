package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;

final class SideBar extends AbstractDisplayItem {
    private static final long  serialVersionUID = -1088487996776823140L;
    private final JButton      buildButton;
    private final JLabel       cellsLabel;
    private final JProgressBar cellProgress;
    private final JPanel       cellPanel;
    
    @Override
    void update(final Side side, final GameModel model, final Options opts) {
        if ( side == Side.Server ) {
            cellsLabel.setText(String.format("%d cell%c", model.cellsA, model.cellsA == 1 ? ' ' : 's'));
            cellProgress.setValue((int) (1000 * model.cellA));
        } else {
            cellsLabel.setText(String.format("%d cell%c", model.cellsB, model.cellsB == 1 ? ' ' : 's'));
            cellProgress.setValue((int) (1000 * model.cellB));
        }
    }
    
    SideBar(final SimulationWindow win) {
        buildButton = new JButton();
        cellsLabel = new JLabel();
        cellProgress = new JProgressBar(JProgressBar.VERTICAL);
        cellPanel = new JPanel();
        buildButton.addActionListener(new BuildButtonListener(buildButton, win));
        buildButton.setText("Build");
        cellProgress.setMinimum(0);
        cellProgress.setMaximum(1000);
        setLayout(new BorderLayout());
        add(buildButton, BorderLayout.PAGE_START);
        add(cellPanel, BorderLayout.CENTER);
        cellPanel.setLayout(new BorderLayout());
        cellPanel.add(cellsLabel, BorderLayout.PAGE_START);
        cellPanel.add(cellProgress, BorderLayout.CENTER);
    }
}
