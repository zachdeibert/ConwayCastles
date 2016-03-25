package com.gitlab.zachdeibert.conwaycastles.options;

import javax.swing.JLabel;

final class GroupHeader extends JLabel {
    private static final long serialVersionUID = 2247594732532017396L;

    GroupHeader(final String title) {
        setFont(getFont().deriveFont(36.0f));
        setHorizontalAlignment(CENTER);
        setText(title);
    }
}
