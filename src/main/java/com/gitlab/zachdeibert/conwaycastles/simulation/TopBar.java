package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;

final class TopBar extends AbstractDisplayItem {
    private static final long serialVersionUID = -8932125956642350149L;
    private final JLabel myLabel;
    private final JLabel yourLabel;
    private final JLabel timeLabel;

    @Override
    void update(final Side side, final GameModel model, final Options opts) {
        myLabel.setText(String.format("%s: %d points", side == Side.Server ? model.nameA : model.nameB, side == Side.Server ? model.scoreA : model.scoreB));
        yourLabel.setText(String.format("%s: %d points", side == Side.Client ? model.nameA : model.nameB, side == Side.Client ? model.scoreA : model.scoreB));
        timeLabel.setText(String.format("%02d:%02d", model.time / 60, model.time % 60));
    }
    
    TopBar() {
        myLabel   = new JLabel();
        yourLabel = new JLabel();
        timeLabel = new JLabel();
        myLabel.setHorizontalAlignment(JLabel.LEFT);
        yourLabel.setHorizontalAlignment(JLabel.RIGHT);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setVerticalAlignment(JLabel.CENTER);
        yourLabel.setVerticalAlignment(JLabel.CENTER);
        timeLabel.setVerticalAlignment(JLabel.CENTER);
        myLabel.setFont(myLabel.getFont().deriveFont(18.0f));
        yourLabel.setFont(yourLabel.getFont().deriveFont(18.0f));
        timeLabel.setFont(timeLabel.getFont().deriveFont(36.0f));
        setLayout(new BorderLayout());
        add(myLabel, BorderLayout.LINE_START);
        add(yourLabel, BorderLayout.LINE_END);
        add(timeLabel, BorderLayout.CENTER);
    }
}
