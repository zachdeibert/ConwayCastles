package com.gitlab.zachdeibert.conwaycastles;

import java.io.Serializable;

public final class GameModel implements Serializable {
    private static transient final long serialVersionUID = 2819915887178775875L;
    public static transient final int   WIDTH            = 256;
    public static transient final int   HEIGHT           = 256;
    
    public String                       nameA;
    public String                       nameB;
    public byte[]                       data;
    public int                          scoreA;
    public int                          scoreB;
    public int                          cellsA;
    public int                          cellsB;
    public float                        cellA;
    public float                        cellB;
    public int                          time;
    
    public GameModel() {
        data = new byte[ (WIDTH * HEIGHT + 1) / 2];
        WorldGenerator.Generate(data);
    }
}
