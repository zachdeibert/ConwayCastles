package com.gitlab.zachdeibert.conwaycastles;

import java.awt.EventQueue;
import com.gitlab.zachdeibert.conwaycastles.menu.MenuWindow;

abstract class Entry {
    public static void main(final String[] args) {
        EventQueue.invokeLater(() -> new MenuWindow().setVisible(true));
    }
}
