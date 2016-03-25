package com.gitlab.zachdeibert.conwaycastles.simulation;

import com.gitlab.zachdeibert.conwaycastles.ActionData;
import com.gitlab.zachdeibert.conwaycastles.GameModel;

final class BuildingShrinker {
    private final byte[] building;
    
    private final boolean get(int x, int y) {
        int o = y * GameModel.WIDTH + x;
        return (building[o / 8] & (1 << (o % 8))) != 0;
    }
    
    private final void set(byte[] d, boolean v, int w, int x, int y) {
        final int o = y * w + x;
        final int i = o / 8;
        final int m = (1 << (o % 8));
        d[i] |= m;
        if ( !v ) {
            d[i] ^= m;
        }
    }
    
    private final int getMinimumX() {
        for ( int x = 0; x < GameModel.WIDTH; x++ ) {
            for ( int y = 0; y < GameModel.HEIGHT; y++ ) {
                if ( get(x, y) ) {
                    return x;
                }
            }
        }
        return -1;
    }
    
    private final int getMaximumX() {
        for ( int x = GameModel.WIDTH - 1; x >= 0; x-- ) {
            for ( int y = 0; y < GameModel.HEIGHT; y++ ) {
                if ( get(x, y) ) {
                    return x;
                }
            }
        }
        return -1;
    }
    
    private final int getMinimumY() {
        for ( int y = 0; y < GameModel.HEIGHT; y++ ) {
            for ( int x = 0; x < GameModel.WIDTH; x++ ) {
                if ( get(x, y) ) {
                    return y;
                }
            }
        }
        return -1;
    }
    
    private final int getMaximumY() {
        for ( int y = GameModel.HEIGHT - 1; y >= 0; y-- ) {
            for ( int x = 0; x < GameModel.WIDTH; x++ ) {
                if ( get(x, y) ) {
                    return y;
                }
            }
        }
        return -1;
    }
    
    final ActionData shrink() {
        final int minX;
        final int maxX;
        final int minY;
        final int maxY;
        if ( (minX = getMinimumX()) < 0 || (maxX = getMaximumX()) < 0 || (minY = getMinimumY()) < 0 || (maxY = getMaximumY()) < 0 ) {
            return null;
        }
        final int width = maxX - minX + 1;
        final int height = maxY - minY + 1;
        final byte[] buffer = new byte[(width * height + 7) / 8];
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                set(buffer, get(x + minX, y + minY), width, x, y);
            }
        }
        return new ActionData(buffer, minX, minY, width, height);
    }
    
    BuildingShrinker(final byte[] building) {
        this.building = building;
    }
}
