package com.gitlab.zachdeibert.conwaycastles.options;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JColorChooser;

final class ColorOptionPopup implements MouseListener {
    private final ColorOptionPanel panel;
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        if ( e.getButton() == MouseEvent.BUTTON1 ) {
            panel.setColor(JColorChooser.showDialog(panel, "Choose color", panel.getColor()));
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
    
    ColorOptionPopup(final ColorOptionPanel panel) {
        this.panel = panel;
    }
}
