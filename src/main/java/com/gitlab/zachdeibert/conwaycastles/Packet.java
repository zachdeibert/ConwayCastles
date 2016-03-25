package com.gitlab.zachdeibert.conwaycastles;

public final class Packet<T> extends com.gitlab.zachdeibert.jnet.Packet {
    private static final long serialVersionUID = -7715360037820760554L;
    public T                  data;
    
    public Packet(int id, T data) {
        super(id);
        this.data = data;
    }
}
