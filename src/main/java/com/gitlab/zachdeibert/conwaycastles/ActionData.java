package com.gitlab.zachdeibert.conwaycastles;

import java.io.Serializable;

public final class ActionData implements Serializable {
    private static final long serialVersionUID = 8577562656063798966L;
    public byte[]             data;
    public int                x;
    public int                y;
    public int                width;
    public int                height;
    
    private final byte get(final byte[] d, final int x, final int y) {
        final int o = y * GameModel.WIDTH + x;
        final int i = o / 2;
        if ( o % 2 == 0 ) {
            return (byte) (d[i] >> 4);
        } else {
            return (byte) (d[i] & 0x0F);
        }
    }
    
    private final boolean getBit(final int x, final int y) {
        final int o = y * width + x;
        return (data[o / 8] & (1 << (o % 8))) != 0;
    }
    
    public boolean isValid(byte[] data, byte v) {
        if ( (v == 1 && y >= GameModel.HEIGHT * 3 / 4) || (v == 0 && y + height <= GameModel.HEIGHT / 4) ) {
            return false;
        }
        if ( width >= 6 ) {
            for ( int x = 0, r = 0; x < width; x++ ) {
                boolean h = false;
                for ( int y = 0; y < height; y++ ) {
                    if ( getBit(x, y) ) {
                        h = true;
                        break;
                    }
                }
                if ( h ) {
                    r = 0;
                } else {
                    r++;
                }
                if ( r >= 4 ) {
                    return false;
                }
            }
        }
        if ( height >= 6 ) {
            for ( int y = 0, r = 0; y < height; y++ ) {
                boolean h = false;
                for ( int x = 0; x < height; x++ ) {
                    if ( getBit(x, y) ) {
                        h = true;
                        break;
                    }
                }
                if ( h ) {
                    r = 0;
                } else {
                    r++;
                }
                if ( r >= 4 ) {
                    return false;
                }
            }
        }
        for ( int x = -4; x < width + 4; x++ ) {
            for ( int y = -4; y < height + 4; y++ ) {
                final int rx = x + this.x;
                final int ry = y + this.y;
                if ( rx > 0 && rx < GameModel.WIDTH && ry > 0 && ry < GameModel.HEIGHT && get(data, rx, ry) % 4 == v ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public ActionData() {}
    
    public ActionData(byte[] data, int x, int y, int width, int height) {
        this.data = data;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
