package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

final class FieldClickListener implements MouseListener {
    private final GameField field;
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        if ( e.getButton() == MouseEvent.BUTTON1 ) {
            field.clickedAt(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {}

    @Override
    public void mouseReleased(final MouseEvent e) {}

    @Override
    public void mouseEntered(final MouseEvent e) {}

    @Override
    public void mouseExited(final MouseEvent e) {}
    
    FieldClickListener(final GameField field) {
        this.field = field;
    }
}
