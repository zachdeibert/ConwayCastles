package com.gitlab.zachdeibert.conwaycastles.options;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

final class TextListener implements CaretListener {
    private final TextOptionPanel panel;
    
    @Override
    public void caretUpdate(final CaretEvent e) {
        panel.save();
    }
    
    TextListener(final TextOptionPanel panel) {
        this.panel = panel;
    }
}
