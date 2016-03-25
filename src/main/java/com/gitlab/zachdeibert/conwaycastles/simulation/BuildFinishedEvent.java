package com.gitlab.zachdeibert.conwaycastles.simulation;

import com.gitlab.zachdeibert.conwaycastles.ActionData;

public interface BuildFinishedEvent {
    default void accept(byte[] data, int x, int y, int width, int height) {
        accept(new ActionData(data, x, y, width, height));
    }
    
    default void accept(ActionData data) {
        accept(data.data, data.x, data.y, data.width, data.height);
    }
}
