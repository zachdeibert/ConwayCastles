package com.gitlab.zachdeibert.conwaycastles.server;

import com.gitlab.zachdeibert.conwaycastles.ActionData;
import com.gitlab.zachdeibert.conwaycastles.Side;

final class Action {
    final Side   side;
    final byte[] data;
    final int    x;
    final int    y;
    final int    width;
    final int    height;
    
    boolean isValid(final byte[] data) {
        return new ActionData(this.data, x, y, width, height).isValid(data, (byte) (side == Side.Server ? 0 : 1));
    }
    
    Action(Side side, byte[] data, int x, int y, int width, int height) {
        this.side = side;
        this.data = data;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
