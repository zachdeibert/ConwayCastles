package com.gitlab.zachdeibert.conwaycastles.options;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

final class TextOptionPanel extends AbstractOptionPanel {
    private static final long serialVersionUID = -2738909830480618027L;
    private final JLabel      label;
    private final JTextField  input;
    
    void save() {
        try {
            if ( field.getType().isAssignableFrom(int.class) ) {
                field.set(obj, Integer.parseInt(input.getText()));
            } else {
                field.set(obj, input.getText());
            }
        } catch ( final ReflectiveOperationException ex ) {
            ex.printStackTrace();
        }
    }
    
    @Override
    protected void setOption() {
        label.setText(String.format("%s: ", annotation.title()));
        String text = "";
        try {
            text = field.get(obj).toString();
        } catch ( final ReflectiveOperationException ex ) {
            ex.printStackTrace();
        }
        input.setText(text);
    }
    
    public TextOptionPanel() {
        label = new JLabel();
        input = new JTextField();
        setLayout(new FlowLayout());
        input.addCaretListener(new TextListener(this));
        input.setColumns(80);
        add(label);
        add(input);
    }
}
