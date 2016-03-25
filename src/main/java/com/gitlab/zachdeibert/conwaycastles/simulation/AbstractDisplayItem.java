package com.gitlab.zachdeibert.conwaycastles.simulation;

import javax.swing.JPanel;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;

abstract class AbstractDisplayItem extends JPanel {
    private static final long serialVersionUID = -7640136997084206948L;
    
    abstract void update(Side side, GameModel model, Options opts);
    
    void enterBuildMode() {}
    
    void leaveBuildMode() {}
}
