package com.gitlab.zachdeibert.conwaycastles.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

final class ColorOptionPanel extends AbstractOptionPanel {
    private static final long serialVersionUID = -8111614555947569127L;
    private final JLabel label;
    private final JPanel preview;
    
    void setColor(final Color c) {
        try {
            field.set(obj, c);
        } catch ( final ReflectiveOperationException ex ) {
            ex.printStackTrace();
        }
        preview.setBackground(c);
    }
    
    Color getColor() {
        try {
            return (Color) field.get(obj);
        } catch ( final ReflectiveOperationException ex ) {
            ex.printStackTrace();
        }
        return Color.BLACK;
    }
    
    @Override
    protected void setOption() {
        label.setText(String.format("%s: ", annotation.title()));
        preview.setBackground(getColor());
    }
    
    public ColorOptionPanel() {
        label = new JLabel();
        preview = new JPanel();
        setLayout(new FlowLayout());
        preview.addMouseListener(new ColorOptionPopup(this));
        preview.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        preview.setPreferredSize(new Dimension(18, 18));
        add(label);
        add(preview);
    }
}
