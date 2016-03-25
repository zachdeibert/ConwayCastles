package com.gitlab.zachdeibert.conwaycastles.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public final class OptionsWindow extends JFrame {
    private static final long serialVersionUID = -2325339614207451656L;
    private final JButton     button;
    private final JScrollPane scroll;
    private final JPanel      panel;
    
    public OptionsWindow(final Options options) {
        button = new JButton();
        panel = new JPanel();
        scroll = new JScrollPane(panel);
        final List<AbstractOptionPanel> panels = new ArrayList<AbstractOptionPanel>();
        for ( final Field field : options.getClass().getFields() ) {
            final Option annotation;
            if ( (annotation = field.getAnnotation(Option.class)) != null ) {
                try {
                    final AbstractOptionPanel panel = annotation.type().newInstance();
                    panel.setOption(options, field, annotation);
                    panels.add(panel);
                } catch ( final ReflectiveOperationException ex ) {
                    ex.printStackTrace();
                }
            }
        }
        panels.sort(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(400, 300));
        setTitle("Conway Castles");
        button.addActionListener(new MainMenuButtonListener(this, options));
        button.setText("Back to Main Menu");
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        String group = "";
        for ( final AbstractOptionPanel panel : panels ) {
            if ( !group.equals(panel.getGroupName()) ) {
                this.panel.add(new GroupHeader(group = panel.getGroupName()), c);
            }
            this.panel.add(panel, c);
        }
        setLayout(new BorderLayout());
        add(button, BorderLayout.PAGE_START);
        add(scroll, BorderLayout.CENTER);
    }
}
