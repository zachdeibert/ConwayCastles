package com.gitlab.zachdeibert.conwaycastles;

abstract class WorldGenerator {
    static final int[] Castle = new int[] {
        5, 0,
        6, 0,
        9, 0,
        10, 0,
        13, 0,
        14, 0,
        17, 0,
        18, 0,
        1, 1,
        2, 1,
        5, 1,
        6, 1,
        9, 1,
        10, 1,
        13, 1,
        14, 1,
        17, 1,
        18, 1,
        21, 1,
        22, 1,
        1, 2,
        2, 2,
        21, 2,
        22, 2,
        4, 4,
        5, 4,
        9, 4,
        14, 4,
        18, 4,
        19, 4,
        0, 5,
        1, 5,
        4, 5,
        5, 5,
        9, 5,
        14, 5,
        18, 5,
        19, 5,
        22, 5,
        23, 5,
        0, 6,
        1, 6,
        9, 6,
        14, 6,
        22, 6,
        23, 6,
        0, 9,
        1, 9,
        4, 9,
        5, 9,
        6, 9,
        9, 9,
        10, 9,
        13, 9,
        14, 9,
        17, 9,
        18, 9,
        19, 9,
        22, 9,
        23, 9,
        0, 10,
        1, 10,
        9, 10,
        10, 10,
        13, 10,
        14, 10,
        22, 10,
        23, 10,
        0, 13,
        1, 13,
        9, 13,
        10, 13,
        13, 13,
        14, 13,
        22, 13,
        23, 13,
        0, 14,
        1, 14,
        4, 14,
        5, 14,
        6, 14,
        9, 14,
        10, 14,
        13, 14,
        14, 14,
        17, 14,
        18, 14,
        19, 14,
        22, 14,
        23, 14,
        0, 17,
        1, 17,
        9, 17,
        14, 17,
        22, 17,
        23, 17,
        0, 18,
        1, 18,
        4, 18,
        5, 18,
        9, 18,
        14, 18,
        18, 18,
        19, 18,
        22, 18,
        23, 18,
        4, 19,
        5, 19,
        9, 19,
        14, 19,
        18, 19,
        19, 19,
        1, 21,
        2, 21,
        21, 21,
        22, 21,
        1, 22,
        2, 22,
        5, 22,
        6, 22,
        9, 22,
        10, 22,
        13, 22,
        14, 22,
        17, 22,
        18, 22,
        21, 22,
        22, 22,
        5, 23,
        6, 23,
        9, 23,
        10, 23,
        13, 23,
        14, 23,
        17, 23,
        18, 23
    };
    
    static final void set(final byte[] data, final int v, final int x, final int y) {
        final int o = y * GameModel.WIDTH + x;
        final int i = o / 2;
        if ( o % 2 == 0 ) {
            data[i] = (byte) ((data[i] & 0x0F) | (v << 4));
        } else {
            data[i] = (byte) ((data[i] & 0xF0) | v);
        }
    }
    
    static final void Clear(final byte[] data) {
        final byte v = (byte) (11 | (11 << 4));
        for ( int i = 0; i < data.length; i++ ) {
            data[i] = v;
        }
    }
    
    static final void Draw(final byte[] data, final int[] indeces, final byte v, final int x, final int y, final boolean flipVertical, final boolean flipHorizontal) {
        if ( indeces.length % 2 == 1 ) {
            throw new IllegalArgumentException("Indeces length must be even");
        }
        int width = 0;
        int height = 0;
        for ( int i = 0; i < indeces.length; i += 2 ) {
            final int rx = indeces[i] - x;
            final int ry = indeces[i + 1] - y;
            if ( rx > width ) {
                width = rx;
            }
            if ( ry > height ) {
                height = ry;
            }
        }
        final int horizontalCenter = x + (width / 2);
        final int verticalCenter = y + (height / 2);
        for ( int i = 0; i < indeces.length; i += 2 ) {
            // Get position
            final int ix = indeces[i];
            final int iy = indeces[i + 1];
            // Transform position
            final int tx = ix + x;
            final int ty = iy + y;
            // Flip position
            final int fx;
            final int fy;
            if ( flipHorizontal ) {
                fx = -tx + horizontalCenter + horizontalCenter;
            } else {
                fx = tx;
            }
            if ( flipVertical ) {
                fy = -ty + verticalCenter + verticalCenter;
            } else {
                fy = ty;
            }
            // Set point
            set(data, v, fx, fy);
        }
    }
    
    static final void DrawCenterBorder(final byte[] data) {
        for ( int x = 0, y = (GameModel.HEIGHT - 1) / 2; x < GameModel.WIDTH - 1; x += 3 ) {
            set(data, 3, x, y);
            set(data, 3, x + 1, y);
            set(data, 3, x, y + 1);
            set(data, 3, x + 1, y + 1);
        }
    }
    
    static final void Generate(final byte[] data) {
        Clear(data);
        DrawCenterBorder(data);
        Draw(data, Castle, (byte) 1, (GameModel.WIDTH - 23) / 2, 4, false, false);
        Draw(data, Castle, (byte) 0, (GameModel.WIDTH - 23) / 2, GameModel.HEIGHT - 27, true, false);
    }
}
